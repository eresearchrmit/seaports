package war.model;

import war.model.*;




import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


@SuppressWarnings("deprecation")
public class TestDatabase {


	public static void main(String[] args) {

		AnnotationConfiguration config = new AnnotationConfiguration() ;
		config.addAnnotatedClass(Person.class) ;
		config.addAnnotatedClass(WorkBoard.class) ;
		config.configure("hibernate.cfg.xml") ;
		
	    new SchemaExport(config).create(true,true) ;

		SessionFactory factory = config.buildSessionFactory() ;
		Session session = factory.getCurrentSession() ;	
		session.beginTransaction() ;	
		
		Person p1 = new Person() ;
		p1.setFirstName("Venki") ;
		p1.setLastName("Bala") ;
		p1.setPassWord("nara");
		
		WorkBoard w1 = new WorkBoard() ;
		w1.setWorkBoardName("My First WorkBoard") ;
		w1.setPerson(p1) ;
		
/*		Person p2 = new Person() ;
		p2.setFirstName("Jane") ;
		p2.setLastName("Mullett") ;
		p2.setPassWord("jane123");
		
		Person p3 = new Person() ;
		p3.setFirstName("Ravi") ;
		p3.setLastName("Srini") ;
		p3.setPassWord("ravi123");*/
		
		session.save(w1) ;
		session.save(p1) ;
/*		session.save(p2) ;
		session.save(p3) ;*/
		session.getTransaction().commit() ;
	}

}
