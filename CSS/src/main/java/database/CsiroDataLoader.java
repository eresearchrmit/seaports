package database;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import war.model.*;

@SuppressWarnings("deprecation")
public class CsiroDataLoader {

	public static final String HOTTER_DRIER = "Hotter & Drier";
	public static final String MOST_LIKELY = "Most Likely";
	public static final String COOLER_WETTER = "Cooler & Wetter";
	public static final String BASELINE = "Baseline";
	
	public static final String csiroPictureFolderPath = "src/main/java/database/csiro-pictures/";
	public static final String cmarPictureFolderPath = "src/main/java/database/cmar-pictures/";
	public static final String bomPictureFolderPath = "src/main/java/database/bom-pictures/";
	
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	
		
		LoadCsiroData(session);
		
		session.getTransaction().commit();
	}
	
	public static void LoadCsiroData(Session session)
	{
		Region r1 = (Region)(session.get(Region.class, 1));
		Region r2 = (Region)(session.get(Region.class, 2));
		Region r3 = (Region)(session.get(Region.class, 3));
		
		// Climate models
		ClimateModel csiro_mk3_5 = new ClimateModel("csiro_mk3_5", "CSIRO Mk3.5", "The CSIRO Mk3.5 climate model");
		session.save(csiro_mk3_5);
		ClimateModel mri_cgcm2_3_2 = new ClimateModel("mri_cgcm2_3_2", "MRI-CGCM2.3.2", "The MRI-CGCM 2.3.2 climate model");
		session.save(mri_cgcm2_3_2);
		ClimateModel ipsl_cm4 = new ClimateModel("ipsl_cm4", "IPSL-CM4", "The IPSL-CM4 climate model");
		session.save(ipsl_cm4);
		ClimateModel miroc_3_2_medres = new ClimateModel("miroc_3_2_medres", "MIROC 3.2 (medres)", "The Miroc 3.2 MedRes climate model"); 
		session.save(miroc_3_2_medres);
		ClimateModel cccma_cgcm3_1_t63 = new ClimateModel("cccma_cgcm3_1_t63", "CCCMA CGCM 3.1 T63", "The CCCMA CGCM 3.1 T63 climate model"); 
		session.save(cccma_cgcm3_1_t63);
		ClimateModel reference = new ClimateModel("reference", "(Reference)", "Reference climate model: considering there is no change"); 
		session.save(reference);
		
		// Climate emission scenarios
		ClimateEmissionScenario A1B = new ClimateEmissionScenario("A1B", "medium");
		session.save(A1B);
		ClimateEmissionScenario A1FI = new ClimateEmissionScenario("A1FI", "high");
		session.save(A1FI);
		ClimateEmissionScenario base = new ClimateEmissionScenario("Base", "No CO2 emissions increase");
		session.save(base);
		
		// Climate Variables
		CsiroVariable te = new CsiroVariable("Temperature", "T", "Forecasted hange of temperature from now", "&#8451;", "&#8451;");
		CsiroVariable ws = new CsiroVariable("Wind speed", "WS", "Forecasted wind speed", "km/h");
		CsiroVariable rf = new CsiroVariable("Rainfall", "RF", "Forecasted rain fall", "mm/y");
		CsiroVariable rh = new CsiroVariable("Relative humidity", "RH", "Forecasted relative humidity", "%");
		CsiroVariable slr = new CsiroVariable("Sea Level Rise", "SLR", "Forecasted sea level rise", "mm");
		session.save(te);
		session.save(ws);
		session.save(rf);
		session.save(rh);
		session.save(slr);
		
		DateFormat dateFormatter = new SimpleDateFormat("F");
		Date dateCsiroData = null;
		Date dateCmarData = null;
		try {
			dateCsiroData = dateFormatter.parse("2013-02-01");
		} catch (ParseException e) {
			dateCsiroData = new Date();
		}
		try {
			dateCmarData = dateFormatter.parse("2013-03-07");
		} catch (ParseException e) {
			dateCmarData = new Date();
		}
		
		// Baseline data for the 3 regions
		CsiroDataBaseline baselineData = new CsiroDataBaseline(dateCsiroData, r1, te, 16.1);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r1, ws, 4.6);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r1, rf, 1029.8);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r1, rh, 74.8);
		session.save(baselineData);
		
		baselineData = new CsiroDataBaseline(dateCsiroData, r2, te, 12.8);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r2, ws, 5.3);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r2, rf, 870.0);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r2, rh, 71.3);
		session.save(baselineData);
		
		baselineData = new CsiroDataBaseline(dateCsiroData, r3, te, 17.6);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r3, ws, 5.4);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r3, rf, 441.4);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(dateCsiroData, r3, rh, 59.2);
		session.save(baselineData);
		
		// Climate Parameters & Data
		
		File file = null;
		byte[] arrPictureContent = null;
		
		ClimateParams params = new ClimateParams(r1, csiro_mk3_5, HOTTER_DRIER, A1B);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.3, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.70, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 0.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -1.30, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.4, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -8.80, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 0.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -5.60, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 3.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -11.10, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 0.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -7.10, null));
		
		params = new ClimateParams(r1, csiro_mk3_5, HOTTER_DRIER, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.2, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.50, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 0.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -2.90, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 3.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -10.90, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 0.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -7.00, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 4.1, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -15.10, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 0.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -9.70, null));
		
		
		params = new ClimateParams(r1, ipsl_cm4, MOST_LIKELY, A1B);
		session.save(params);
		
		// CMAR Data
		file = new File(cmarPictureFolderPath + "sealevelrise-ecs-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CmarData(dateCmarData, params, slr, 2030, "-34.5,151.5,167;-33.5,152.5,176;-32.5,152.5,168;-31.5,153.5,166;-30.5,153.5,164;-29.5,153.5,163;-28.5,153.5,159", arrPictureContent));
		// CMAR Data
		file = new File(cmarPictureFolderPath + "sealevelrise-ecs-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CmarData(dateCmarData, params, slr, 2070, "-34.5,151.5,437;-33.5,152.5,447;-32.5,152.5,445;-31.5,153.5,439;-30.5,153.5,436;-29.5,153.5,434;-28.5,153.5,429", arrPictureContent));
		
		
		
		file = new File(csiroPictureFolderPath + "temperature-ecs-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.0, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-ecs-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.50, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -2.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.20, null));
		file = new File(csiroPictureFolderPath + "temperature-ecs-2055.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.0, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-ecs-2055.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -8.40, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -4.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -0.40, null));
		file = new File(csiroPictureFolderPath + "temperature-ecs-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.5, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-ecs-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -10.60, arrPictureContent));
		
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -5.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -0.50, null));
		
		params = new ClimateParams(r1, ipsl_cm4, MOST_LIKELY, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.30, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -2.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.4, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -10.40, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -4.90, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -0.50, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 3.4, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -14.40, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -6.80, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -0.70, null));
		
		params = new ClimateParams(r1, miroc_3_2_medres, COOLER_WETTER, A1B);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.7, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, 4.10, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -1.30, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, 1.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.3, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, 7.70, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -2.40, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, 2.30, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 1.7, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, 9.70, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -3.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, 2.90, null));
		
		params = new ClimateParams(r1, miroc_3_2_medres, COOLER_WETTER, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.7, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, 4.00, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -1.20, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, 1.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.7, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, 9.50, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -2.90, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, 2.90, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.3, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, 13.20, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -4.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, 4.00, null));
		
		
		
		
		
		
		params = new ClimateParams(r2, csiro_mk3_5, HOTTER_DRIER, A1B);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.1, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.50, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -1.60, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -2.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -8.50, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -3.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -4.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -10.80, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -3.90, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -5.30, null));
		
		params = new ClimateParams(r2, csiro_mk3_5, HOTTER_DRIER, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.40, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -1.60, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -2.10, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.5, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -10.60, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -3.80, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -5.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 3.5, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -14.70, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -5.30, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -7.20, null));
		
		
		params = new ClimateParams(r2, ipsl_cm4, MOST_LIKELY, A1B);
		session.save(params);
		
		// CMAR Data
		file = new File(cmarPictureFolderPath + "sealevelrise-ssve-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CmarData(dateCmarData, params, slr, 2030, "-39.5,146.5,128;-39.5,147.5,132;-38.5,148.5,156;-38.5,149.5,163;-37.5,150.5,180;-36.5,150.5,176;-35.5,151.5,172;-34.5,151.5,167", arrPictureContent));
		// CMAR Data
		file = new File(cmarPictureFolderPath + "sealevelrise-ssve-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CmarData(dateCmarData, params, slr, 2070, "-39.5,146.5,369;-39.5,147.5,375;-38.5,148.5,416;-38.5,149.5,426;-37.5,150.5,444;-36.5,150.5,440;-35.5,151.5,440;-34.5,151.5,437", arrPictureContent));
		
		file = new File(csiroPictureFolderPath + "temperature-ssve-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.0, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-ssve-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.10, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -2.70, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.80, null));
		file = new File(csiroPictureFolderPath + "temperature-ssve-2055.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.9, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-ssve-2055.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -7.70, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -5.00, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -1.60, null));
		file = new File(csiroPictureFolderPath + "temperature-ssve-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.4, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-ssve-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -9.80, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -6.40, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -2.00, null));
		
		params = new ClimateParams(r2, ipsl_cm4, MOST_LIKELY, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -4.00, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -2.60, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.80, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.4, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -9.60, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -6.30, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -2.00, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 3.3, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -13.30, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -8.70, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -2.70, null));
		
		params = new ClimateParams(r2, miroc_3_2_medres, COOLER_WETTER, A1B);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, 2.00, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 0.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, 0.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.2, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, 3.70, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 0.20, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, 0.30, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 1.5, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, 4.60, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 0.20, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, 0.40, null));
		
		params = new ClimateParams(r2, miroc_3_2_medres, COOLER_WETTER, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, 1.90, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 0.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, 0.20, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.5, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, 4.60, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 0.20, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, 0.40, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.0, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, 6.30, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 0.30, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, 0.60, null));
		
		
		
		
		
		

		params = new ClimateParams(r3, miroc_3_2_medres, HOTTER_DRIER, A1B);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.7, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -6.7, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 1.10, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.9, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.2, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -12.6, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 2.1, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -1.8, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -16.00, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 2.6, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -2.3, null));
		
		params = new ClimateParams(r3, miroc_3_2_medres, HOTTER_DRIER, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -6.5, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 1.1, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.9, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.5, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -15.7, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 2.6, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -2.2, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.1, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -21.70, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 3.6, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -3.1, null));
		
		
		params = new ClimateParams(r3, csiro_mk3_5, MOST_LIKELY, A1B);
		session.save(params);
		
		// CMAR Data
		file = new File(cmarPictureFolderPath + "sealevelrise-sswfw-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CmarData(dateCmarData, params, slr, 2030, "-27.5,113.5,125;-28.5,113.5,124;-29.5,114.5,121;-30.5,114.5,120;-31.5,115.5,119;-32.5,115.5,118;-33.5,114.5,118;-34.5,114.5,117;-34.5,115.5,117;-35.5,116.5,119;-35.5,117.5,119;-35.5,118.5,121;-34.5,119.5,118;-34.5,120.5,118;-34.5,121.5,123;-34.5,122.5,123;-34.5,123.5,124;-33.5,124.5,120", arrPictureContent));
		// CMAR Data
		file = new File(cmarPictureFolderPath + "sealevelrise-sswfw-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CmarData(dateCmarData, params, slr, 2070, "-27.5,113.5,370;-28.5,113.5,369;-29.5,114.5,365;-30.5,114.5,363;-31.5,115.5,361;-32.5,115.5,360;-33.5,114.5,360;-34.5,114.5,360;-34.5,115.5,361;-35.5,116.5,363;-35.5,117.5,363;-35.5,118.5,364;-34.5,119.5,354;-34.5,120.5,354;-34.5,121.5,361;-34.5,122.5,360;-34.5,123.5,360;-33.5,124.5,354", arrPictureContent));
		
		file = new File(csiroPictureFolderPath + "temperature-sswfw-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.1, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-sswfw-2030.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -6.2, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -1.5, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -1.5, null));
		file = new File(csiroPictureFolderPath + "temperature-sswfw-2055.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.1, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-sswfw-2055.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -11.6, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -2.8, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -2.9, null));
		file = new File(csiroPictureFolderPath + "temperature-sswfw-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.6, arrPictureContent));
		file = new File(csiroPictureFolderPath + "rainfall-sswfw-2070.png");
		try { arrPictureContent = FileUtils.readFileToByteArray(file); }
		catch (IOException e) { arrPictureContent = null; e.printStackTrace(); }
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -14.70, arrPictureContent));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -3.60, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -3.70, null));
		
		params = new ClimateParams(r3, csiro_mk3_5, MOST_LIKELY, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 1.1, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, -6.00, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, -1.50, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -1.50, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, -14.5, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, -3.5, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -3.6, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 3.6, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, -20.10, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, -4.90, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -5.00, null));
		
		params = new ClimateParams(r3, cccma_cgcm3_1_t63, COOLER_WETTER, A1B);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.9, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, 0.8, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 0.3, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.2, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 1.7, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, 1.4, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 0.5, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -0.3, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.1, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, 1.8, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 0.6, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -0.4, null));
		
		params = new ClimateParams(r3, cccma_cgcm3_1_t63, COOLER_WETTER, A1FI);
		session.save(params);
		session.save(new CsiroData(dateCsiroData, params, te, 2030, 0.9, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2030, 0.7, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2030, 0.3, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2030, -0.2, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2055, 2.1, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2055, 1.8, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2055, 0.6, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2055, -0.4, null));
		session.save(new CsiroData(dateCsiroData, params, te, 2070, 2.9, null));
		session.save(new CsiroData(dateCsiroData, params, rf, 2070, 2.5, null));
		session.save(new CsiroData(dateCsiroData, params, ws, 2070, 0.9, null));
		session.save(new CsiroData(dateCsiroData, params, rh, 2070, -0.6, null));


		params = new ClimateParams(r1, reference, BASELINE, base);
		session.save(params);
		params = new ClimateParams(r2, reference, BASELINE, base);
		session.save(params);
	}
}
