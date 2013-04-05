package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import war.model.*;

@SuppressWarnings("deprecation")
public class AbsDataLoader {
		
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	
		
		LoadAbsData(session);
		
		session.getTransaction().commit();
	}
	
	public static void LoadAbsData(Session session)
	{
		AbsVariable popChange = new AbsVariable("Population", "Pop.", "Population in urban centre", "people", "%");
		session.save(popChange);

		Seaport seaport = (Seaport)(session.get(Seaport.class, "AUNTL"));
		session.save(new AbsData(seaport, popChange, "2001,381248;2002,384826;2003,388460;2004,391638;2005,395806;2006,399712;2007,402318;2008,406202;2009,408966;2010,410825;2011,413962"));

		seaport = (Seaport)(session.get(Seaport.class, "AUSYD"));
		session.save(new AbsData(seaport, popChange, "2001,3784614;2002,3815623;2003,3841038;2004,3863306;2005,3892711;2006,3927676;2007,3983300;2008,4048560;2009,4120338;2010,4179466;2011,4231954"));
		
		seaport = (Seaport)(session.get(Seaport.class, "AUPKL"));
		session.save(new AbsData(seaport, popChange, "2001,262785;2002,265247;2003,266898;2004,267829;2005,269255;2006,270997;2007,272556;2008,275602;2009,277778;2010,279642;2011,280705"));

		seaport = (Seaport)(session.get(Seaport.class, "AUBSJ"));
		session.save(new AbsData(seaport, popChange, "2001,12266;2002,12307;2003,12270;2004,12500;2005,12666;2006,12925;2007,13078;2008,13194;2009,13827;2010,13407;2011,13479"));
		
		seaport = (Seaport)(session.get(Seaport.class, "AUALH"));
		session.save(new AbsData(seaport, popChange, "2001,28442;2002,28755;2003,29107;2004,29451;2005,30019;2006,30492;2007,30749;2008,30965;2009,31237;2010,31479;2011,31538"));

		seaport = (Seaport)(session.get(Seaport.class, "AUGET"));
		session.save(new AbsData(seaport, popChange, "2001,31994;2002,32260;2003,32427;2004,32506;2005,33338;2006,34175;2007,34766;2008,35554;2009,36289;2010,36670;2011,37114"));

		seaport = (Seaport)(session.get(Seaport.class, "AUBUY"));
		session.save(new AbsData(seaport, popChange, "2001,50260;2002,51040;2003,51646;2004,53531;2005,55919;2006,58694;2007,60371;2008,62457;2009,64503;2010,66053;2011,67421"));
	}
}
