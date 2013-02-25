package database;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


import war.model.*;

@SuppressWarnings("deprecation")
public class DatabaseLoader {

	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure("database/hibernate.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	

		User p1 = new User("gprevost", "password", "Guillaume", "Prevost", User.Privilege.ADMIN);
		User p2 = new User("jmullet", "jane123", "Jane", "Mullett", User.Privilege.USER);
		User p3 = new User("rsrini", "ravi123", "Ravi", "Srini", User.Privilege.USER);
		User p4 = new User("user", "password", "user", "user", User.Privilege.USER);
		User p5 = new User("vbala", "nara", "Venki", "Bala", User.Privilege.ADMIN);
		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);
		
		// Add Workboards & User Stories
		/*UserStory wb = new UserStory();
		wb.setName("My Workboard");
		wb.setMode("active");
		wb.setAccess("private");
		wb.setOwner(p1);*/
		
		UserStory us = new UserStory();
		us.setName("My User Story");
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
