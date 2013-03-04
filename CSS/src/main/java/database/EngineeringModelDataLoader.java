package database;

import helpers.EngineeringModelHelper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import war.controller.UserController;
import war.controller.WorkboardController;
import war.dao.ClimateEmissionScenarioDao;
import war.dao.ClimateModelDao;
import war.dao.ClimateParamsDao;
import war.dao.EngineeringModelAssetDao;
import war.dao.EngineeringModelDataDao;
import war.dao.EngineeringModelVariableDao;
import war.dao.RegionDao;
import war.model.*;

@SuppressWarnings("deprecation")
public class EngineeringModelDataLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static String REGION1 = "East Coast South";
	private static String REGION2 = "Southern Slopes Vic East";
	private static String REGION3 = "Southern and South-Western Flatlands";
	
	@Autowired
	private static RegionDao regionDao;
	
	@Autowired
	private static EngineeringModelVariableDao engineeringModelVariableDao;
	
	@Autowired
	private static EngineeringModelAssetDao engineeringModelAssetDao;
	
	@Autowired
	private static EngineeringModelDataDao engineeringModelDataDao;
	
	@Autowired
	private static ClimateEmissionScenarioDao climateEmissionScenarioDao;
	
	@Autowired
	private static ClimateModelDao climateModelDao;
	
	@Autowired
	private static ClimateParamsDao climateParamsDao;
	
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("database/hibernate.cfg.xml");
		//new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	
		
		LoadEngineeringModelData(session);
		
		session.getTransaction().commit();
	}
	
	public static void LoadEngineeringModelData(Session session)
	{
		// Engineering variables
		ArrayList<EngineeringModelVariable> engVarsList = new ArrayList<EngineeringModelVariable>();
		// Chloride
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation of reinforcing bar probability", "Pint", "Corrosion initiation of reinforcing bar probability", "Chloride", "")); // CY
		engVarsList.add(new EngineeringModelVariable("Change in chloride concentration at concrete cover depth", "C(h,t)", "Change in chloride concentration at concrete cover depth", "Chloride", "kg/m&sup3;")); // CW
		engVarsList.add(new EngineeringModelVariable("Change in corrosion rate of reinforcing bar", "icorr (t)", "Change in corrosion rate of reinforcing bar", "Chloride", "&#181;A/cm&sup2;")); // DB
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation time", "ti", "Corrosion initiation time", "Chloride", "yr")); // EN
		engVarsList.add(new EngineeringModelVariable("Crack propagation time", "tp", "Crack propagation time", "Chloride", "yr")); // EO
		engVarsList.add(new EngineeringModelVariable("Time to severe cracking (1mm crack width)", "tcr", "Time to severe cracking (1mm crack width)", "Chloride", "yr")); // EP
		engVarsList.add(new EngineeringModelVariable("Chloride induced corrosion probability to severe cracking", "Pinit x Pcrack", "Chloride induced corrosion probability to severe cracking", "Chloride", "")); // ER
		engVarsList.add(new EngineeringModelVariable("Reduction or loss in reinforcing bar", "Rebar loss", "Reduction or loss in reinforcing bar", "Chloride", "mm")); // ES
		// Carbonation
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation of reinforcing bar probability", "Pint", "Corrosion initiation of reinforcing bar probability ", "Carbonation", "")); // BS
		engVarsList.add(new EngineeringModelVariable("Change in carbonation depth", "&#916; xc(t')", "Change in carbonation depth", "Carbonation", "mm")); // BQ
		engVarsList.add(new EngineeringModelVariable("Change in corrosion rate of reinforcing bar", "icorr(t')", "Change in corrosion rate of reinforcing bar", "Carbonation", "&#181;A/cm&sup2;")); // BY
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation time", "ti", "Corrosion initiation time", "Carbonation", "yr")); // EH
		engVarsList.add(new EngineeringModelVariable("Crack propagation time", "tp", "Crack propagation time", "Carbonation", "yr")); // EI
		engVarsList.add(new EngineeringModelVariable("Time to severe cracking (1mm crack width)", "tcr", "Time to severe cracking (1mm crack width)", "Carbonation", "yr")); // EJ
		engVarsList.add(new EngineeringModelVariable("Carbonation induced corrosion probability to severe cracking", "Pinit x Pcrack", "Carbonation induced corrosion probability to severe cracking", "Carbonation", "")); // EL
		engVarsList.add(new EngineeringModelVariable("Reduction or loss in reinforcing bar", "Rebar loss", "Reduction or loss in reinforcing bar", "Carbonation", "mm")); // EM
		
		for (EngineeringModelVariable engVar : engVarsList) {
			session.save(engVar);
		}
		
		/*LoadExampleData(session, regionDao.find(REGION1), engVarsList);
		LoadExampleData(session, regionDao.find(REGION2), engVarsList);
		LoadExampleData(session, regionDao.find(REGION3), engVarsList);*/
	}
	
	/**
	 * Reads example Excel files and loads the data into the database for each variable
	 * @param region: the region for which the data needs to be loaded
	 * @param engVarsList: the list of variables to extract data from
	 */
	/*private static void LoadExampleData(Session session, Region region, List<EngineeringModelVariable> engVarsList) {
		for (EngineeringModelVariable engVar : engVarsList) {	
			try {
	    		EngineeringModelVariable engVariable = null;
				try {
					engVariable = engineeringModelVariableDao.find(engVar.getName(), engVar.getCategory());
				}
				catch (NoResultException e) {
					logger.info(e.getMessage() + "(" + engVar.getName() + ")");
					throw (e);
				}
				
    	    	File file = new File("example_data_" + region.getName() + ".xls");
				byte[] bytes = new byte[(int)file.length()];
    	    	ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

    	    	HSSFWorkbook workbook = new HSSFWorkbook(bis);
    	    	
    			// Extract & save Engineering Model Asset
    			EngineeringModelAsset asset = extractEngineeringOutputAsset(workbook);
    	    	session.save(asset);
    	    	
    	    	// Extract and save Engineering Model Data
    	    	List<EngineeringModelData> extractedDataList = extractEngineeringOutputData(workbook, region, engVariable, asset);
    			if (extractedDataList != null && extractedDataList.size() > 0) {
    				for (EngineeringModelData data : extractedDataList) {
    					session.save(data);
    				}
    			}
    			else {
    				throw new NoResultException(WorkboardController.ERR_NO_DATA_ENG_MODEL);
    			}
	        }
	        catch (Exception e) {
	        	logger.error(e.getMessage());
	        }
		}
	}*/
	
	/**
	 * Extracts and construct an engineering model asset from the given Excel sheet
	 * @return EngineeringModelAsset: the asset object extracted from the file
	 */
	/*private static EngineeringModelAsset extractEngineeringOutputAsset(HSSFWorkbook workbook) {
		
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
	}*/

	/**
	 * Extracts and construct an engineering model asset from the given Excel sheet, then saves it in the database
	 * @return EngineeringModelAsset: the asset object extracted from the file
	 */
	/*private static List<EngineeringModelData> extractEngineeringOutputData(HSSFWorkbook workbook, Region region, EngineeringModelVariable engVariable, EngineeringModelAsset asset) {
    	
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
       			climateParams = climateParamsDao.find(region.getName(), emissionScenarioName, climateModelName);
       		}
            catch (NoResultException e) {
            	logger.info("Could not find climate params in the database: Emission Scenario: " + emissionScenarioName + ", Climate Model: " + climateModelName);
            	
            	// If the set of parameters is not found, create it
            	climateParams = new ClimateParams(region, climateModel, climateModelName, emissionScenario);
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
	}*/
}
