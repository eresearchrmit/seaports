package database;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import security.UserLoginService;

import war.model.*;

@SuppressWarnings("deprecation")
public class DatabaseLoader {

	// The password correspond to the SHA-256 hash of 'password'
	private static final String DEFAULT_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
	
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	

		
		User p1 = new User("gprevost", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_ADMINISTRATOR,"guillaume.prevost@rmit.edu.au", "Guillaume", "Prevost");
		User p2 = new User("jmullett", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "jane.mullett@rmit.edu.au", "Jane", "Mullett");
		User p3 = new User("atrundle", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "alexei.trundle@rmit.edu.au", "Alexei", "Trundle");
		User p4 = new User("rsrini", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email", "Ravi", "Srini");
		User p5 = new User("user", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email", "User", "User");
		User p6 = new User("admin", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_ADMINISTRATOR, "email", "Admin", "Admin");
		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);
		session.save(p6);
		
		// Add Workboards & User Stories
		/*UserStory wb = new UserStory();
		wb.setName("My Workboard");
		wb.setMode("active");
		wb.setAccess("private");
		wb.setOwner(p1);*/
		
		UserStory us = new UserStory();
		us.setName("My User Story");
		//us.setRegion(new Region("East Coast South"));
		us.setMode("passive");
		us.setAccess("private");
		us.setOwner(p1);
		
		//session.save(wb);
		session.save(us);
		
		// Add Data Elements
	    Date date = new Date();
	    String content = "This is a test for Data Element";
	    DataElementFile de1 = new DataElementFile(date, "Test 1", true, 1, us, "csv", content.getBytes());
	   
	    content = "This is the second test";
	    DataElementFile de2 = new DataElementFile(date, "Test 2", true, 2, us, "xml", content.getBytes());
	    de2.setIncluded(false);
	    
	    DataElementText text1 = new DataElementText(date, "Comment text 1", true, 2, us, "This is a text comment");
	    text1.setIncluded(false);
	    
	    content = "This is the third test";
	    DataElementFile de3 = new DataElementFile(date, "Test 3", true, 3, us, "txt", content.getBytes());
	    
	    DataElementText text2 = new DataElementText(date, "Comment text 1", true, 4, us, "This is a text comment");

	    session.save(de1);
		session.save(de2);
		session.save(text1);
		session.save(de3);
		session.save(text2);
	
		CsiroDataLoader.LoadCsiroData(session);
		EngineeringModelDataLoader.LoadEngineeringModelData(session);
		
		session.getTransaction().commit();
	}
}
