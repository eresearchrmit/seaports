package war.controller;

import helpers.EngineeringModelHelper;
import helpers.SecurityHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import war.dao.*;
import war.model.*;
import war.model.DataElement.DisplayType;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.ui.Model;

@Controller
@RequestMapping("auth/workboard")
public class WorkboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserStoryDao userStoryDao;
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private SeaportDao seaportDao;
	
	@Autowired
	private DataElementDao dataElementDao;

	@Autowired
	private AbsVariableDao absVariableDao;
	
	@Autowired
	private AbsDataDao absDataDao;
	
	@Autowired
	private BitreVariableCategoryDao bitreVariableCategoryDao;
	
	@Autowired
	private BitreDataDao bitreDataDao;
	
	@Autowired
	private CsiroDataDao csiroDataDao;
	
	@Autowired
	private CmarDataDao cmarDataDao;
	
	@Autowired
	private CsiroDataBaselineDao csiroDataBaselineDao;
	
	@Autowired
	private PastDataDao pastDataDao;
	
	@Autowired
	private AcornSatStationDao acornSatStationDao;
	
	@Autowired
	private AcornSatDataDao acornSatDataDao;
	
	@Autowired
	private EngineeringModelVariableDao engineeringModelVariableDao;
	
	@Autowired
	private EngineeringModelAssetDao engineeringModelAssetDao;
	
	@Autowired
	private EngineeringModelDataDao engineeringModelDataDao;
	
	@Autowired
	private ClimateEmissionScenarioDao climateEmissionScenarioDao;
	
	@Autowired
	private ClimateModelDao climateModelDao;
	
	@Autowired
	private ClimateParamsDao climateParamsDao;

	@Autowired
	private WeatherEventDao weatherEventDao;

	@RequestMapping(value= "/workboard-created", method = RequestMethod.GET)
	public String workboardCreated(Model model) {
		return "workboardCreated";
	}
	
	@RequestMapping(value= "/my-workboard", method = RequestMethod.GET)
	public String getWorkboard(Model model) {
		logger.info("Inside getWorkboard");
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
			
			return "redirect:/auth/workboard?user=" + userDetails.getUsername();
		}
		catch (NullPointerException ex) {
			return "redirect:/accessDenied";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getWorkboard(@RequestParam(value="user",required=true) String username, Model model) {
		logger.info("Inside getWorkboard");
		
		try {
			if (!(SecurityHelper.IsCurrentUserMatching(username) || SecurityHelper.IsCurrentUserAdmin())) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			User user = userDao.find(username);
			UserStory userStory = userStoryDao.getWorkboard(user); 
			if (userStory == null) {
				return ModelForWorkboardCreation(model, user);
			}
			return ModelForWorkboard(model, userStory);
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", WorkboardController.ERR_RETRIEVE_WORKBOARD);
		}

		return ModelForWorkboard(model, null);
	}
	
	@RequestMapping(value= "/addAbsData", method = RequestMethod.POST)
	public String addAbsDataToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="variable",required=true) Integer variableId,
		@RequestParam(value="seaport",required=true) String seaportCode, 
		@RequestParam(value="displayType",required=true) String displayTypeString, 
		RedirectAttributes attributes)
	{
		logger.info("Inside addAbsDataToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
    		AbsVariable variable = absVariableDao.find(variableId);
    		Seaport seaport = seaportDao.find(seaportCode);
			List<AbsData> absDataList = absDataDao.getAllInSeaportForVariable(seaport, variable);
			DataElement.DisplayType displayType = DataElement.DisplayType.fromString(displayTypeString);
			
			DataElementAbs dataElement = new DataElementAbs(new Date(), "ABS Data - " + variable.getName(), true, 0, displayType, userStory, absDataList);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_ABS_DATA_ADDED);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("warningMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-non-climate-context";
	}
	
	@RequestMapping(value= "/addBitreData", method = RequestMethod.POST)
	public String addBitreDataToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="variableCategory",required=true) Integer variableCategoryId,
		@RequestParam(value="seaport",required=true) String seaportCode, 
		@RequestParam(value="displayType",required=true) String displayTypeString, 
		RedirectAttributes attributes)
	{
		logger.info("Inside addBitreDataToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
    		BitreVariableCategory category = bitreVariableCategoryDao.find(variableCategoryId);
    		Seaport seaport = seaportDao.find(seaportCode);
    		List<BitreData> bitreDataList = bitreDataDao.find(seaport, category);
    		DataElement.DisplayType displayType = DataElement.DisplayType.fromString(displayTypeString);
    		
			DataElementBitre dataElement = new DataElementBitre(new Date(), "Ports Australia Data - " + category.getName(), true, 0, displayType, userStory, bitreDataList);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_BITRE_DATA_ADDED);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("warningMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-non-climate-context";
	}
	
	@RequestMapping(value= "/upload", method = RequestMethod.POST)
	public String uploadfileinWorkboard(
			@RequestParam(value="file",required=true) MultipartFile uploadfile,
			@RequestParam(value="id",required=true) Integer userStoryId, 
			RedirectAttributes attributes) {
		logger.info("Inside uploadfileinWorkBoard");
		
		UserStory userStory = null;
        try {
        	userStory = userStoryDao.find(userStoryId);
        	
        	if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
        	
            int lastIndex = uploadfile.getOriginalFilename().lastIndexOf('.');
        	String fileName = uploadfile.getOriginalFilename().substring(0, lastIndex);
            String fileExtension = uploadfile.getOriginalFilename().substring(lastIndex + 1);
            
            String contentType = uploadfile.getContentType();
            String[] arrPlainTextFiletypes = {"text/plain", "application/txt", "browser/internal", "text/anytext", "widetext/plain", "widetext/paragraph"};
            String[] arrJpegFiletypes = {"image/jpeg", "image/jpg", "image/jp_", "application/jpg", "application/x-jpg", "image/pjpeg", "image/pipeg", "image/vnd.swiftview-jpeg","image/x-xbitmap"};
            String[] arrCsvFiletypes = {"text/comma-separated-values", "text/csv", "application/csv", "application/excel", "application/vnd.ms-excel", "application/vnd.msexcel"};
            if(ArrayUtils.contains(arrPlainTextFiletypes, contentType) || ArrayUtils.contains(arrJpegFiletypes, contentType) || ArrayUtils.contains(arrCsvFiletypes, contentType)) {
            	
            	DisplayType displayType = DisplayType.PLAIN;
            	if (ArrayUtils.contains(arrJpegFiletypes, contentType)) // File is a JPEG
            		displayType = DisplayType.PICTURE;
            	else if (ArrayUtils.contains(arrCsvFiletypes, contentType)) // File is a CSV
            		displayType = DisplayType.TABLE;
            	DataElementFile dataElement = new DataElementFile(new Date(), fileName, true, 0, displayType, userStory, fileExtension, uploadfile.getBytes());
            	dataElementDao.save(dataElement);
            	userStory.getDataElements().add(dataElement);
            	
            	attributes.addFlashAttribute("successMessage", MSG_FILE_UPLOAD_SUCCESS);
            }
            else
            	attributes.addFlashAttribute("errorMessage", ERR_INVALID_FILETYPE);
        }
        catch (IOException e) {
        	attributes.addFlashAttribute("errorMessage", ERR_FILE_UPLOAD);
        }
        
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-non-climate-context";
	}

	@RequestMapping(value= "/addPastData", method = RequestMethod.POST)
	public String addPastDataToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="pastDataId",required=true) Integer pastDataId, 
		RedirectAttributes attributes)
	{
		logger.info("Inside addPastDataToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			List<PastData> pastDataList = new ArrayList<PastData>();
			pastDataList.add(pastDataDao.find(pastDataId));
			
			DataElementPast dataElement = new DataElementPast(new Date(), "National climate and marine trends", true, 0, DisplayType.PICTURE, userStory, pastDataList);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_PAST_DATA_ADDED);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("warningMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-observed-climate";
	}
	
	@RequestMapping(value= "/addAcornSatData", method = RequestMethod.POST)
	public String addAcornSatDataToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="acornSatExtremeData",required=true) String acornSatExtremeData,
		RedirectAttributes attributes)
	{
		logger.info("Inside addAcornSatDataToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
    		Region region = regionDao.find(userStory.getSeaport().getRegion().getName());
    		List<AcornSatStation> stations = acornSatStationDao.find(region);
    		
    		Boolean extreme = acornSatExtremeData.equals("extreme");
    		
    		List<AcornSatData> acornSatDataList = new ArrayList<AcornSatData>();
    		for (AcornSatStation station : stations) {
    			acornSatDataList.addAll(acornSatDataDao.find(station, extreme));
    		}
    		
    		DataElementAcornSat dataElement = new DataElementAcornSat(new Date(), "ACORN-SAT measurements", true, 0, DisplayType.TABLE, userStory, acornSatDataList);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_ACORNSAT_DATA_ADDED);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("warningMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-observed-climate";
	}
	
	@RequestMapping(value= "/addCsiroData", method = RequestMethod.POST)
	public String addCsiroDataToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="climateVariable",required=true) String climateVariable,
		@RequestParam(value="climateEmissionScenario",required=true) String climateEmissionScenario,
		@RequestParam(value="climateModel",required=true) String climateModel,
		@RequestParam(value="year",required=true) String year,
		@RequestParam(value="displayType",required=false) String displayTypeString, 
		RedirectAttributes attributes)
	{
		logger.info("Inside addCsiroDataToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			List<CsiroData> csiroDataList = null;
			if (climateVariable.equals("All"))
				csiroDataList = csiroDataDao.find(userStory.getSeaport().getRegion().getName(), climateEmissionScenario, climateModel, Integer.valueOf(year));
			else {
				csiroDataList = new ArrayList<CsiroData>();
				csiroDataList.add(csiroDataDao.find(userStory.getSeaport().getRegion().getName(), climateEmissionScenario, climateModel, Integer.valueOf(year), climateVariable));
			}
			
			DataElement.DisplayType displayType = DataElement.DisplayType.fromString(displayTypeString);
			
			DataElementCsiro dataElement = new DataElementCsiro(new Date(), "Future Climate Change Data", true, 0, displayType, userStory, csiroDataList);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_CSIRO_DATA_ADDED);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("warningMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-future-climate";
	}
	
	@RequestMapping(value= "/addCmarData", method = RequestMethod.POST)
	public String addCmarDataToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="year",required=true) String year,
		@RequestParam(value="includePictures",required=false) String includePictures, 
		RedirectAttributes attributes)
	{
		logger.info("Inside addCmarDataToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
    		// Retrieve data using static Climate Model, Emission scenario and Variable since it is fixed
    		// Refer to the method "addCsiroDataToWorkBoard" to see how to enable dynamic data
			List<CmarData> cmarDataList = new ArrayList<CmarData>();
			cmarDataList.add(cmarDataDao.find(userStory.getSeaport().getRegion().getName(), "A1B", "Most Likely", Integer.valueOf(year), "Sea Level Rise"));
			
			Boolean picturesIncluded = (includePictures != null && includePictures.equals("on")) ? true : false;
			
			DataElementCmar dataElement = new DataElementCmar(new Date(), "CMAR Data", true, 0, DisplayType.TABLE, userStory, cmarDataList, picturesIncluded);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_CMAR_DATA_ADDED);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("warningMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-future-climate";
	}
	
	@RequestMapping(value= "/addEngineeringData", method = RequestMethod.POST)
	public String addEngineeringDataToWorkboard(
			@RequestParam(value="file",required=true) MultipartFile uploadfile,
			@RequestParam(value="sourceType",required=true) String sourceType,
			@RequestParam(value="engVariable",required=true) String engVariableName,
			@RequestParam(value="engVariableCategory",required=true) String engVariableCategory,
			@RequestParam(value="id",required=true) Integer userStoryId, 
			@RequestParam(value="displayType",required=true) String displayTypeString, 
			RedirectAttributes attributes) {
		logger.info("Inside addEngineeringDataToWorkBoard");
		
    	UserStory userStory = null;
    	try {
    		userStory = userStoryDao.find(userStoryId);
    		
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
    		
    		EngineeringModelVariable engVariable = null;
			try {
				engVariable = engineeringModelVariableDao.find(engVariableName, engVariableCategory);
			}
			catch (NoResultException e) {
				logger.info(e.getMessage() + "(" + engVariableName + ")");
				throw (e);
			}
    		
			DataElement.DisplayType displayType = DataElement.DisplayType.fromString(displayTypeString);
			
    		if (sourceType.equals("upload")) {
    			logger.info("Excel File Upload");
    			
    			int lastIndex = uploadfile.getOriginalFilename().lastIndexOf('.');
    	    	//String fileName = uploadfile.getOriginalFilename().substring(0, lastIndex);
    	        String fileExtension = uploadfile.getOriginalFilename().substring(lastIndex + 1);
    	    	String fileType = uploadfile.getContentType();
    			
    			// Checks the file extension & MIME type
    	    	if (uploadfile.getSize() == 0) {
    	    		throw new IllegalArgumentException(WorkboardController.ERR_UPLOAD_NO_FILE);
    	    	}
    	    	else if (!(fileType.equals("application/vnd.ms-excel") || fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
    	        		|| !(fileExtension.equals("xls") || fileExtension.equals("xlsx"))) {
    	        	throw new InvalidFormatException(WorkboardController.ERR_INVALID_FILE_FORMAT);
    	        }
    	        
    	    	ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
    	    	// HSSFWorkbook and HSSFSheet for XLS, XSSFWorkbook and XSSFSheet for XLSX
    	    	HSSFWorkbook workbook = new HSSFWorkbook(bis);
    	    	
    			// Extract & save Engineering Model Asset
    			EngineeringModelAsset asset = extractEngineeringOutputAsset(workbook);
    	    	asset = engineeringModelAssetDao.save(asset);
    	    	
    	    	// Extract and save Engineering Model Data
    	    	List<EngineeringModelData> extractedDataList = extractEngineeringOutputData(workbook, userStory, engVariable, asset);
    				
    			// Create a data element using the extracted data if there is some
    			if (extractedDataList != null && extractedDataList.size() > 0) {
    			
    				for (EngineeringModelData data : extractedDataList) {
    					engineeringModelDataDao.save(data);
    				}
    				
    				DataElementEngineeringModel de = new DataElementEngineeringModel(new Date(), "Concrete deterioration for " + asset.getAssetCode(), true, 0, displayType, userStory, extractedDataList);
    				dataElementDao.save(de);
    				
    				userStory = userStoryDao.find(userStoryId);
    				attributes.addFlashAttribute("successMessage", WorkboardController.MSG_ENG_DATA_ADDED);
    			}
    			else {
    				throw new NoResultException(WorkboardController.ERR_NO_DATA_ENG_MODEL);
    			}
    		}
    		else if (sourceType.equals("example")) {
    			logger.info("Example selected");
    			
    			//Find the Data
    			List<EngineeringModelData> engineeringModelDataList = engineeringModelDataDao.find(userStory.getSeaport().getRegion(), engVariable);
    			
    			// Create the data element
    			if (engineeringModelDataList != null && engineeringModelDataList.size() > 0) {
    				String dataElementTitle = "Concrete deterioration for " + engineeringModelDataList.get(0).getAsset().getAssetCode();
					DataElementEngineeringModel de = new DataElementEngineeringModel(new Date(), dataElementTitle, true, 0, displayType, userStory, engineeringModelDataList);
					dataElementDao.save(de);
					
					userStory = userStoryDao.find(userStoryId);
					attributes.addFlashAttribute("successMessage", WorkboardController.MSG_ENG_DATA_ADDED);
    			}
    			else {
    				throw new NoResultException(WorkboardController.ERR_NO_DATA_ENG_MODEL_EXAMPLE);
    			}
    		}
        }
    	catch (NoResultException e) {
    		attributes.addFlashAttribute("warningMessage", e.getMessage());
        }
        catch (InvalidFormatException e) {
        	attributes.addFlashAttribute("warningMessage", e.getMessage());
        }
        catch (IllegalArgumentException e) {
        	attributes.addFlashAttribute("warningMessage", e.getMessage());
        }
        catch (IOException e) {
        	attributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-applications";
	}
	
	/**
	 * Extracts and construct an engineering model asset from the given Excel sheet
	 * @return EngineeringModelAsset: the asset object extracted from the file
	 */
	private EngineeringModelAsset extractEngineeringOutputAsset(HSSFWorkbook workbook) {
		
		HSSFSheet  sheet = workbook.getSheetAt(0); // Supposedly always 1 sheet only (index 0)
		String assetCode = workbook.getSheetName(0); // The asset code is the name of the sheet
		Row row = sheet.getRow(25); // Line 25 is the 1st row containing data
    	
    	// Extract data from columns H to U
    	int assetYear = (int)(row.getCell(7).getNumericCellValue());
    	String assetDescription = row.getCell(8).getStringCellValue();
    	String assetZone = row.getCell(9).getStringCellValue();
    	Double assetDistanceFromCoast = row.getCell(10).getNumericCellValue();
    	String assetExposureClass = row.getCell(11).getStringCellValue();
    	String assetCarbonationClass = row.getCell(12).getStringCellValue();
    	String assetChlorideClass = row.getCell(13).getStringCellValue();
    	Double assetCover = row.getCell(15).getNumericCellValue();
    	Double assetDMember = row.getCell(16).getNumericCellValue();
    	Double assetFPrimeC = row.getCell(17).getNumericCellValue();
    	Double assetWc = row.getCell(18).getNumericCellValue();
    	Double assetCe = row.getCell(19).getNumericCellValue();
    	Double assetDbar = row.getCell(20).getNumericCellValue();
    	
    	// Creates the Asset object
    	EngineeringModelAsset asset = new EngineeringModelAsset(assetCode, assetDescription, assetYear, assetZone, assetDistanceFromCoast, 
    			assetExposureClass, assetCarbonationClass, assetChlorideClass, assetCover, assetDMember, assetFPrimeC, 
    			assetWc, assetCe, assetDbar);
    	
    	return asset;
	}

	/**
	 * Extracts and construct an engineering model asset from the given Excel sheet, then saves it in the database
	 * @return EngineeringModelAsset: the asset object extracted from the file
	 */
	private List<EngineeringModelData> extractEngineeringOutputData(HSSFWorkbook workbook, UserStory userStory, EngineeringModelVariable engVariable, EngineeringModelAsset asset) {
    	
    	List<EngineeringModelData> extractedDataList = new ArrayList<EngineeringModelData>();
		HSSFSheet  sheet = workbook.getSheetAt(0); // Supposedly always 1 sheet only (index 0)
    	
		String climateModelName = null;
		ClimateModel climateModel = null;
		Map<Integer, Float> values = new HashMap<Integer, Float>();
		
		for (int i = 24; i < 529; i++) {
			Row row = sheet.getRow(i);
			
    		// Change the name of the model if it is in column A
			Cell cellInColA = row.getCell(0);
    		if (cellInColA != null && (cellInColA.getCellType() == Cell.CELL_TYPE_STRING || cellInColA.getCellType() == Cell.CELL_TYPE_FORMULA)) {
    			climateModelName = cellInColA.getStringCellValue();
    			continue;
    		}
    		if (climateModelName == null) {
    			//logger.info("Invalid Climate Model");
    			continue;
    		}
    		
    		// Read column B : the emission scenario
    		String emissionScenarioName = null;
    		ClimateEmissionScenario emissionScenario = null;
    		Cell cellInColB = row.getCell(1);
    		if (cellInColB != null && (cellInColB.getCellType() == Cell.CELL_TYPE_STRING || cellInColB.getCellType() == Cell.CELL_TYPE_FORMULA)) {
    			emissionScenarioName = cellInColB.getStringCellValue();
    			emissionScenario = climateEmissionScenarioDao.find(emissionScenarioName);
    		}
    		if (emissionScenarioName == null) {
    			logger.info("Invalid Emission Scenario Name");
    			continue;
    		}
    		
    		// Read column C : the actual model name
    		String actualModelName = null;
    		Cell cellInColC = row.getCell(2);
    		if (cellInColC != null && (cellInColC.getCellType() == Cell.CELL_TYPE_STRING || cellInColC.getCellType() == Cell.CELL_TYPE_FORMULA)) {
    			actualModelName = cellInColC.getStringCellValue();
    			climateModel = climateModelDao.find(actualModelName);
    		}
    		if (actualModelName == null) {
    			logger.info("Invalid actual Climate Model Name: '" + actualModelName + "'");
    			continue;
    		}
    		
    		// Read column D : the year
    		int year = 0;
    		Cell cellInColD = row.getCell(3);
    		if (cellInColD != null && cellInColD.getCellType() == Cell.CELL_TYPE_NUMERIC)
    			year = (int)(cellInColD.getNumericCellValue());
    		if (!(year >= 2000 && year <= 2070)) {
    			logger.info("Invalid Year: '" + year + "'");
    			continue;
    		}
    		
    		logger.info("Year:" + year + ", Emission Scenario:" + emissionScenarioName + ", Climate Model: " + climateModelName);
    		
			ClimateParams climateParams = null;
       		try {
       			climateParams = climateParamsDao.find(userStory.getSeaport().getRegion().getName(), emissionScenarioName, climateModelName);
       		}
            catch (NoResultException e) {
            	logger.info("Could not find climate params in the database: Emission Scenario: " + emissionScenarioName + ", Climate Model: " + climateModelName);
            	
            	// If the set of parameters is not found, create it
            	climateParams = new ClimateParams(userStory.getSeaport().getRegion(), climateModel, climateModelName, emissionScenario);
            	climateParams = climateParamsDao.save(climateParams);
            }
   			
   			
            // Read the value of the variable
            Integer engVariableIndex = EngineeringModelHelper.ENGINEERING_MODEL_VARIABLES.get(engVariable.getCategory() + "|" + engVariable.getName());
            Cell valueCell = row.getCell(engVariableIndex);
   			if (valueCell != null && ((valueCell.getCellType() == Cell.CELL_TYPE_NUMERIC) || (valueCell.getCellType() == Cell.CELL_TYPE_FORMULA))) {
   				
   				float value = 0;
   				try {
   				 value = (float)(valueCell.getNumericCellValue());
   				}
   				catch (Exception e) {
   					logger.info("Cell '" + i + "/" + engVariableIndex + "' in error: value set to 0.");
   				}
   				
       			values.put(year, value);
       			
       			// When reaching the end of a series, save the values
       			if (year == 2070) {
       				extractedDataList.add(new EngineeringModelData(asset, climateParams, engVariable, values));
       				values.clear();
       			}
   			}
   			else {
   				logger.info("Error extracting the data for variable");
   			}
   			
    	}
		return extractedDataList;
	}
	
	@RequestMapping(value= "/addVulnerability", method = RequestMethod.POST)
	public String addVulnerabilityAssessmentToWorkboard(
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, 
		@RequestParam(value="weatherEventType",required=true) String type,
		@RequestParam(value="weatherEventYear",required=true) String yearString,
		@RequestParam(value="weatherEventDirect",required=true) String directString, 
		@RequestParam(value="weatherEventImpact",required=true) String impact,
		@RequestParam(value="weatherEventConsequence1",required=true) String weatherEventConsequence1, @RequestParam(value="weatherEventConsequence2",required=true) String weatherEventConsequence2, @RequestParam(value="weatherEventConsequence3",required=true) String weatherEventConsequence3, @RequestParam(value="weatherEventConsequence4",required=true) String weatherEventConsequence4, @RequestParam(value="weatherEventConsequence5",required=true) String weatherEventConsequence5, @RequestParam(value="weatherEventConsequence6",required=true) String weatherEventConsequence6,  
		@RequestParam(value="weatherEventConsequence7",required=true) String weatherEventConsequence7, @RequestParam(value="weatherEventConsequence8",required=true) String weatherEventConsequence8, @RequestParam(value="weatherEventConsequence9",required=true) String weatherEventConsequence9, @RequestParam(value="weatherEventConsequence10",required=true) String weatherEventConsequence10, @RequestParam(value="weatherEventConsequence11",required=true) String weatherEventConsequence11, @RequestParam(value="weatherEventConsequence12",required=true) String weatherEventConsequence12,
		@RequestParam(value="weatherEventConsequencesOther",required=true) String consequencesOther,
		@RequestParam(value="weatherEventResponseAdequate",required=true) String responseAdequateString,
		@RequestParam(value="weatherEventChanges",required=true) String changes,
		RedirectAttributes attributes)
	{
		logger.info("Inside addVulnerabilityToWorkboard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
    		
			Boolean direct = (directString != null && directString.equals("direct")) ? true : false;
			Boolean responseAdequate = (responseAdequateString != null && responseAdequateString.equals("adequate")) ? true : false;
			Integer year = Integer.valueOf(yearString);
			
			String consequencesRating = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence1), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence2), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence3),
					WeatherEvent.parseConsequenceRating(weatherEventConsequence4), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence5), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence6), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence7), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence8), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence9), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence10), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence11), 
					WeatherEvent.parseConsequenceRating(weatherEventConsequence12));

			WeatherEvent weatherEvent = new WeatherEvent(type, year, direct, impact, consequencesRating, consequencesOther, responseAdequate, changes);
			weatherEventDao.save(weatherEvent);
			DataElementVulnerability dataElement = new DataElementVulnerability(new Date(), "Vulnerability Assessment", true, 0, DisplayType.GRAPH, userStory, weatherEvent);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			attributes.addFlashAttribute("successMessage", WorkboardController.MSG_VULNERABILITY_DATA_ADDED);
		}
		catch (Exception e) {
			attributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-applications";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST) 
	public ModelAndView createWorkboard(@ModelAttribute("userstory") UserStory userStory, Model model) {
		logger.info("Inside addWorkboard");
        
		try {
			User user = userDao.find(SecurityHelper.getCurrentlyLoggedInUsername());
			UserStory currentWorkboard = userStoryDao.getWorkboard(user);
			if (currentWorkboard != null) {
				model.addAttribute("warningMessage", WorkboardController.ERR_ALREADY_CURRENT_WORKBOARD);
				return ModelForWorkboard(model, currentWorkboard);
			}
			
			if (userStory.getSeaport() == null || userStory.getSeaport().getRegion() == null)
				throw new IllegalArgumentException(WorkboardController.ERR_REGION_NOT_DEFINED);
			
			if (userStory.getPurpose() == null || userStory.getPurpose().length() <= 0)
				throw new IllegalArgumentException(WorkboardController.ERR_PURPOSE_NOT_DEFINED);
			
			Seaport seaport = seaportDao.find(userStory.getSeaport().getCode());
			
			userStory.setOwner(user);
			userStory.setSeaport(seaport);
			userStory.setMode("active");
			userStory.setAccess("private");
			userStory.setDataElements(new ArrayList<DataElement>());
			userStory.setCreationDate(new Date());
			userStoryDao.save(userStory);
			
			return new ModelAndView("workboardCreated");
		}
		catch (AccessDeniedException e) {
			throw e;
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_CREATE_WORKBOARD + ". Details: " + e.getMessage());
		}
		
		return ModelForWorkboardCreation(model, null);
	}

	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public ModelAndView deleteWorkboard(@RequestParam(value="id", required=true) Integer userStoryId, Model model) {
		logger.debug("Inside deleteWorkboard");
		
		try {
			model.addAttribute("user", userDao.find(SecurityHelper.getCurrentlyLoggedInUsername()));
			
			UserStory userStory = userStoryDao.find(userStoryId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			User user = userStory.getOwner();
			userStoryDao.delete(userStory); // Deletes the Workboard
			return ModelForWorkboardCreation(model, user); // Starts the creation of a new workboard
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return new ModelAndView("workboard");
	}	
	
	@RequestMapping(value = "/deletedataelement",method=RequestMethod.GET) 
	public String deleteDataElement(@RequestParam(value="dataelementid",required=true) Integer dataElementId, 
			RedirectAttributes attributes, Model model) {
		logger.info("Inside deleteDataElement");
		
		try {
			DataElement dataElement = dataElementDao.find(dataElementId);
			UserStory userStory = dataElement.getUserStory();
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			// Delete the Data Element if it belongs to the user's workboard
			if (userStory.getMode().equals("active")) {
				dataElementDao.delete(dataElement);
				userStory.getDataElements().remove(dataElement);
				attributes.addFlashAttribute("successMessage", MSG_DATA_ELEMENT_DELETED);
			}
			else { // If the Data Element belongs to a User Story and not the Workboard, don't delete
				attributes.addFlashAttribute("errorMessage", ERR_DELETE_DATA_ELEMENT);
			}
			// Redirects to the right tab of the workboard after deletion based on the data element type
			return "redirect:" + redirectToCategory(dataElement);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return ("redirect:/auth/my-workboard");
	}
	
	private ModelAndView ModelForWorkboard(Model model, UserStory userStory) {
		logger.info("Inside ModelForWorkboard");
		
		model.addAttribute("user", userDao.find(SecurityHelper.getCurrentlyLoggedInUsername()));
		ModelAndView mav = new ModelAndView("workboard");
		
		if (userStory != null) {	
			mav.addObject("userstory", userStory);
			
			try {
				int[] dataelementsCounts = new int[4]; // Count data element in each category to "check" the non-empty tabs
				
				// Prepare the data elements
				List<DataElement> dataElements = userStory.getDataElements();
		 		for (DataElement dataElement : dataElements) {
		 			if (dataElement.getClass().equals(DataElementAbs.class)) {
		 				List<AbsData> absDataList = ((DataElementAbs)dataElement).getAbsDataList();
		 				for (AbsData data : absDataList) {
		 					data.generateValues();
		 				}
		 				dataelementsCounts[0]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementBitre.class)) {
		 				List<BitreData> bitreDataList = ((DataElementBitre)dataElement).getBitreDataList();
		 				for (BitreData data : bitreDataList) {
		 					data.generateValues();
		 				}
		 				dataelementsCounts[0]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementFile.class)) {
		 				((DataElementFile)dataElement).generateStringContent();
		 				dataelementsCounts[0]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementPast.class)) {
		 				dataelementsCounts[1]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementAcornSat.class)) {
		 				dataelementsCounts[1]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementCmar.class)) {
		 				dataelementsCounts[2]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementCsiro.class)) {
		 				for (CsiroData data : ((DataElementCsiro)dataElement).getCsiroDataList()) {
		 					data.setBaseline(csiroDataBaselineDao.find(data.getParameters().getRegion(), data.getVariable()));
		 				}
		 				dataelementsCounts[2]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementEngineeringModel.class)) {
		 				List<EngineeringModelData> engineeringModelDataList = ((DataElementEngineeringModel)dataElement).getEngineeringModelDataList();
		 				for (EngineeringModelData data : engineeringModelDataList) {
		 					data.generateValues();
		 				}
		 				dataelementsCounts[3]++;
		 			}
		 			else if (dataElement.getClass().equals(DataElementVulnerability.class)) {
		 				dataelementsCounts[3]++;
		 			}
				}
		 		mav.addObject("dataelements", dataElements);
		 		mav.addObject("dataelementsCounts", dataelementsCounts);
		 		
		 		// Empty data element to use as a "New Data Element"
		 		mav.addObject(new DataElement());
		
		 		// Prepares the various Comboboxes for new data element creation
		 		List<EngineeringModelVariable> chlorideEngineeringModelVariables = engineeringModelVariableDao.getAll("Chloride"); 
		 		mav.addObject("chlorideEngineeringModelVariables", chlorideEngineeringModelVariables);
		 		List<EngineeringModelVariable> carbonationEngineeringModelVariables = engineeringModelVariableDao.getAll("Carbonation"); 
		 		mav.addObject("carbonationEngineeringModelVariables", carbonationEngineeringModelVariables);
		 		
		 		// List of seaports in the workboard's region
		 		List<Seaport> regionSeaports = seaportDao.getAllInRegion(userStory.getSeaport().getRegion());
		 		mav.addObject("regionSeaports", regionSeaports);
			}
			catch (Exception e) {
				model.addAttribute("errorMessage", e.getMessage());
			}
		}
		
		return mav;
	}

	private ModelAndView ModelForWorkboardCreation(Model model, User user) {
		logger.info("Inside ModelForWorkboardCreation");
		
		ModelAndView mav = new ModelAndView("workboardCreation");
		try {
			model.addAttribute("user", user);
			
			UserStory userStory = new UserStory();
			userStory.setOwner(user);
			userStory.setSeaport(new Seaport("", "", new Region("", "")));
			mav.addObject("userstory", userStory);
			
			// List of all the seaports
	 		List<Seaport> allSeaports = seaportDao.getAll();
	 		mav.addObject("allSeaports", allSeaports);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	/**
	 * Redirects to the right tab of the workboard after deletion based on the data element type
	 * @param dataElement: the data element
	 * @return the address to which the page should be redirected
	 */
	private String redirectToCategory(DataElement dataElement) {
		
		if (dataElement.getClass().equals(DataElementAbs.class) || dataElement.getClass().equals(DataElementBitre.class) || dataElement.getClass().equals(DataElementFile.class))
			return "/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-non-climate-context";
		else if (dataElement.getClass().equals(DataElementPast.class) || dataElement.getClass().equals(DataElementAcornSat.class))
			return "/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-observed-climate";
		else if (dataElement.getClass().equals(DataElementCsiro.class) || dataElement.getClass().equals(DataElementCmar.class))
			return "/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-future-climate";
		else if (dataElement.getClass().equals(DataElementEngineeringModel.class) || dataElement.getClass().equals(DataElementVulnerability.class))
			return "/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername() + "#tabs-applications";
		
		return "/auth/workboard?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	public static final String ERR_ACCESS_DENIED = "You are not allowed to access this Workboard";
	
	public static final String ERR_CREATE_WORKBOARD = "An error happened while creating your Workboard. Please contact an administrator";
	public static final String ERR_ALREADY_CURRENT_WORKBOARD = "There is already a current workboard. Delete it or make a User Story before creating a new Workboard";
	public static final String ERR_REGION_NOT_DEFINED = "Please select a region and a seaport for your workboard";
	public static final String ERR_PURPOSE_NOT_DEFINED = "Please describe the activity that leads you to use this application";
	public static final String ERR_RETRIEVE_WORKBOARD = "Impossible to retrieve your Workboard";
	public static final String ERR_DELETE_DATA_ELEMENT = "The Data Element could not be deleted";
	public static final String ERR_FILE_UPLOAD = "Unable to upload the file to your workboard";
	public static final String ERR_INVALID_FILETYPE = "This file format is not handled by the application (only text, csv and jpeg files are allowed).";
	
	public static final String MSG_ABS_DATA_ADDED = "The ABS data has been added successfully to your workboard";
	public static final String MSG_BITRE_DATA_ADDED = "The BITRE data has been added successfully to your workboard";
	public static final String MSG_PAST_DATA_ADDED = "The Past data has been added successfully to your workboard";
	public static final String MSG_ACORNSAT_DATA_ADDED = "The Acorn-Sat data has been added successfully to your workboard";
	public static final String MSG_CSIRO_DATA_ADDED = "The CSIRO data has been added successfully to your workboard";
	public static final String MSG_CMAR_DATA_ADDED = "The CMAR data has been added successfully to your workboard";
	public static final String MSG_VULNERABILITY_DATA_ADDED = "The Vulnerability has been added successfully to your workboard";
	public static final String MSG_DATA_ELEMENT_DELETED = "The Data Element was deleted successfully from your Workboard";
	public static final String MSG_WORKBOARD_SAVED = "Workboard saved";
	public static final String MSG_FILE_UPLOAD_SUCCESS = "The file was uploaded successfully to your workboard";
	public static final String MSG_ENG_DATA_ADDED = "The Engineering Model Data has been added successfully to your workboard";
	
	public static final String ERR_UPLOAD_ENG_MODEL = "Unable to upload the engineering model data to your workboard";
	public static final String ERR_NO_DATA_ENG_MODEL = "No data could be extracted from the provided Excel file";
	public static final String ERR_NO_DATA_ENG_MODEL_EXAMPLE = "No example data found for the required variable";
	public static final String ERR_INVALID_FILE_FORMAT = "Invalid file format. The type of the file you tried to upload is not allowed";
	public static final String ERR_UPLOAD_NO_FILE = "No file received. Please make sure that you have chosen an Excel file to upload, or selected a pre-defined example.";
}