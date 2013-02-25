package database;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import war.model.*;

@SuppressWarnings("deprecation")
public class TestDatabaseLoader {

	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("database/hibernate-test.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	

		// Regions & Ports
		Region r1 = new Region();
		r1.setName("East Coast South");
		Seaport port1 = new Seaport();
		port1.setName("Port Kembla");
		port1.setRegion(r1);
		Seaport port2 = new Seaport();
		port2.setName("Gladstone");
		port2.setRegion(r1);
		Seaport port3 = new Seaport();
		port3.setName("");
		port3.setRegion(r1);

		Region r2 = new Region();
		r2.setName("Western Australia");
		Seaport port4 = new Seaport();
		port4.setName("Albany");
		port4.setRegion(r2);
		Seaport port5 = new Seaport();
		port5.setName("Geraldton");
		port5.setRegion(r2);
		
		session.save(r1);
		session.save(port1);
		session.save(port2);
		session.save(port3);
		session.save(r2);
		session.save(port4);
		session.save(port5);
		
		
		// Add Users
		User p1 = new User("testuser1", "password", "testuser1", "testuser1", User.Privilege.USER);
		User p2 = new User("testuser2", "password", "testuser2", "testuser2", User.Privilege.USER);
		User p3 = new User("testuser3", "password", "testuser3", "testuser3", User.Privilege.USER);
		User p4 = new User("testuser4", "password", "testuser4", "testuser4", User.Privilege.USER);
		User p5 = new User("testadmin1", "password", "testadmin1", "testadmin1", User.Privilege.ADMIN);
		User p6 = new User("testadmin2", "password", "testadmin2", "testadmin2", User.Privilege.ADMIN);
		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);
		session.save(p6);
		
		// Add Workboards & User Stories
		UserStory user1wb = new UserStory();
		user1wb.setName("User 1 WorkBoard");
		user1wb.setMode("active");
		user1wb.setAccess("private");
		user1wb.setOwner(p1);
		user1wb.setRegion(r1);
		
		UserStory user1us1 = new UserStory();
		user1us1.setName("User 1 Story 1");
		user1us1.setMode("passive");
		user1us1.setAccess("private");
		user1us1.setOwner(p1);
		user1us1.setRegion(r2);
		
		UserStory user1us2 = new UserStory();
		user1us2.setName("User 1 Story 2 (Public)");
		user1us2.setMode("passive");
		user1us2.setAccess("public");
		user1us2.setOwner(p1);
		user1us2.setRegion(r1);
		
		UserStory user1us3 = new UserStory();
		user1us3.setName("User 1 Story 3 (Published)");
		user1us3.setMode("published");
		user1us3.setAccess("public");
		user1us3.setOwner(p1);
		user1us3.setRegion(r1);
		
		UserStory user2wb = new UserStory();
		user2wb.setName("User 2 Workboard (Empty)");
		user2wb.setMode("active");
		user2wb.setAccess("private");
		user2wb.setOwner(p2);
		user2wb.setRegion(r2);
		
		UserStory user2us = new UserStory();
		user2us.setName("User 2 Story (Empty)");
		user2us.setMode("passive");
		user2us.setAccess("private");
		user2us.setOwner(p2);
		user2us.setRegion(r1);
		
		session.save(user1wb);
		session.save(user1us1);
		session.save(user1us2);
		session.save(user1us3);
		session.save(user2wb);
		session.save(user2us);
		
		// Add Data Elements
		
	    Date date = new Date();
	    String content = "This is a test for Data Element";
	    DataElementFile de1 = new DataElementFile(date, "Test 1", true, 0, user1wb, "csv", content.getBytes());
	   
	    content = "This is the second test";
	    DataElementFile de2 = new DataElementFile(date, "Test 2", true, 0, user1wb, "xml", content.getBytes());
		
	    content = "This is the third test";
	    DataElementFile de3 = new DataElementFile(date, "Test 3", true, 0, user1wb, "txt", content.getBytes());
		
	    content = "User Story's Data Element content test";
	    
	    DataElementFile de4 = new DataElementFile(date, "Test 4", true, 1, user1us1, "txt", content.getBytes());
	    DataElementText text1 = new DataElementText(date, "Comment text 1", true, 2, user1us1, "This is a text comment");
	    DataElementFile de5 = new DataElementFile(date, "Test 5", true, 3, user1us1, "txt", content.getBytes());
	    DataElementText text2 = new DataElementText(date, "Comment text 2", true, 4, user1us1, "This is a second text comment");
		
	    DataElementFile de6 = new DataElementFile(date, "Test 6", true, 1, user1us2, "txt", content.getBytes());
	    
		session.save(de1);
		session.save(de2);
		session.save(de3);
		session.save(de4);
		session.save(text1);
		session.save(de5);
		session.save(text2);
		session.save(de6);
		
		// Climate models
		ArrayList<ClimateModel> modelsList = new ArrayList<ClimateModel>();
		ClimateModel csiro_mk3_5 = new ClimateModel("csiro_mk3_5", "CSIRO Mk3.5", "The CSIRO MK 3.5 climate model");
		modelsList.add(csiro_mk3_5);
		ClimateModel mri_cgcm2_3_2 = new ClimateModel("mri_cgcm2_3_2", "MRI-CGCM2.3.2", "The MRI-CGCM 2.3.2 climate model");
		modelsList.add(mri_cgcm2_3_2);
		ClimateModel ipsl_cm4 = new ClimateModel("ipsl_cm4", "IPSL CM4", "The IPSL CM4 climate model");
		modelsList.add(ipsl_cm4);
		ClimateModel miroc_3_2_medres = new ClimateModel("miroc_3_2_medres", "MIROC-Medres", "The Miroc 3.2 MedRes climate model"); 
		modelsList.add(miroc_3_2_medres);
		ClimateModel reference = new ClimateModel("reference", "(Reference)", "Reference climate model: considering there is no change"); 
		session.save(reference);
		for (ClimateModel model : modelsList) {
			session.save(model);
		}
		
		// Climate emission scenarios
		ArrayList<ClimateEmissionScenario> scenariosList = new ArrayList<ClimateEmissionScenario>();
		ClimateEmissionScenario A1B = new ClimateEmissionScenario("A1B", "Medium CO2 emissions");
		scenariosList.add(A1B);
		ClimateEmissionScenario A1FI = new ClimateEmissionScenario("A1FI", "High CO2 emissions");
		scenariosList.add(A1FI);
		ClimateEmissionScenario base = new ClimateEmissionScenario("Base", "No CO2 emissions increase");
		session.save(base);
		for (ClimateEmissionScenario scenario : scenariosList) {
			session.save(scenario);
		}
		
		// Climate Variables
		ArrayList<CsiroVariable> climVarsList = new ArrayList<CsiroVariable>();
		climVarsList.add(new CsiroVariable("Temperature", "T", "Forecasted hange of temperature from now", "�C"));
		climVarsList.add(new CsiroVariable("Wind speed", "WS", "Forecasted wind speed", "%"));
		climVarsList.add(new CsiroVariable("Rainfall", "RF", "Forecasted rain fall", "%"));
		climVarsList.add(new CsiroVariable("Relative humidity", "RH", "Forecasted relative humidity", "%"));
		for (CsiroVariable variable : climVarsList) {
			session.save(variable);
		}
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
		for (EngineeringModelVariable variable : engVarsList) {
			session.save(variable);
		}
		
		// Climate Parameters
		ArrayList<ClimateParams> paramsList = new ArrayList<ClimateParams>();
		paramsList.add(new ClimateParams(r1, csiro_mk3_5, "Hotter and Drier", A1B));
		paramsList.add(new ClimateParams(r1, csiro_mk3_5, "Hotter and Drier", A1FI));
		paramsList.add(new ClimateParams(r1, ipsl_cm4, "Most Likely", A1B));
		paramsList.add(new ClimateParams(r1, ipsl_cm4, "Most Likely", A1FI));
		paramsList.add(new ClimateParams(r1, miroc_3_2_medres, "Colder and Wetter", A1B));
		paramsList.add(new ClimateParams(r1, miroc_3_2_medres, "Colder and Wetter", A1FI));
		
		paramsList.add(new ClimateParams(r2, miroc_3_2_medres, "Hotter and Drier", A1B));
		paramsList.add(new ClimateParams(r2, miroc_3_2_medres, "Hotter and Drier", A1FI));
		paramsList.add(new ClimateParams(r2, csiro_mk3_5, "Most Likely", A1B));
		paramsList.add(new ClimateParams(r2, csiro_mk3_5, "Most Likely", A1FI));
		paramsList.add(new ClimateParams(r2, ipsl_cm4, "Colder and Wetter", A1B));
		paramsList.add(new ClimateParams(r2, ipsl_cm4, "Colder and Wetter", A1FI));
		
		for (ClimateParams parameter : paramsList) {
			session.save(parameter);
		}
		
		// CSIRO data
		Random randomGenerator = new Random();
		ArrayList<CsiroData> csirodata = new ArrayList<CsiroData>(); 
		for (ClimateParams parameter : paramsList) {
			for (CsiroVariable variable : climVarsList) {
				csirodata.add(new CsiroData(date, parameter, variable, 2030, randomGenerator.nextDouble()));
				csirodata.add(new CsiroData(date, parameter, variable, 2055, randomGenerator.nextDouble()));
				csirodata.add(new CsiroData(date, parameter, variable, 2070, randomGenerator.nextDouble()));
			}
		}
		for (CsiroData data : csirodata) {
			session.save(data);
		}
		
		// CSIRO Baseline data
		CsiroDataBaseline baselineData = new CsiroDataBaseline(date, r1, climVarsList.get(0), 16.1);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r1, climVarsList.get(1), 1.0); // TODO: Replace with actual Value
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r1, climVarsList.get(2), 1029.8);
		session.save(baselineData);
		baselineData = new CsiroDataBaseline(date, r1, climVarsList.get(3), 74.8);
		session.save(baselineData);
		
		ArrayList<CsiroData> csiroDataList = new ArrayList<CsiroData>();
		csiroDataList.add(csirodata.get(0));
		csiroDataList.add(csirodata.get(1));
		csiroDataList.add(csirodata.get(2));
		csiroDataList.add(csirodata.get(3));
		
	    DataElementCsiro de7 = new DataElementCsiro(date, "CSIRO Data Element Test", true, 0, user1wb, csiroDataList);
	    session.save(de7);
	    
	    
	    // Engineering Model Asset
	    EngineeringModelAsset asset = new EngineeringModelAsset("006B", "Pile No 6 @ Berth 08", 1991, "atmospheric", 2.0, "C2", "CB3", "CL1", 60.0, 1300.0, 60.0, 0.37, 300.0, 13.0);
	    session.save(asset);
	    
	    // Engineering Model Data
		ArrayList<EngineeringModelData> engineeringModelDataList = new ArrayList<EngineeringModelData>();
		
		for (ClimateParams parameter : paramsList) {
			if (parameter.getRegion().getName().equals("East Coast South")) {
				
				Map<Integer, Float> values = new HashMap<Integer, Float>();
				for (int year = 2000; year <= 2070; year++) {
					values.put(year, (float)(randomGenerator.nextInt(10)));
				}
				EngineeringModelData data = new EngineeringModelData(asset, parameter, engVarsList.get(0), values);
				session.save(data);
				engineeringModelDataList.add(data);
			}
		}
		DataElementEngineeringModel de8 = new DataElementEngineeringModel(date, "Data Element for " + engVarsList.get(0), true, 0, user1wb, engineeringModelDataList);
	    session.save(de8);
	    
		// Commit all the transaction
		session.getTransaction().commit();
	}
}
