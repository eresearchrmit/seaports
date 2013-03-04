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
	private DataElementDao dataElementDao;

	@Autowired
	private CsiroDataDao csiroDataDao;
	
	@Autowired
	private CsiroDataBaselineDao csiroDataBaselineDao;
	
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

	
	@RequestMapping(value= "my-workboard", method = RequestMethod.GET)
	public ModelAndView getWorkBoard(Model model) {
		logger.info("Inside getWorkBoard");
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
		  userDetails = (UserDetails) principal;
		}
		
		return getUserWorkBoard(userDetails.getUsername(), model);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserWorkBoard(@RequestParam(value="user",required=true) String username, Model model) {
		logger.info("Inside getUserWorkBoard");
		
		try {
			if (!(SecurityHelper.IsCurrentUserMatching(username))) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			User user = userDao.find(username);
			UserStory userStory = userStoryDao.getWorkboard(user); 
			if (userStory == null) {
				return CreateWorkBoard(user, model);
			}
			return modelForActiveWBview(model, userStory);
		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", WorkboardController.ERR_RETRIEVE_WORKBOARD);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return modelForActiveWBview(model, null);
	}
	
	@RequestMapping(value= "/upload", method = RequestMethod.POST)
	public ModelAndView uploadfileinWorkBoard(
			@RequestParam(value="file",required=true) MultipartFile uploadfile,
			@RequestParam(value="id",required=true) Integer userStoryId, 
			Model model) {
		logger.info("Inside uploadfileinWorkBoard");
		
		UserStory userStory = null;
        try {
        	userStory = userStoryDao.find(userStoryId);
        	
        	if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
        	
            int lastIndex = uploadfile.getOriginalFilename().lastIndexOf('.');
        	String fileName = uploadfile.getOriginalFilename().substring(0, lastIndex);
            String fileExtension = uploadfile.getOriginalFilename().substring(lastIndex + 1);
            
            // TODO: Confirm the list of allowed Extensions / MIME type
            String[] arrAllowedFiletypes = {"image/jpeg", "image/gif", "image/png", "text/plain", "text/html", "text/csv", "text/xml"};
            if(ArrayUtils.contains(arrAllowedFiletypes, uploadfile.getContentType())) {
            	DataElementFile dataElement = new DataElementFile(new Date(), fileName, true, 0, userStory, fileExtension, uploadfile.getBytes());
            	dataElementDao.save(dataElement);
            	userStory.getDataElements().add(dataElement);
            	
            	model.addAttribute("successMessage", MSG_FILE_UPLOAD_SUCCESS);
            }
            else
            	model.addAttribute("errorMessage", ERR_INVALID_FILETYPE);
        }
        catch (AccessDeniedException e) {
        	model.addAttribute("errorMessage", e.getMessage());
        }
        catch (Exception e) {
        	model.addAttribute("errorMessage", ERR_FILE_UPLOAD);
        }
        
		ModelAndView mav = modelForActiveWBview(model, userStory);
		return mav;	
	}
	
	@RequestMapping(value= "/addEngineeringData", method = RequestMethod.POST)
	public ModelAndView addEngineeringDataToWorkBoard(
			@RequestParam(value="file",required=true) MultipartFile uploadfile,
			@RequestParam(value="sourceType",required=true) String sourceType,
			@RequestParam(value="engVariable",required=true) String engVariableName,
			@RequestParam(value="engVariableCategory",required=true) String engVariableCategory,
			@RequestParam(value="id",required=true) Integer userStoryId, 
			Model model)
	throws IOException {
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
    		
    		if (sourceType.equals("upload")) {
    			logger.info("Excel File Upload");
    			
    			int lastIndex = uploadfile.getOriginalFilename().lastIndexOf('.');
    	    	//String fileName = uploadfile.getOriginalFilename().substring(0, lastIndex);
    	        String fileExtension = uploadfile.getOriginalFilename().substring(lastIndex + 1);
    	    	String fileType = uploadfile.getContentType();
    			
    			// Checks the file extension & MIME type
    	        if (!(fileType.equals("application/vnd.ms-excel") || fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
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
    				
    				DataElementEngineeringModel de = new DataElementEngineeringModel(new Date(), "Concrete deterioration for " + asset.getAssetCode(), true, 0, userStory, extractedDataList);
    				dataElementDao.save(de);
    				
    				userStory = userStoryDao.find(userStoryId);
    				model.addAttribute("successMessage", WorkboardController.MSG_ENG_DATA_ADDED);
    			}
    			else {
    				throw new NoResultException(WorkboardController.ERR_NO_DATA_ENG_MODEL);
    			}
    		}
    		else if (sourceType.equals("example")) {
    			logger.info("Example selected");
    			
    			//Find the Data
    			List<EngineeringModelData> engineeringModelDataList = engineeringModelDataDao.find(userStory.getRegion(), engVariable);
    			
    			// Create the data element
    			if (engineeringModelDataList != null && engineeringModelDataList.size() > 0) {
    				String dataElementTitle = "Concrete deterioration for " + engineeringModelDataList.get(0).getAsset().getAssetCode();
					DataElementEngineeringModel de = new DataElementEngineeringModel(new Date(), dataElementTitle, true, 0, userStory, engineeringModelDataList);
					dataElementDao.save(de);
					
					userStory = userStoryDao.find(userStoryId);
					model.addAttribute("successMessage", WorkboardController.MSG_ENG_DATA_ADDED);
    			}
    			else {
    				throw new NoResultException(WorkboardController.ERR_NO_DATA_ENG_MODEL_EXAMPLE);
    			}
    		}
        }
    	catch (NoResultException e) {
    		model.addAttribute("warningMessage", e.getMessage());
        }
        catch (Exception e) {
        	model.addAttribute("errorMessage", e.getMessage());
        }
        
		return modelForActiveWBview(model, userStory);	
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
       			climateParams = climateParamsDao.find(userStory.getRegion().getName(), emissionScenarioName, climateModelName);
       		}
            catch (NoResultException e) {
            	logger.info("Could not find climate params in the database: Emission Scenario: " + emissionScenarioName + ", Climate Model: " + climateModelName);
            	
            	// If the set of parameters is not found, create it
            	climateParams = new ClimateParams(userStory.getRegion(), climateModel, climateModelName, emissionScenario);
            	climateParams = climateParamsDao.save(climateParams);
            }
   			
   			
            // Read the value of the variable
            Integer engVariableIndex = EngineeringModelHelper.ENGINEERING_MODEL_VARIABLES.get(engVariable.getName());
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
	
	@RequestMapping(value= "/addCsiroData", method = RequestMethod.POST)
	public ModelAndView addCsiroDataToWorkBoard(
		@RequestParam(value="climateVariable",required=true) String climateVariable,
		@RequestParam(value="climateEmissionScenario",required=true) String climateEmissionScenario,
		@RequestParam(value="climateModel",required=true) String climateModel,
		@RequestParam(value="year",required=true) String year,
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, Model model)
	{
		logger.info("Inside addCsiroDataToWorkBoard");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
    		if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			List<CsiroData> csiroDataList = null;
			if (climateVariable.equals("All"))
				csiroDataList = csiroDataDao.find(userStory.getRegion().getName(), climateEmissionScenario, climateModel, Integer.valueOf(year));
			else {
				csiroDataList = new ArrayList<CsiroData>();
				csiroDataList.add(csiroDataDao.find(userStory.getRegion().getName(), climateEmissionScenario, climateModel, Integer.valueOf(year), climateVariable));
			}
			
			DataElementCsiro dataElement = new DataElementCsiro(new Date(), "CSIRO Data", true, 0, userStory, csiroDataList);
			dataElementDao.save(dataElement);
			
			userStory.getDataElements().add(dataElement);
			
			model.addAttribute("successMessage", WorkboardController.MSG_CSIRO_DATA_ADDED);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		ModelAndView mav = modelForActiveWBview(model, userStory);
		return mav;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST) 
	public ModelAndView addWorkboard(@ModelAttribute("userstory") UserStory userStory, Model model) {
		logger.info("Received object for workboard  " + userStory);
        
		try {
			User user = userDao.find(SecurityHelper.getCurrentlyLoggedInUsername());
			UserStory currentWorkboard = userStoryDao.getWorkboard(user);
			if (currentWorkboard != null)
			{
				model.addAttribute("errorMessage", WorkboardController.ERR_ALREADY_CURRENT_WORKBOARD);
				return modelForActiveWBview(model, currentWorkboard);
			}
			
			Region region = regionDao.find(userStory.getRegion().getName());
			
			userStory.setOwner(user);
			userStory.setRegion(region);
			userStory.setMode("active");
			userStory.setAccess("private");
			userStoryDao.save(userStory);
			
			return modelForActiveWBview(model, userStory);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return modelForActiveWBview(model, null);
	}

	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public ModelAndView deleteWorkboard(@RequestParam(value="id", required=true) Integer userStoryId, Model model) {
		logger.debug("Inside deleteWorkboard");
		
		try {
			UserStory userStory = userStoryDao.find(userStoryId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			userStoryDao.delete(userStory);

			return CreateWorkBoard(userStory.getOwner(), model);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return new ModelAndView("workboard");
	}	
	
	@RequestMapping(value = "/deletedataelement",method=RequestMethod.GET) 
	public ModelAndView deleteDataElementFromUserStory(@RequestParam(value="dataelementid",required=true) Integer dataElementId, Model model) {
		logger.info("Inside deleteDataElementFromUserStory");
		
		try {
			DataElement dataElement = dataElementDao.find(dataElementId);
			UserStory userStory = dataElement.getUserStory();
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			dataElementDao.delete(dataElement);
			userStory.getDataElements().remove(dataElement);
			model.addAttribute("successMessage", MSG_DATA_ELEMENT_DELETED);
			
			ModelAndView mav = modelForActiveWBview(model, userStory);
			return mav;
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		catch (AccessDeniedException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_DELETE_DATA_ELEMENT);
		}
		
		return new ModelAndView("workboard");
	}
	

	private ModelAndView modelForActiveWBview(Model model, UserStory userStory) {
		logger.info("Inside modelForActiveWBview");
		
		ModelAndView mav = new ModelAndView();
		logger.info("Setting View Name: workboard");
		mav.setViewName("workboard");
		
		mav.addObject("userstory", userStory);
		model.addAttribute("user", userDao.find(SecurityHelper.getCurrentlyLoggedInUsername()));
		
		try {		
			//List<DataElement> dataElements = dataElementDao.getDataElements(userStory);
			List<DataElement> dataElements = userStory.getDataElements();
	 		for (DataElement dataElement : dataElements) {
	 			if (dataElement.getClass().equals(DataElementFile.class)) {
	 				((DataElementFile)dataElement).generateStringContent();
	 			}
	 			else if (dataElement.getClass().equals(DataElementCsiro.class)) {
	 				for (CsiroData data : ((DataElementCsiro)dataElement).getCsiroDataList()) {
	 					data.setBaseline(csiroDataBaselineDao.find(data.getParameters().getRegion(), data.getVariable()));
	 				}
	 			}
	 			else if (dataElement.getClass().equals(DataElementEngineeringModel.class)) {
	 				List<EngineeringModelData> engineeringModelDataList = ((DataElementEngineeringModel)dataElement).getEngineeringModelDataList();
	 				for (EngineeringModelData data : engineeringModelDataList) {
	 					data.generateValues();
	 				}
	 			}
			}
	 		mav.addObject("dataelements", dataElements);
	 		
	 		// Empty data element to use as a "New Data Element"
	 		mav.addObject(new DataElement());
	
	 		List<EngineeringModelVariable> chlorideEngineeringModelVariables = engineeringModelVariableDao.getAll("Chloride"); 
	 		mav.addObject("chlorideEngineeringModelVariables", chlorideEngineeringModelVariables);
	 		List<EngineeringModelVariable> carbonationEngineeringModelVariables = engineeringModelVariableDao.getAll("Carbonation"); 
	 		mav.addObject("carbonationEngineeringModelVariables", carbonationEngineeringModelVariables);
	 		
	 		/*List<ClimateEmissionScenario> emissionScenarios = climateEmissionScenarioDao.getAll(); 
	 		mav.addObject("emissionScenarios", emissionScenarios);
	 		List<ClimateModel> climateModels = climateModelDao.getAll();
	 		mav.addObject("climateModels", climateModels);
	 		List<ClimateVariable> climateVariables = climateVariableDao.getAll();
	 		mav.addObject("climateVariables", climateVariables);*/
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}

	private ModelAndView CreateWorkBoard(User user, Model model) {
		ModelAndView mav = new ModelAndView();
		try {
			model.addAttribute("user", user);
			
			UserStory userStory = new UserStory();
			userStory.setOwner(user);
			userStory.setRegion(new Region(""));
			mav.addObject("userstory", userStory);
			
			logger.info("Setting View Name: workboardCreation");
			mav.setViewName("workboardCreation");
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	public static final String ERR_ACCESS_DENIED = "You are not allowed to access this Workboard";
	
	public static final String ERR_ALREADY_CURRENT_WORKBOARD = "There is already a current workboard. Delete it or make a User Story before creating a new Workboard";
	public static final String ERR_RETRIEVE_WORKBOARD = "Impossible to retrieve your Workboard";
	public static final String ERR_DELETE_DATA_ELEMENT = "The Data Element could not be deleted";
	public static final String ERR_FILE_UPLOAD = "Unable to upload the file to your workboard";
	public static final String ERR_INVALID_FILETYPE = "This file format is not handled by the application (only text, xml, html, jpeg, png and gif files are allowed).";
	
	public static final String MSG_CSIRO_DATA_ADDED = "The CSIRO Data has been added successfully to your workboard";
	public static final String MSG_DATA_ELEMENT_DELETED = "The Data Element was deleted successfully from your Workboard";
	public static final String MSG_WORKBOARD_SAVED = "Workboard saved";
	public static final String MSG_FILE_UPLOAD_SUCCESS = "The file was uploaded successfully to your workboard";
	public static final String MSG_ENG_DATA_ADDED = "The Engineering Model Data has been added successfully to your workboard";
	
	public static final String ERR_UPLOAD_ENG_MODEL = "Unable to upload the engineering model data to your workboard";
	public static final String ERR_NO_DATA_ENG_MODEL = "No data could be extracted from the provided Excel file";
	public static final String ERR_NO_DATA_ENG_MODEL_EXAMPLE = "No example data found for the required variable";
	public static final String ERR_INVALID_FILE_FORMAT = "Invalid file format. The type of the file you tried to upload is not allowed";
}