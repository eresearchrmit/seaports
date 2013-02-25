package database;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import war.model.*;

@SuppressWarnings("deprecation")
public class CsiroDataLoader {

	public static final String HOTTER_DRIER = "Hotter and Drier";
	public static final String MOST_LIKELY = "Most Likely";
	public static final String COOLER_WETTER = "Cooler and Wetter";
	
	public static void main(String[] args) {
		AnnotationConfiguration config = new AnnotationConfiguration();
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
		// Regions & Ports
		Region r1 = new Region("East Coast South");
		Region r2 = new Region("Southern Slopes Vic East");
		Region r3 = new Region("Southern and South-Western Flatlands");
		Region r4 = new Region("Monsoonal North");
		Region r5 = new Region("Wet Tropics");
		Region r6 = new Region("Rangelands");
		Region r7 = new Region("Central Slopes");
		Region r8 = new Region("Murray Basin");
		session.save(r1);
		session.save(r2);
		session.save(r3);
		session.save(r4);
		session.save(r5);
		session.save(r6);
		session.save(r7);
		session.save(r8);
		//Seaport port1 = new Seaport("Port Kembla", r1);
		//Seaport port2 = new Seaport("Gladstone", r1);
		// Western Australia: Albany & Geraldton
		
		
		// Climate models
		ClimateModel csiro_mk3_5 = new ClimateModel("csiro_mk3_5", "CSIRO Mk3.5", "The CSIRO MK 3.5 climate model");
		session.save(csiro_mk3_5);
		ClimateModel mri_cgcm2_3_2 = new ClimateModel("mri_cgcm2_3_2", "MRI-CGCM2.3.2", "The MRI-CGCM 2.3.2 climate model");
		session.save(mri_cgcm2_3_2);
		ClimateModel ipsl_cm4 = new ClimateModel("ipsl_cm4", "IPSL CM4", "The IPSL CM4 climate model");
		session.save(ipsl_cm4);
		ClimateModel miroc_3_2_medres = new ClimateModel("miroc_3_2_medres", "MIROC-Medres", "The Miroc 3.2 MedRes climate model"); 
		session.save(miroc_3_2_medres);
		ClimateModel cccma_cgcm3_1_t63 = new ClimateModel("cccma_cgcm3_1_t63", "CCCMA CGCM 3.1 T63", "The CCCMA CGCM 3.1 T63 climate model"); 
		session.save(cccma_cgcm3_1_t63);
		ClimateModel reference = new ClimateModel("reference", "(Reference)", "Reference climate model: considering there is no change"); 
		session.save(reference);
		
		// Climate emission scenarios
		ClimateEmissionScenario A1B = new ClimateEmissionScenario("A1B", "Medium CO2 emissions");
		session.save(A1B);
		ClimateEmissionScenario A1FI = new ClimateEmissionScenario("A1FI", "High CO2 emissions");
		session.save(A1FI);
		ClimateEmissionScenario base = new ClimateEmissionScenario("Base", "No CO2 emissions increase");
		session.save(base);
		
		// Climate Variables
		CsiroVariable te = new CsiroVariable("Temperature", "T", "Forecasted hange of temperature from now", "�C", "�C");
		CsiroVariable ws = new CsiroVariable("Wind speed", "WS", "Forecasted wind speed", "km/h");
		CsiroVariable rf = new CsiroVariable("Rainfall", "RF", "Forecasted rain fall", "mm/y");
		CsiroVariable rh = new CsiroVariable("Relative humidity", "RH", "Forecasted relative humidity", "%");
		session.save(te);
		session.save(ws);
		session.save(rf);
		session.save(rh);
		
		Date date = new Date();
		
		// Baseline data for the 3 regions
		CsiroDataBaseline baselineData = new CsiroDataBaseline(date, r1, te, 16.1);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r1, ws, 4.6);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r1, rf, 1029.8);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r1, rh, 74.8);
		session.save(baselineData);
		
		baselineData = new CsiroDataBaseline(date, r2, te, 12.8);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r2, ws, 5.3);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r2, rf, 870.0);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r2, rh, 71.3);
		session.save(baselineData);
		
		baselineData = new CsiroDataBaseline(date, r3, te, 17.6);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r3, ws, 5.4);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r3, rf, 441.4);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r3, rh, 59.2);
		session.save(baselineData);
		
		// Climate Parameters & Data
		
		ClimateParams params = new ClimateParams(r1, csiro_mk3_5, HOTTER_DRIER, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.3));
		session.save(new CsiroData(date, params, rf, 2030, -4.70));
		session.save(new CsiroData(date, params, ws, 2030, 0.00));
		session.save(new CsiroData(date, params, rh, 2030, -1.30));
		session.save(new CsiroData(date, params, te, 2055, 2.4));
		session.save(new CsiroData(date, params, rf, 2055, -8.80));
		session.save(new CsiroData(date, params, ws, 2055, 0.00));
		session.save(new CsiroData(date, params, rh, 2055, -5.60));
		session.save(new CsiroData(date, params, te, 2070, 3.0));
		session.save(new CsiroData(date, params, rf, 2070, -11.10));
		session.save(new CsiroData(date, params, ws, 2070, 0.10));
		session.save(new CsiroData(date, params, rh, 2070, -7.10));
		
		params = new ClimateParams(r1, csiro_mk3_5, HOTTER_DRIER, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.2));
		session.save(new CsiroData(date, params, rf, 2030, -4.50));
		session.save(new CsiroData(date, params, ws, 2030, 0.00));
		session.save(new CsiroData(date, params, rh, 2030, -2.90));
		session.save(new CsiroData(date, params, te, 2055, 3.0));
		session.save(new CsiroData(date, params, rf, 2055, -10.90));
		session.save(new CsiroData(date, params, ws, 2055, 0.10));
		session.save(new CsiroData(date, params, rh, 2055, -7.00));
		session.save(new CsiroData(date, params, te, 2070, 4.1));
		session.save(new CsiroData(date, params, rf, 2070, -15.10));
		session.save(new CsiroData(date, params, ws, 2070, 0.10));
		session.save(new CsiroData(date, params, rh, 2070, -9.70));
		
		
		params = new ClimateParams(r1, ipsl_cm4, MOST_LIKELY, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.0));
		session.save(new CsiroData(date, params, rf, 2030, -4.50));
		session.save(new CsiroData(date, params, ws, 2030, -2.10));
		session.save(new CsiroData(date, params, rh, 2030, -0.20));
		session.save(new CsiroData(date, params, te, 2055, 2.0));
		session.save(new CsiroData(date, params, rf, 2055, -8.40));
		session.save(new CsiroData(date, params, ws, 2055, -4.00));
		session.save(new CsiroData(date, params, rh, 2055, -0.40));
		session.save(new CsiroData(date, params, te, 2070, 2.5));
		session.save(new CsiroData(date, params, rf, 2070, -10.60));
		session.save(new CsiroData(date, params, ws, 2070, -5.00));
		session.save(new CsiroData(date, params, rh, 2070, -0.50));
		
		params = new ClimateParams(r1, ipsl_cm4, MOST_LIKELY, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.0));
		session.save(new CsiroData(date, params, rf, 2030, -4.30));
		session.save(new CsiroData(date, params, ws, 2030, -2.00));
		session.save(new CsiroData(date, params, rh, 2030, -0.20));
		session.save(new CsiroData(date, params, te, 2055, 2.4));
		session.save(new CsiroData(date, params, rf, 2055, -10.40));
		session.save(new CsiroData(date, params, ws, 2055, -4.90));
		session.save(new CsiroData(date, params, rh, 2055, -0.50));
		session.save(new CsiroData(date, params, te, 2070, 3.4));
		session.save(new CsiroData(date, params, rf, 2070, -14.40));
		session.save(new CsiroData(date, params, ws, 2070, -6.80));
		session.save(new CsiroData(date, params, rh, 2070, -0.70));
		
		params = new ClimateParams(r1, miroc_3_2_medres, COOLER_WETTER, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.7));
		session.save(new CsiroData(date, params, rf, 2030, 4.10));
		session.save(new CsiroData(date, params, ws, 2030, -1.30));
		session.save(new CsiroData(date, params, rh, 2030, 1.20));
		session.save(new CsiroData(date, params, te, 2055, 1.3));
		session.save(new CsiroData(date, params, rf, 2055, 7.70));
		session.save(new CsiroData(date, params, ws, 2055, -2.40));
		session.save(new CsiroData(date, params, rh, 2055, 2.30));
		session.save(new CsiroData(date, params, te, 2070, 1.7));
		session.save(new CsiroData(date, params, rf, 2070, 9.70));
		session.save(new CsiroData(date, params, ws, 2070, -3.00));
		session.save(new CsiroData(date, params, rh, 2070, 2.90));
		
		params = new ClimateParams(r1, miroc_3_2_medres, COOLER_WETTER, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.7));
		session.save(new CsiroData(date, params, rf, 2030, 4.00));
		session.save(new CsiroData(date, params, ws, 2030, -1.20));
		session.save(new CsiroData(date, params, rh, 2030, 1.20));
		session.save(new CsiroData(date, params, te, 2055, 1.7));
		session.save(new CsiroData(date, params, rf, 2055, 9.50));
		session.save(new CsiroData(date, params, ws, 2055, -2.90));
		session.save(new CsiroData(date, params, rh, 2055, 2.90));
		session.save(new CsiroData(date, params, te, 2070, 2.3));
		session.save(new CsiroData(date, params, rf, 2070, 13.20));
		session.save(new CsiroData(date, params, ws, 2070, -4.10));
		session.save(new CsiroData(date, params, rh, 2070, 4.00));
		
		
		
		
		
		
		params = new ClimateParams(r2, csiro_mk3_5, HOTTER_DRIER, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.1));
		session.save(new CsiroData(date, params, rf, 2030, -4.50));
		session.save(new CsiroData(date, params, ws, 2030, -1.60));
		session.save(new CsiroData(date, params, rh, 2030, -2.20));
		session.save(new CsiroData(date, params, te, 2055, 2.0));
		session.save(new CsiroData(date, params, rf, 2055, -8.50));
		session.save(new CsiroData(date, params, ws, 2055, -3.10));
		session.save(new CsiroData(date, params, rh, 2055, -4.20));
		session.save(new CsiroData(date, params, te, 2070, 2.6));
		session.save(new CsiroData(date, params, rf, 2070, -10.80));
		session.save(new CsiroData(date, params, ws, 2070, -3.90));
		session.save(new CsiroData(date, params, rh, 2070, -5.30));
		
		params = new ClimateParams(r2, csiro_mk3_5, HOTTER_DRIER, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.0));
		session.save(new CsiroData(date, params, rf, 2030, -4.40));
		session.save(new CsiroData(date, params, ws, 2030, -1.60));
		session.save(new CsiroData(date, params, rh, 2030, -2.10));
		session.save(new CsiroData(date, params, te, 2055, 2.5));
		session.save(new CsiroData(date, params, rf, 2055, -10.60));
		session.save(new CsiroData(date, params, ws, 2055, -3.80));
		session.save(new CsiroData(date, params, rh, 2055, -5.20));
		session.save(new CsiroData(date, params, te, 2070, 3.5));
		session.save(new CsiroData(date, params, rf, 2070, -14.70));
		session.save(new CsiroData(date, params, ws, 2070, -5.30));
		session.save(new CsiroData(date, params, rh, 2070, -7.20));
		
		
		params = new ClimateParams(r2, ipsl_cm4, MOST_LIKELY, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.0));
		session.save(new CsiroData(date, params, rf, 2030, -4.10));
		session.save(new CsiroData(date, params, ws, 2030, -2.70));
		session.save(new CsiroData(date, params, rh, 2030, -0.80));
		session.save(new CsiroData(date, params, te, 2055, 1.9));
		session.save(new CsiroData(date, params, rf, 2055, -7.70));
		session.save(new CsiroData(date, params, ws, 2055, -5.00));
		session.save(new CsiroData(date, params, rh, 2055, -1.60));
		session.save(new CsiroData(date, params, te, 2070, 2.4));
		session.save(new CsiroData(date, params, rf, 2070, -9.80));
		session.save(new CsiroData(date, params, ws, 2070, -6.40));
		session.save(new CsiroData(date, params, rh, 2070, -2.00));
		
		params = new ClimateParams(r2, ipsl_cm4, MOST_LIKELY, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.0));
		session.save(new CsiroData(date, params, rf, 2030, -4.00));
		session.save(new CsiroData(date, params, ws, 2030, -2.60));
		session.save(new CsiroData(date, params, rh, 2030, -0.80));
		session.save(new CsiroData(date, params, te, 2055, 2.4));
		session.save(new CsiroData(date, params, rf, 2055, -9.60));
		session.save(new CsiroData(date, params, ws, 2055, -6.30));
		session.save(new CsiroData(date, params, rh, 2055, -2.00));
		session.save(new CsiroData(date, params, te, 2070, 3.3));
		session.save(new CsiroData(date, params, rf, 2070, -13.30));
		session.save(new CsiroData(date, params, ws, 2070, -8.70));
		session.save(new CsiroData(date, params, rh, 2070, -2.70));
		
		params = new ClimateParams(r2, miroc_3_2_medres, COOLER_WETTER, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.6));
		session.save(new CsiroData(date, params, rf, 2030, 2.00));
		session.save(new CsiroData(date, params, ws, 2030, 0.10));
		session.save(new CsiroData(date, params, rh, 2030, 0.20));
		session.save(new CsiroData(date, params, te, 2055, 1.2));
		session.save(new CsiroData(date, params, rf, 2055, 3.70));
		session.save(new CsiroData(date, params, ws, 2055, 0.20));
		session.save(new CsiroData(date, params, rh, 2055, 0.30));
		session.save(new CsiroData(date, params, te, 2070, 1.5));
		session.save(new CsiroData(date, params, rf, 2070, 4.60));
		session.save(new CsiroData(date, params, ws, 2070, 0.20));
		session.save(new CsiroData(date, params, rh, 2070, 0.40));
		
		params = new ClimateParams(r2, miroc_3_2_medres, COOLER_WETTER, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.6));
		session.save(new CsiroData(date, params, rf, 2030, 1.90));
		session.save(new CsiroData(date, params, ws, 2030, 0.10));
		session.save(new CsiroData(date, params, rh, 2030, 0.20));
		session.save(new CsiroData(date, params, te, 2055, 1.5));
		session.save(new CsiroData(date, params, rf, 2055, 4.60));
		session.save(new CsiroData(date, params, ws, 2055, 0.20));
		session.save(new CsiroData(date, params, rh, 2055, 0.40));
		session.save(new CsiroData(date, params, te, 2070, 2.0));
		session.save(new CsiroData(date, params, rf, 2070, 6.30));
		session.save(new CsiroData(date, params, ws, 2070, 0.30));
		session.save(new CsiroData(date, params, rh, 2070, 0.60));
		
		
		
		
		
		

		params = new ClimateParams(r3, miroc_3_2_medres, HOTTER_DRIER, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.7));
		session.save(new CsiroData(date, params, rf, 2030, -6.7));
		session.save(new CsiroData(date, params, ws, 2030, 1.10));
		session.save(new CsiroData(date, params, rh, 2030, -0.9));
		session.save(new CsiroData(date, params, te, 2055, 1.2));
		session.save(new CsiroData(date, params, rf, 2055, -12.6));
		session.save(new CsiroData(date, params, ws, 2055, 2.1));
		session.save(new CsiroData(date, params, rh, 2055, -1.8));
		session.save(new CsiroData(date, params, te, 2070, 2.6));
		session.save(new CsiroData(date, params, rf, 2070, -16.00));
		session.save(new CsiroData(date, params, ws, 2070, 2.6));
		session.save(new CsiroData(date, params, rh, 2070, -2.3));
		
		params = new ClimateParams(r3, miroc_3_2_medres, HOTTER_DRIER, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.6));
		session.save(new CsiroData(date, params, rf, 2030, -6.5));
		session.save(new CsiroData(date, params, ws, 2030, 1.1));
		session.save(new CsiroData(date, params, rh, 2030, -0.9));
		session.save(new CsiroData(date, params, te, 2055, 1.5));
		session.save(new CsiroData(date, params, rf, 2055, -15.7));
		session.save(new CsiroData(date, params, ws, 2055, 2.6));
		session.save(new CsiroData(date, params, rh, 2055, -2.2));
		session.save(new CsiroData(date, params, te, 2070, 2.1));
		session.save(new CsiroData(date, params, rf, 2070, -21.70));
		session.save(new CsiroData(date, params, ws, 2070, 3.6));
		session.save(new CsiroData(date, params, rh, 2070, -3.1));
		
		
		params = new ClimateParams(r3, csiro_mk3_5, MOST_LIKELY, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.1));
		session.save(new CsiroData(date, params, rf, 2030, -6.2));
		session.save(new CsiroData(date, params, ws, 2030, -1.5));
		session.save(new CsiroData(date, params, rh, 2030, -1.5));
		session.save(new CsiroData(date, params, te, 2055, 2.1));
		session.save(new CsiroData(date, params, rf, 2055, -11.6));
		session.save(new CsiroData(date, params, ws, 2055, -2.8));
		session.save(new CsiroData(date, params, rh, 2055, -2.9));
		session.save(new CsiroData(date, params, te, 2070, 2.6));
		session.save(new CsiroData(date, params, rf, 2070, -14.70));
		session.save(new CsiroData(date, params, ws, 2070, -3.60));
		session.save(new CsiroData(date, params, rh, 2070, -3.70));
		
		params = new ClimateParams(r3, csiro_mk3_5, MOST_LIKELY, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 1.1));
		session.save(new CsiroData(date, params, rf, 2030, -6.00));
		session.save(new CsiroData(date, params, ws, 2030, -1.50));
		session.save(new CsiroData(date, params, rh, 2030, -1.50));
		session.save(new CsiroData(date, params, te, 2055, 2.6));
		session.save(new CsiroData(date, params, rf, 2055, -14.5));
		session.save(new CsiroData(date, params, ws, 2055, -3.5));
		session.save(new CsiroData(date, params, rh, 2055, -3.6));
		session.save(new CsiroData(date, params, te, 2070, 3.6));
		session.save(new CsiroData(date, params, rf, 2070, -20.10));
		session.save(new CsiroData(date, params, ws, 2070, -4.90));
		session.save(new CsiroData(date, params, rh, 2070, -5.00));
		
		params = new ClimateParams(r3, cccma_cgcm3_1_t63, COOLER_WETTER, A1B);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.9));
		session.save(new CsiroData(date, params, rf, 2030, 0.8));
		session.save(new CsiroData(date, params, ws, 2030, 0.3));
		session.save(new CsiroData(date, params, rh, 2030, -0.2));
		session.save(new CsiroData(date, params, te, 2055, 1.7));
		session.save(new CsiroData(date, params, rf, 2055, 1.4));
		session.save(new CsiroData(date, params, ws, 2055, 0.5));
		session.save(new CsiroData(date, params, rh, 2055, -0.3));
		session.save(new CsiroData(date, params, te, 2070, 2.1));
		session.save(new CsiroData(date, params, rf, 2070, 1.8));
		session.save(new CsiroData(date, params, ws, 2070, 0.6));
		session.save(new CsiroData(date, params, rh, 2070, -0.4));
		
		params = new ClimateParams(r3, cccma_cgcm3_1_t63, COOLER_WETTER, A1FI);
		session.save(params);
		session.save(new CsiroData(date, params, te, 2030, 0.9));
		session.save(new CsiroData(date, params, rf, 2030, 0.7));
		session.save(new CsiroData(date, params, ws, 2030, 0.3));
		session.save(new CsiroData(date, params, rh, 2030, -0.2));
		session.save(new CsiroData(date, params, te, 2055, 2.1));
		session.save(new CsiroData(date, params, rf, 2055, 1.8));
		session.save(new CsiroData(date, params, ws, 2055, 0.6));
		session.save(new CsiroData(date, params, rh, 2055, -0.4));
		session.save(new CsiroData(date, params, te, 2070, 2.9));
		session.save(new CsiroData(date, params, rf, 2070, 2.5));
		session.save(new CsiroData(date, params, ws, 2070, 0.9));
		session.save(new CsiroData(date, params, rh, 2070, -0.6));
		
	}
}
