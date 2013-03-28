package database;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;

import war.model.*;

@SuppressWarnings("deprecation")
public class EngineeringModelDataLoader {
	
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate.cfg.xml");
		//new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		
		LoadEngineeringModelData(session);
		
		session.getTransaction().commit();
	}
	
	public static void LoadEngineeringModelData(Session session)
	{
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
		
		for (EngineeringModelVariable engVar : engVarsList) {
			session.save(engVar);
		}
		
		/*LoadExampleData(session, regionDao.find(REGION1), engVarsList);
		LoadExampleData(session, regionDao.find(REGION2), engVarsList);
		LoadExampleData(session, regionDao.find(REGION3), engVarsList);*/
	}
}
