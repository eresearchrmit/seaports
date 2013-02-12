package war.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import war.dao.*;
import war.model.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/workboard")
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
	private ClimateParamsDao climateParamsDao;
	
	@Autowired
	private ClimateVariableDao climateVariableDao;
	
	@Autowired
	private EngineeringModelAssetDao engineeringModelAssetDao;
	
	@Autowired
	private EngineeringModelDataDao engineeringModelDataDao;
	
	@Autowired
	private ClimateEmissionScenarioDao climateEmissionScenarioDao;
	
	@Autowired
	private ClimateModelDao climateModelDao;
	
	
	
	//@ModelAttribute("user")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserWorkBoard(@RequestParam(value="user",required=true) String login, Model model) {
		logger.info("Inside getUserWorkBoard");
		
		try {
			User user = userDao.find(login);
			UserStory userStory = userStoryDao.getWorkBoard(user); 
			if (userStory == null) {
				return CreateWorkBoard(user, model);
			}
			return modelForActiveWBview(model, userStory);
		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_WORKBOARD);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value= "/upload", method = RequestMethod.POST)
	public ModelAndView uploadfileinWorkBoard(
			@RequestParam(value="file",required=true) MultipartFile uploadfile,
			@RequestParam(value="id",required=true) Integer userStoryId, 
			Model model) 
	throws IOException {
		logger.info("Inside uploadfileinWorkBoard");
		
		UserStory userStory = userStoryDao.find(userStoryId);
        try {
            int lastIndex = uploadfile.getOriginalFilename().lastIndexOf('.');
        	String fileName = uploadfile.getOriginalFilename().substring(0, lastIndex);
            String fileExtension = uploadfile.getOriginalFilename().substring(lastIndex + 1);
            //String fileType = uploadfile.getContentType();
            //String [] tokens = filename.split("\\.");
            
            // TODO: Define a list of allowed Extensions / MIME type
            
	        DataElementFile dataElement = new DataElementFile(new Date(), fileName, true, 0, userStory, fileExtension, uploadfile.getBytes());
            
        	dataElementDao.save(dataElement);
	        	
        	model.addAttribute("successMessage", MSG_FILE_UPLOAD_SUCCESS);
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
			@RequestParam(value="id",required=true) Integer userStoryId, 
			Model model) 
	throws IOException {
		logger.info("Inside addEngineeringDataToWorkBoard");
		
		int lastIndex = uploadfile.getOriginalFilename().lastIndexOf('.');
    	//String fileName = uploadfile.getOriginalFilename().substring(0, lastIndex);
        String fileExtension = uploadfile.getOriginalFilename().substring(lastIndex + 1);
    	String fileType = uploadfile.getContentType();
        
    	UserStory userStory = userStoryDao.find(userStoryId);
    	try {
	        // Checks the file extension & MIME type
	        if (!(fileType.equals("application/vnd.ms-excel") || fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	        		|| !(fileExtension.equals("xls") || fileExtension.equals("xlsx"))) {
	        	throw new InvalidFormatException(ERR_INVALID_FILE_FORMAT);
	        }
        
        	ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
        	// HSSFWorkbook and HSSFSheet for XLS, XSSFWorkbook and XSSFSheet for XLSX
        	HSSFWorkbook workbook = new HSSFWorkbook(bis);
        	
    		// Extract & save Engineering Model Asset
    		EngineeringModelAsset asset = extractEngineeringOutputAsset(workbook);
        	asset = engineeringModelAssetDao.save(asset);
        	
        	// Extract and save Engineering Model Data
        	List<EngineeringModelData> extractedDataList = extractEngineeringOutputData(workbook, 26, asset);
   			/*String content = "";
   			for (EngineeringModelData data : ExtractedDataList) {
   				data.setAsset(asset);
   				data = engineeringModelDataDao.save(data);
   				content += data.toString();
   			}*/
   			
   			// Create a data element using the extracted data if there is some
   			if (extractedDataList != null && extractedDataList.size() > 0) {
	   			
   				for (EngineeringModelData data : extractedDataList) {
   					engineeringModelDataDao.save(data);
   				}
   				
   				UserStory story = userStoryDao.find(userStoryId);
	   			DataElementEngineeringModel de = new DataElementEngineeringModel(new Date(), 
	   					"Concrete deterioration for " + asset.getAssetCode(), true,
	   					0, story, extractedDataList);
	   			dataElementDao.save(de);
   			}
        }
        catch (InvalidFormatException e) {
        	model.addAttribute("errorMessage", ERR_INVALID_FILE_FORMAT);
        }
        catch (Exception e) {
        	model.addAttribute("errorMessage", "Unable to upload the engineering model data to your workboard: "  + e.getMessage());
        }
        
		ModelAndView mav = modelForActiveWBview(model, userStory);
		return mav;	
	}
	
	
	/**
	 * Extracts and construct an engineering model asset from the given Excel sheet
	 * @return EngineeringModelAsset: the asset object extracted from the file
	 */
	public EngineeringModelAsset extractEngineeringOutputAsset(HSSFWorkbook workbook) {
		
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
	private List<EngineeringModelData> extractEngineeringOutputData(HSSFWorkbook workbook, int variableIndex, EngineeringModelAsset asset) {
    	
    	List<EngineeringModelData> extractedDataList = new ArrayList<EngineeringModelData>();
		HSSFSheet  sheet = workbook.getSheetAt(0); // Supposedly always 1 sheet only (index 0)
    	
		String climateModelName = null;
		ClimateModel climateModel = null;
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
       			climateParams = climateParamsDao.find("East Coast South", emissionScenarioName, climateModelName);
       		}
            catch (NoResultException e) {
            	logger.info("Could not find climate params in the database: Year: " + year + ", Emission Scenario: " + emissionScenarioName + ", Climate Model: " + climateModelName);
            	
            	// If the set of parameters is not found, create it
            	Region region = regionDao.find("East Coast South");
            	climateParams = new ClimateParams(region, climateModel, climateModelName, emissionScenario);
            	climateParams = climateParamsDao.save(climateParams);
            }
   			
   				
            // Read column D : the value of the variable
   			Cell valueCell = row.getCell(variableIndex);
   			if (valueCell != null && ((valueCell.getCellType() == Cell.CELL_TYPE_NUMERIC) || (valueCell.getCellType() == Cell.CELL_TYPE_FORMULA))) {
   				
   				float value = 0;
   				try {
   				 value = (float)(valueCell.getNumericCellValue());
   				}
   				catch (Exception e) {
   					logger.info("Cell '" + i + "/" + variableIndex + "' in error: value set to 0.");
   				}
   				
       			ClimateVariable variable = null;
       			try {
       				// Retrieve the variable in the Database
           			variable = climateVariableDao.find("Change in carbonation");
           			
       				extractedDataList.add(new EngineeringModelData(asset, climateParams, variable, year, value));
           		}
           		catch (NoResultException e) {
           			logger.info(e.getMessage() + "(" + "Change in carbonation" + ")");
           		}
   			}
   			else {
   				logger.info("Error extracting the data for variable");
   			}
   			
    	}
		return extractedDataList;
	}
	
	//@ModelAttribute("csiroData")
	@RequestMapping(value= "/addCsiroData", method = RequestMethod.POST)
	public ModelAndView addCsiroDataToWorkBoard(
		@RequestParam(value="climateVariable",required=true) String climateVariable,
		@RequestParam(value="climateEmissionScenario",required=true) String climateEmissionScenario,
		@RequestParam(value="climateModel",required=true) String climateModel,
		@RequestParam(value="year",required=true) String year,
		@RequestParam(value="userstoryid",required=true) Integer userStoryId, Model model)
	{
		logger.info("Inside addCsiroDataToWorkBoard");
		
		UserStory userStory = userStoryDao.find(userStoryId);
		
		try {
			List<CsiroData> csiroDataList = csiroDataDao.find("East Coast South", climateEmissionScenario, climateModel, Integer.valueOf(year));
			
			DataElementCsiro dataElement = new DataElementCsiro(new Date(), "CSIRO Data", true, 0, userStory, csiroDataList);
			dataElementDao.save(dataElement);
			
			model.addAttribute("successMessage", MSG_CSIRO_DATA_ADDED);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		ModelAndView mav = modelForActiveWBview(model, userStory);
		return mav;
	}
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public ModelAndView addWorkboard(@ModelAttribute("userstory") UserStory userStory, @RequestParam(value="login",required=true) String login, Model model) {
		logger.debug("Received object for workboard  " + userStory);
        
		try {
			User user = userDao.find(login);
			UserStory currentWorkboard = userStoryDao.getWorkBoard(user);
			if (currentWorkboard != null)
			{
				model.addAttribute("errorMessage", ERR_ALREADY_CURRENT_WORKBOARD);
				return modelForActiveWBview(model, currentWorkboard);
			}
			
			userStory.setOwner(user);
			userStory.setMode("active");
			userStory.setAccess("private");
			userStoryDao.save(userStory);
			
			return modelForActiveWBview(model, userStory);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return new ModelAndView();
	}
	
	@RequestMapping(value ="/save",method=RequestMethod.POST)
	public ModelAndView saveWorkboard(@ModelAttribute("userstory") UserStory updatedWorkboard, @RequestParam(value="id",required=true) Integer workboardId, Model model) {
		logger.debug("Inside saveWorkboard");
		
		try {
			//UserStory workboard = userStoryDao.find(workboardId);
			
			for (DataElement dataElement : updatedWorkboard.getDataElements())
			{
				if (dataElement.getClass().equals(DataElementFile.class)) {
					DataElementFile file = (DataElementFile)(dataElement);
					if (!(file.getFiletype().contains("jpg") || file.getFiletype().contains("jpeg") || file.getFiletype().contains("data"))) {
						String stringContent = file.getContent().toString();
						file.setContent(stringContent.getBytes());
					}
					dataElementDao.save(file);
				}
				else
					dataElementDao.save(dataElement);
				model.addAttribute("successMessage", MSG_WORKBOARD_SAVED);
			}
			return modelForActiveWBview(model, updatedWorkboard);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("activeWB");
		return mav;		
	}

	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public ModelAndView deleteWorkboard(@RequestParam(value="id", required=true) Integer userStoryId, Model model) {
		logger.debug("Inside deleteWorkboard");
		
		try {
			UserStory userStory = userStoryDao.find(userStoryId);
			userStoryDao.delete(userStory);
			
			return CreateWorkBoard(userStory.getOwner(), model);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return new ModelAndView();
	}	
	
	@RequestMapping(value = "/deletedataelement",method=RequestMethod.GET) 
	public ModelAndView deleteDataElementFromUserStory(@RequestParam(value="dataelementid",required=true) Integer dataElementId, Model model) {
		logger.debug("Inside deleteDataElementFromUserStory");
		
		try {
			DataElement dataElement = dataElementDao.find(dataElementId);
			UserStory userStory = dataElement.getUserStory();
			
			dataElementDao.delete(dataElement);
			model.addAttribute("successMessage", MSG_DATA_ELEMENT_DELETED);
			
			ModelAndView mav = modelForActiveWBview(model, userStory);
			return mav;
		}
		catch (NoResultException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}
		catch (Exception e)
		{
			model.addAttribute("errorMessage", ERR_DELETE_DATA_ELEMENT);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("activeWB");
		return mav;
	}
	

	private ModelAndView CreateWorkBoard(User user, Model model) {
		ModelAndView mav = new ModelAndView();
		try {
			model.addAttribute("user", user);
			
			UserStory userStory = new UserStory();
			userStory.setOwner(user) ;
			mav.addObject("userstory", userStory);
			
			mav.setViewName("createWB");
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	
	private ModelAndView modelForActiveWBview(Model model, UserStory userStory) {
		logger.debug("Inside modelForActiveWBview");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userstory", userStory);
		model.addAttribute("user", userStory.getOwner());
		
		try {
			mav.setViewName("activeWB");
			
			
			List<DataElement> dataElements = dataElementDao.getDataElements(userStory);
	 		for (DataElement dataElement : dataElements) {
	 			if (dataElement.getClass().equals(DataElementFile.class)) {
	 				((DataElementFile)dataElement).generateStringContent();
	 			}
	 			else if (dataElement.getClass().equals(DataElementEngineeringModel.class)) {
	 				((DataElementEngineeringModel)dataElement).generateDistinctDataList();
	 			}
			}
	 		mav.addObject("dataelements", dataElements);
	 		
	 		// Empty data element to use as a "New Data Element"
	 		mav.addObject(new DataElement());

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
	
	public static final String ERR_ALREADY_CURRENT_WORKBOARD = "There is already a current workboard. Delete it or make a User Story before creating a new Workboard";
	public static final String ERR_RETRIEVE_WORKBOARD = "Impossible to retrieve your Workboard";
	public static final String ERR_DELETE_DATA_ELEMENT = "The Data Element could not be deleted";
	public static final String ERR_FILE_UPLOAD = "Unable to upload the file to your workboard";
	public static final String ERR_INVALID_FILE_FORMAT = "Invalid file format. The type of the file you tried to upload is not allowed";
	
	public static final String MSG_CSIRO_DATA_ADDED = "The CSIRO Data has been added successfully to your workboard";
	public static final String MSG_DATA_ELEMENT_DELETED = "The Data Element was deleted successfully from your Workboard";
	public static final String MSG_WORKBOARD_SAVED = "Workboard saved";
	public static final String MSG_FILE_UPLOAD_SUCCESS = "The file was uploaded successfully to your workboard";
	
	
}