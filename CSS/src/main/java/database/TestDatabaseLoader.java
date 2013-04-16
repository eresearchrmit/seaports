package database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import security.UserLoginService;

import war.model.*;
import war.model.DataElement.DisplayType;

@SuppressWarnings("deprecation")
public class TestDatabaseLoader {

	// The password correspond to the SHA-256 hash of 'password'
	private static final String DEFAULT_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
	
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate-test.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	

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
		
		Seaport port1 = new Seaport("AUYBA", "Port of Yamba", r1);
		Seaport port2 = new Seaport("AUNTL", "Newcastle Port", r1);
		Seaport port3 = new Seaport("AUSYD", "Sydney Harbour", r1);
		Seaport port4 = new Seaport("AUBTB", "Port of Botany Bay", r1);
		Seaport port5 = new Seaport("AUCFS", "Coffs Harbour", r1);
		
		Seaport port6 = new Seaport("AUBSJ", "Lakes Entrance (Bairnsdale)", r2);
		Seaport port7 = new Seaport("AUPKL", "Port Kembla", r2);
		Seaport port8 = new Seaport("AUQDN", "Port of Eden", r2);
		Seaport port9 = new Seaport("AUXMC", "Port of Mallacoota", r2);
		Seaport port10 = new Seaport("AUWHL", "Port of Corner Inlet & Port Albert (Welshpool)", r2);
		
		Seaport port11 = new Seaport("AUEPR", "Esperance Port", r3);
		Seaport port12 = new Seaport("AUALH", "Albany Port", r3);
		Seaport port13 = new Seaport("AUBUY", "Port of Bunbury", r3);
		Seaport port14 = new Seaport("AUGET", "Port of Geraldton", r3);
		Seaport port15 = new Seaport("AUFRE", "Fremantle", r3);
		
		session.save(port1);
		session.save(port2);
		session.save(port3);
		session.save(port4);
		session.save(port5);
		session.save(port6);
		session.save(port7);
		session.save(port8);
		session.save(port9);
		session.save(port10);
		session.save(port11);
		session.save(port12);
		session.save(port13);
		session.save(port14);
		session.save(port15);
		
		CsiroDataLoader.LoadCsiroData(session);
		EngineeringModelDataLoader.LoadEngineeringModelData(session);
		BomDataLoader.LoadBomData(session);
		AcornSatDataLoader.LoadAcornSatData(session);
		AbsDataLoader.LoadAbsData(session);
		BitreDataLoader.LoadBitreData(session);
		
		// Add Users
		User p1 = new User("testuser1", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		User p2 = new User("testuser2", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser2", "testuser2");
		User p3 = new User("testuser3", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser3", "testuser3");
		User p4 = new User("testuser4", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser4", "testuser4");
		User p5 = new User("testadmin1", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "testadmin1", "testadmin1");
		User p6 = new User("testadmin2", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "testadmin2", "testadmin2");
		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);
		session.save(p6);
		
		
		
		// Add Workboards & User Stories
		UserStory user1wb = new UserStory();
		user1wb.setName("User 1 Workboard");
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
		
		DateFormat dateFormatter = new SimpleDateFormat("F");
		Date datePublish = null;
		try { datePublish = dateFormatter.parse("2013-04-10"); } catch (ParseException e) {}
		
		UserStory user1us3 = new UserStory();
		user1us3.setName("User 1 Story 3 (Published)");
		user1us3.setMode("published");
		user1us3.setAccess("public");
		user1us3.setCreationDate(datePublish);
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
	    DataElementFile de1 = new DataElementFile(date, "Test 1", true, 0, DisplayType.PLAIN, user1wb, "csv", content.getBytes());
	   
	    content = "This is the second test";
	    DataElementFile de2 = new DataElementFile(date, "Test 2", true, 0, DisplayType.PLAIN, user1wb, "xml", content.getBytes());
		
	    content = "This is the third test";
	    DataElementFile de3 = new DataElementFile(date, "Test 3", true, 0, DisplayType.PLAIN, user1wb, "txt", content.getBytes());
		
	    content = "User Story's Data Element content test";
	    
	    DataElementFile de4 = new DataElementFile(date, "Test 4", true, 1, DisplayType.PLAIN, user1us1, "txt", content.getBytes());
	    DataElementText text1 = new DataElementText(date, "Comment text 1", true, 2, DisplayType.PLAIN, user1us1, "This is a text comment");
	    DataElementFile de5 = new DataElementFile(date, "Test 5", true, 3, DisplayType.PLAIN, user1us1, "txt", content.getBytes());
	    DataElementText text2 = new DataElementText(date, "Comment text 2", true, 4, DisplayType.PLAIN, user1us1, "This is a second text comment");
		
	    DataElementFile de6 = new DataElementFile(date, "Test 6", true, 1, DisplayType.PLAIN, user1us2, "txt", content.getBytes());
	    
		session.save(de1);
		session.save(de2);
		session.save(de3);
		session.save(de4);
		session.save(text1);
		session.save(de5);
		session.save(text2);
		session.save(de6);

		// CSIRO Data Element
		ArrayList<CsiroData> csiroDataList = new ArrayList<CsiroData>();
		csiroDataList.add((CsiroData)(session.get(CsiroData.class, 1)));
		csiroDataList.add((CsiroData)(session.get(CsiroData.class, 2)));
		csiroDataList.add((CsiroData)(session.get(CsiroData.class, 3)));
		csiroDataList.add((CsiroData)(session.get(CsiroData.class, 4)));
	    DataElementCsiro deCsiro = new DataElementCsiro(date, "CSIRO Data Element Test", true, 0, DisplayType.TABLE, user1wb, csiroDataList, true);
	    session.save(deCsiro);
	    
	    // CMAR Data Element
	    ArrayList<CmarData> cmarDataList = new ArrayList<CmarData>();
	    cmarDataList.add((CmarData)(session.get(CmarData.class, 1)));
	    DataElementCmar deCmar = new DataElementCmar(date, "CMAR Data Element Test", true, 0, DisplayType.TABLE, user1wb, cmarDataList, true);
	    session.save(deCmar);
	    
	    // Engineering Model Data Element
	    /*DataElementEngineeringModel deEngModel = new DataElementEngineeringModel(date, "Data Element for " + engVar, true, 0, user1wb, engineeringModelDataList);
	    session.save(deEngModel);*/
	    
	    // Vulnerability Data Element
	    WeatherEvent we = new WeatherEvent("Storm", 2006, true, "Impact of the event", "1;2;4;0;0;1;5;3;2;0;1;4", "Other consequences", false, "Changes implemented");
	    session.save(we);
	    session.save(new DataElementVulnerability(date, "Vulnerability Assessment", true, 0, DisplayType.GRAPH, user1wb, we));
	    
	    
		// Commit all the transaction
		session.getTransaction().commit();
	}
}
