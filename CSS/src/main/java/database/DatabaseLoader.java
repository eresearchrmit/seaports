package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import security.UserLoginService;

import war.model.*;

/**
 * Class used to load all the initial data of the Climate Smart Seaports application in the database.
 * @author Guillaume Prevost
 */
@SuppressWarnings("deprecation")
public class DatabaseLoader {

	/**
	 * This password correspond to the SHA-256 hash of 'password'
	 */
	private static final String DEFAULT_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
	
	/**
	 * Main method loading all the initial data in the database using Hibernate
	 * Running this is equivalent to importing the 'seaports_dump.sql' SQL script 
	 * in an empty 'seaports' database, minus the engineering model examples.
	 * @param args: no parameters
	 */
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	

		User guillaume = new User("gprevost", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_ADMINISTRATOR,"guillaume.prevost@rmit.edu.au", "Guillaume", "Prevost");
		User jane = new User("jmullett", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "jane.mullett@rmit.edu.au", "Jane", "Mullett");
		User alexei = new User("atrundle", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "alexei.trundle@rmit.edu.au", "Alexei", "Trundle");
		User helen = new User("hscott", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "helen.scott@rmit.edu.au", "Helen", "Scott");
		User darryn = new User("dmcevoy", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "darryn.mcevoy@rmit.edu.au", "Darryn", "McEvoy");
		User ravi = new User("rsrini", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "ravi.sreenivasamurthy@rmit.edu.au", "Ravi", "Srini");
		User user = new User("user", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_USER, "email@company.com", "User", "User");
		User admin = new User("admin", DEFAULT_PASSWORD, true, true, UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "Admin", "Admin");
		session.save(guillaume);
		session.save(jane);
		session.save(alexei);
		session.save(helen);
		session.save(darryn);
		session.save(ravi);
		session.save(user);
		session.save(admin);

		// Regions & Ports
		Region r1 = new Region("East Coast South", "149.0547914184899,-28.24548513942176 148.9609907444339,-35.25426489702431 155.2660617233398,-35.19627668034501 154.8867619045885,-27.90208550082615 149.0547914184899,-28.24548513942176");
		Region r2 = new Region("Southern Slopes Vic East", "145.3189141977523,-33.8991314327851 145.0790050622273,-39.34717405549549 153.0808752795217,-39.39167277749677 152.564341927145,-34.0115196362042 145.3189141977523,-33.8991314327851");
		Region r3 = new Region("Southern and Southwestern Flatlands West", "112.7632954156051,-26.52537830109324 112.2397686001995,-35.9640923319097 125.4390964302563,-36.01229549528745 124.759915381913,-26.54545205884348 112.7632954156051,-26.52537830109324");
		Region r4 = new Region("Monsoonal North", "");
		Region r5 = new Region("Wet Tropics", "");
		Region r6 = new Region("Rangelands", "");
		Region r7 = new Region("Central Slopes", "");
		Region r8 = new Region("Murray Basin", "");
		session.save(r1);
		session.save(r2);
		session.save(r3);
		session.save(r4);
		session.save(r5);
		session.save(r6);
		session.save(r7);
		session.save(r8);
		
		Seaport port1 = new Seaport("AUYBA", "Port of Yamba", r1);
		Seaport port2 = new Seaport("AUNTL", "Newcastle Port", r1, "Newcastle");
		Seaport port3 = new Seaport("AUSYD", "Sydney Harbour", r1, "Sydney");
		//Seaport port4 = new Seaport("AUBTB", "Port of Botany Bay", r1);
		//Seaport port5 = new Seaport("AUCFS", "Coffs Harbour", r1);
		
		Seaport port6 = new Seaport("AUBSJ", "Lakes Entrance", r2, "Bairnsdale");
		Seaport port7 = new Seaport("AUPKL", "Port Kembla", r2, "Wollongong");
		Seaport port8 = new Seaport("AUQDN", "Port of Eden", r2);
		Seaport port9 = new Seaport("AUXMC", "Port of Mallacoota", r2);
		Seaport port10 = new Seaport("AUWHL", "Port Welshpool", r2);
		
		Seaport port11 = new Seaport("AUEPR", "Esperance Port", r3);
		Seaport port12 = new Seaport("AUALH", "Albany Port", r3, "Albany");
		Seaport port13 = new Seaport("AUBUY", "Port of Bunbury", r3, "Bunbury");
		Seaport port14 = new Seaport("AUGET", "Port of Geraldton", r3, "Geraldton");
		Seaport port15 = new Seaport("AUFRE", "Fremantle", r3, "Perth");
		
		session.save(port1);
		session.save(port2);
		session.save(port3);
		//session.save(port4);
		//session.save(port5);
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
		
		// Loads the various datasets
		CsiroDataLoader.LoadCsiroData(session);
		EngineeringModelDataLoader.LoadEngineeringModelData(session);
		BomDataLoader.LoadBomData(session);
		AcornSatDataLoader.LoadAcornSatData(session);
		AbsDataLoader.LoadAbsData(session);
		BitreDataLoader.LoadBitreData(session);
		
		session.getTransaction().commit();
		
	}
}
