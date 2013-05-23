package database;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;

import war.model.*;

/**
 * Class used to load the engineering model variables in the database
 * @author Guillaume Prevost
 */
@SuppressWarnings("deprecation")
public class EngineeringModelDataLoader {
	
	/**
	 * Main method used to load engineering model variables only.
	 * On an existing database, this may duplicate data.
	 * @param args: no parameters
	 */
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
	
	/**
	 * Loads the engineering model variable in the database
	 * @param session: the Hibernate Session object which takes care of persisting objects in the database
	 */
	public static void LoadEngineeringModelData(Session session)
	{
		// Engineering variables
		ArrayList<EngineeringModelVariable> engVarsList = new ArrayList<EngineeringModelVariable>();
		// Chloride
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation of reinforcing bar probability", "Pint", "Corrosion initiation of reinforcing bar probability", "Chloride", "")); // CY
		engVarsList.add(new EngineeringModelVariable("Change in chloride concentration at concrete cover depth", "C(h,t)", "Change in chloride concentration at concrete cover depth", "Chloride", "kg/m³")); // CW
		engVarsList.add(new EngineeringModelVariable("Change in corrosion rate of reinforcing bar", "icorr (t)", "Change in corrosion rate of reinforcing bar", "Chloride", "µA/cm²")); // DB
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation time", "ti", "Corrosion initiation time", "Chloride", "yr")); // EN
		engVarsList.add(new EngineeringModelVariable("Crack propagation time", "tp", "Crack propagation time", "Chloride", "yr")); // EO
		engVarsList.add(new EngineeringModelVariable("Time to severe cracking (1mm crack width)", "tcr", "Time to severe cracking (1mm crack width)", "Chloride", "yr")); // EP
		engVarsList.add(new EngineeringModelVariable("Chloride induced corrosion probability to severe cracking", "Pinit x Pcrack", "Chloride induced corrosion probability to severe cracking", "Chloride", "")); // ER
		engVarsList.add(new EngineeringModelVariable("Reduction or loss in reinforcing bar", "Rebar loss", "Reduction or loss in reinforcing bar", "Chloride", "mm")); // ES
		// Carbonation
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation of reinforcing bar probability", "Pint", "Corrosion initiation of reinforcing bar probability ", "Carbonation", "")); // BS
		engVarsList.add(new EngineeringModelVariable("Change in carbonation depth", "Delta xc(t')", "Change in carbonation depth", "Carbonation", "mm")); // BQ
		engVarsList.add(new EngineeringModelVariable("Change in corrosion rate of reinforcing bar", "icorr(t')", "Change in corrosion rate of reinforcing bar", "Carbonation", "µA/cm²")); // BY
		engVarsList.add(new EngineeringModelVariable("Corrosion initiation time", "ti", "Corrosion initiation time", "Carbonation", "yr")); // EH
		engVarsList.add(new EngineeringModelVariable("Crack propagation time", "tp", "Crack propagation time", "Carbonation", "yr")); // EI
		engVarsList.add(new EngineeringModelVariable("Time to severe cracking (1mm crack width)", "tcr", "Time to severe cracking (1mm crack width)", "Carbonation", "yr")); // EJ
		engVarsList.add(new EngineeringModelVariable("Carbonation induced corrosion probability to severe cracking", "Pinit x Pcrack", "Carbonation induced corrosion probability to severe cracking", "Carbonation", "")); // EL
		engVarsList.add(new EngineeringModelVariable("Reduction or loss in reinforcing bar", "Rebar loss", "Reduction or loss in reinforcing bar", "Carbonation", "mm")); // EM
		
		for (EngineeringModelVariable engVar : engVarsList) {
			session.save(engVar);
		}
		/*
	    EngineeringModelAsset asset1 = new EngineeringModelAsset("042D", "Pile No 2 @ Berth 09", 1902, "tidal", 0.4, "C1", "CB2", "CL3", 70.0, 1400.0, 80.0, 0.33, 290.0, 14.0);
	    session.save(asset1);
	    EngineeringModelAsset asset2 = new EngineeringModelAsset("054F", "Pile No 78 @ Berth 42", 2000, "atmospheric", 2.0, "B2", "CB4", "CL2", 60.0, 1600.0, 60.0, 0.6, 270.0, 16.0);
	    session.save(asset2);

	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 19)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.58034945;2032,0.5635755;2064,0.57995474;2033,0.56426835;2067,0.5811257;2034,0.56494176;2066,0.58073974;2035,0.56559706;2069,0.5818852;2036,0.56623554;2068,0.5815075;2037,0.5668583;2038,0.5674663;2070,0.582259;2039,0.56806046;2040,0.5686416;2041,0.56921047;2042,0.5697678;2043,0.570314;2044,0.5708498;2045,0.57137567;2046,0.5718921;2047,0.5723995;2048,0.5728983;2017,0.5486281;2049,0.5733889;2016,0.54690844;2050,0.5738716;2019,0.5515309;2051,0.5743468;2018,0.55015224;2052,0.57481474;2021,0.55396986;2053,0.5752757;2020,0.5527961;2054,0.57573;2023,0.5561021;2055,0.57617795;2022,0.55506796;2056,0.5766196;2025,0.5580131;2057,0.5770554;2024,0.55708146;2058,0.5774853;2027,0.5597552;2059,0.57790977;2026,0.55890274;2060,0.57832885;2029,0.5613632;2061,0.5787427;2028,0.56057423;2062,0.5791515;2031,0.5628616;2063,0.5795555;2030,0.56212485;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.5424517;2015,0.5449071;2012,0.0;2013,0.5390782"));

	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 4)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.5943125;2032,0.56808805;2064,0.59355104;2033,0.568988;2067,0.59583616;2034,0.5698735;2066,0.5950742;2035,0.57074565;2069,0.5973611;2036,0.5716055;2068,0.59659845;2037,0.572454;2038,0.57329184;2070,0.5981241;2039,0.5741199;2040,0.57493883;2041,0.5757518;2042,0.5765591;2043,0.5773612;2044,0.57815844;2045,0.578951;2046,0.5797393;2047,0.5805235;2048,0.5813039;2017,0.5503608;2049,0.5820807;2016,0.54844564;2050,0.5828541;2019,0.5536318;2051,0.58362573;2018,0.5520718;2052,0.5843956;2021,0.55642664;2053,0.58516383;2020,0.5550747;2054,0.58593065;2023,0.55891776;2055,0.58669597;2022,0.5577036;2056,0.5874602;2025,0.5611924;2057,0.5882234;2024,0.56007826;2058,0.5889856;2027,0.5633039;2059,0.5897469;2026,0.56226605;2060,0.5905074;2029,0.56528753;2061,0.59126806;2028,0.56430995;2062,0.59202886;2031,0.56717235;2063,0.5927898;2030,0.5662394;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.54354423;2015,0.5462347;2012,0.0;2013,0.53987825"));
	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 2)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.59442705;2032,0.5680889;2064,0.5936593;2033,0.56898963;2067,0.5959641;2034,0.5698761;2066,0.59519535;2035,0.57074946;2069,0.5975032;2036,0.57161075;2068,0.59673333;2037,0.5724608;2038,0.5733005;2070,0.5982735;2039,0.5741305;2040,0.5749515;2041,0.57576674;2042,0.5765765;2043,0.57738113;2044,0.578181;2045,0.5789765;2046,0.57976776;2047,0.5805551;2048,0.5813387;2017,0.5503608;2049,0.58211887;2016,0.54844564;2050,0.5828958;2019,0.5536318;2051,0.583671;2018,0.5520718;2052,0.58444464;2021,0.55642664;2053,0.58521676;2020,0.5550747;2054,0.5859875;2023,0.55891776;2055,0.58675694;2022,0.5577036;2056,0.58752555;2025,0.5611924;2057,0.58829325;2024,0.56007826;2058,0.5890603;2027,0.5633039;2059,0.58982664;2026,0.56226605;2060,0.5905924;2029,0.56528753;2061,0.59135854;2028,0.56430995;2062,0.59212506;2031,0.56717265;2063,0.59289193;2030,0.5662394;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.54354423;2015,0.5462347;2012,0.0;2013,0.53987825"));
	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 6)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.5929444;2032,0.56767565;2064,0.5922224;2033,0.5685557;2067,0.59438694;2034,0.56942064;2066,0.5936659;2035,0.57027173;2069,0.59582776;2036,0.5711099;2068,0.59510756;2037,0.5719361;2038,0.57275116;2070,0.59654754;2039,0.57355595;2040,0.574351;2041,0.5751395;2042,0.5759219;2043,0.57669854;2044,0.57746977;2045,0.5782358;2046,0.578997;2047,0.5797537;2048,0.580506;2017,0.5502092;2049,0.5812543;2016,0.54831195;2050,0.58199865;2019,0.5534459;2051,0.58274066;2018,0.5519029;2052,0.5834805;2021,0.5562072;2053,0.58421814;2020,0.55487204;2054,0.5849538;2023,0.55866474;2055,0.5856875;2022,0.55746746;2056,0.58641934;2025,0.56090546;2057,0.58714926;2024,0.5598084;2058,0.58787745;2027,0.5629824;2059,0.588604;2026,0.5619619;2060,0.5893289;2029,0.5649308;2061,0.59005314;2028,0.5639709;2062,0.5907768;2031,0.5667792;2063,0.59149987;2030,0.5658648;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.5434504;2015,0.54612005;2012,0.0;2013,0.53981"));
	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 3)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.59232193;2032,0.5681306;2064,0.5916734;2033,0.56901443;2067,0.59361;2034,0.569881;2066,0.59296745;2035,0.5707317;2069,0.59488666;2036,0.5715676;2068,0.5942497;2037,0.5723899;2038,0.57319945;2070,0.595521;2039,0.57399714;2040,0.57478374;2041,0.5755606;2042,0.5763284;2043,0.5770875;2044,0.5778386;2045,0.578582;2046,0.5793182;2047,0.5800476;2048,0.58077055;2017,0.5504285;2049,0.58148736;2016,0.5485064;2050,0.5821983;2019,0.5537122;2051,0.58290386;2018,0.552146;2052,0.5836042;2021,0.55651665;2053,0.5842996;2020,0.55516106;2054,0.5849903;2023,0.55900997;2055,0.58567655;2022,0.5577955;2056,0.5863584;2025,0.56128156;2057,0.587036;2024,0.5601695;2058,0.5877096;2027,0.5633859;2059,0.5883794;2026,0.56235206;2060,0.58904546;2029,0.5653592;2061,0.58970773;2028,0.56438714;2062,0.59036636;2031,0.56722796;2063,0.59102154;2030,0.5663048;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.54358846;2015,0.54628783;2012,0.0;2013,0.53991103"));
	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 1)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.59253734;2032,0.5682333;2064,0.59188366;2033,0.5691212;2067,0.59383625;2034,0.5699916;2066,0.59318817;2035,0.570846;2069,0.59512454;2036,0.5716856;2068,0.59448165;2037,0.5725114;2038,0.5733244;2070,0.59576494;2039,0.57412547;2040,0.5749154;2041,0.5756955;2042,0.5764664;2043,0.57722867;2044,0.5779828;2045,0.57872915;2046,0.5794683;2047,0.5802006;2048,0.5809264;2017,0.55046666;2049,0.581646;2016,0.54854;2050,0.58235985;2019,0.553759;2051,0.58306813;2018,0.5521885;2052,0.58377117;2021,0.5565719;2053,0.5844693;2020,0.5552121;2054,0.5851627;2023,0.5590737;2055,0.58585155;2022,0.557855;2056,0.5865363;2025,0.5613538;2057,0.5872171;2024,0.56023747;2058,0.58789414;2027,0.56346685;2059,0.5885676;2026,0.5624286;2060,0.58923763;2029,0.56544894;2061,0.58990407;2028,0.5644725;2062,0.5905672;2031,0.56732655;2063,0.59122694;2030,0.5663991;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.543612;2015,0.5463167;2012,0.0;2013,0.5399282"));
	    session.save(new EngineeringModelData(asset1, 
	    		(ClimateParams)(session.get(ClimateParams.class, 5)), 
	    		(EngineeringModelVariable)(session.get(EngineeringModelVariable.class, 1)),
	    		"2065,0.5912121;2032,0.5677194;2064,0.5905883;2033,0.56858504;2067,0.5924499;2034,0.5694333;2066,0.59183264;2035,0.5702655;2069,0.5936753;2036,0.5710828;2068,0.5930641;2037,0.57188624;2038,0.57267684;2070,0.59428364;2039,0.5734554;2040,0.57422274;2041,0.57498014;2042,0.5757282;2043,0.5764675;2044,0.5771985;2045,0.5779217;2046,0.5786375;2047,0.57934624;2048,0.5800484;2017,0.55027646;2049,0.58074415;2016,0.54837227;2050,0.5814339;2019,0.5535257;2051,0.58211803;2018,0.55197656;2052,0.5827968;2021,0.5562965;2053,0.5834704;2020,0.5549577;2054,0.5841391;2023,0.55875623;2055,0.5848031;2022,0.5575586;2056,0.58546245;2025,0.5609938;2057,0.5861173;2024,0.5598988;2058,0.5867679;2027,0.5630637;2059,0.5874144;2026,0.5620471;2060,0.588057;2029,0.5650018;2061,0.58869547;2028,0.5640474;2062,0.5893301;2031,0.5668348;2063,0.589961;2030,0.5659296;2002,0.0;2003,0.0;2000,0.0;2001,0.0;2006,0.0;2007,0.0;2004,0.0;2005,0.0;2010,0.0;2011,0.0;2008,0.0;2009,0.0;2014,0.54349434;2015,0.54617274;2012,0.0;2013,0.5398426"));
	    */
	}
}
