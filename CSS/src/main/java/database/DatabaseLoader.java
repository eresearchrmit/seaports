package database;

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
		
		session.getTransaction().commit();
	}
}
