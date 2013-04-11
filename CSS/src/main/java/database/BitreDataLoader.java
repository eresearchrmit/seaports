package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import war.model.BitreData;
import war.model.BitreVariableCategory;
import war.model.BitreVariable;
import war.model.Seaport;

@SuppressWarnings("deprecation")
public class BitreDataLoader {
		
	public static void main(String[] args)
	{
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		config.configure("database/hibernate.cfg.xml");
		new SchemaExport(config).create(true,true);

		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();	
		
		LoadBitreData(session);
		
		session.getTransaction().commit();
	}
	
	public static void LoadBitreData(Session session)
	{
		// BITRE Variables Categories
		BitreVariableCategory cargoType = new BitreVariableCategory("Freight throuput by cargo type");
		BitreVariableCategory vesselcallsType = new BitreVariableCategory("Commercial vessel calls by type");
		BitreVariableCategory commoditiesType = new BitreVariableCategory("Export Commodities by type");
		session.save(cargoType);
		session.save(vesselcallsType);
		session.save(commoditiesType);
		
		// BITRE Variables
		BitreVariable bulkExport = new BitreVariable("Bulk - Export", "Bulk exp.", "Bulk export", cargoType, "Mass tonnes");
		BitreVariable bulkImport = new BitreVariable("Bulk - Import", "Bulk imp.", "Bulk import", cargoType, "Mass tonnes");
		BitreVariable containerizedExport = new BitreVariable("Containerized - Export", "Cont. exp.", "Containerized export", cargoType, "Mass tonnes");
		BitreVariable containerizedImport = new BitreVariable("Containerized - Import", "Cont. imp.", "Containerized import", cargoType, "Mass tonnes");
		BitreVariable generalExport = new BitreVariable("General - Export", "Gen. exp.", "General export", cargoType, "Mass tonnes");
		BitreVariable generalImport = new BitreVariable("General - Import", "Gen. imp.", "General import", cargoType, "Mass tonnes");
		session.save(bulkExport);
		session.save(bulkImport);
		session.save(containerizedExport);
		session.save(containerizedImport);
		session.save(generalExport);
		session.save(generalImport);
		
		BitreVariable bulkLiquids = new BitreVariable("Bulk Liquids", "Bulk liq.", "Bulk liquids inc. LNG, Chemicals, etc.", vesselcallsType, "Number of calls");
		BitreVariable carCarriers = new BitreVariable("Car Carriers", "Car carriers", "Car carriers", vesselcallsType, "Number of calls");
		BitreVariable containers = new BitreVariable("Containers", "Cont.", "Containers", vesselcallsType, "Number of calls");
		BitreVariable dryBulk = new BitreVariable("Dry Bulk", "Dry Bulk", "Dry bulk", vesselcallsType, "Number of calls");
		BitreVariable gasCalls = new BitreVariable("Gas", "Gas", "Gas", vesselcallsType, "Number of calls");
		BitreVariable generalCargo = new BitreVariable("General cargo", "Gen. cargo", "General cargo + others", vesselcallsType, "Number of calls");
		BitreVariable livestockCarriers = new BitreVariable("Livestock Carriers", "Liv. carriers", "Livestock carriers", vesselcallsType, "Number of calls");
		session.save(bulkLiquids);
		session.save(carCarriers);
		session.save(containers);
		session.save(dryBulk);
		session.save(gasCalls);
		session.save(generalCargo);
		session.save(livestockCarriers);
		
		BitreVariable alumina = new BitreVariable("Alumina", "Al.", "Alumina commodities", commoditiesType, "Mass tonnes");
		BitreVariable aluminium = new BitreVariable("Aluminium", "Al.", "Aluminium commodities", commoditiesType, "Mass tonnes");
		BitreVariable bauxite = new BitreVariable("Bauxite", "Baux.", "Bauxite commodities", commoditiesType, "Mass tonnes");
		BitreVariable coal = new BitreVariable("Coal", "Coal", "Coal commodities", commoditiesType, "Mass tonnes");
		BitreVariable copperConcentrate = new BitreVariable("Copper Concentrate", "Cop. conc.", "Copper concentrate commodities", commoditiesType, "Mass tonnes");
		BitreVariable copperOre = new BitreVariable("Copper Ore", "Cop. ore", "Copper ore commodities", commoditiesType, "Mass tonnes");
		BitreVariable copperRefined = new BitreVariable("Refined Copper", "Ref. cop.", "Refined copper commodities", commoditiesType, "Mass tonnes");
		BitreVariable cotton = new BitreVariable("Cotton", "Cotton", "Cotton", commoditiesType, "Mass tonnes");
		BitreVariable gas = new BitreVariable("Gas", "Gas", "Gas commodities", commoditiesType, "Mass tonnes");
		BitreVariable grain = new BitreVariable("Grain", "Grain", "Grain commodities", commoditiesType, "Mass tonnes");
		BitreVariable ironOre = new BitreVariable("Iron Ore", "Iron ore", "Iron ore commodities", commoditiesType, "Mass tonnes");
		BitreVariable leadConcentrate = new BitreVariable("Lead Concentrate", "Lead conc.", "Lead concentrate commodities", commoditiesType, "Mass tonnes");
		BitreVariable leadOre = new BitreVariable("Lead Ore", "Lead ore", "Lead ore commodities", commoditiesType, "Mass tonnes");
		BitreVariable leadRefined = new BitreVariable("Refined Lead", "Ref. lead", "Refined lead commodities", commoditiesType, "Mass tonnes");
		BitreVariable liveStock = new BitreVariable("Livestock", "Livestock", "Livestock commodities", commoditiesType, "Mass tonnes");
		BitreVariable manganese = new BitreVariable("Manganese", "Manganese", "Manganese commodities", commoditiesType, "Mass tonnes");
		BitreVariable mineralSand = new BitreVariable("Mineral Sand", "Min. sand", "Mineral sand commodities", commoditiesType, "Mass tonnes");
		BitreVariable motorVehicle = new BitreVariable("Motor Vehicle", "Mot. Veh.", "Motor vehicle commodities", commoditiesType, "Mass tonnes");
		BitreVariable nickel = new BitreVariable("Nickel", "Nickel", "Nickel commodities", commoditiesType, "Mass tonnes");
		BitreVariable oilPetroleum = new BitreVariable("Oil and Petroleum", "Oil & Petrol.", "Oil and petroleum commodities", commoditiesType, "Mass tonnes");
		BitreVariable silicaSand = new BitreVariable("Silica sand", "Silica sand", "Silica sand commodities", commoditiesType, "Mass tonnes");
		BitreVariable steel = new BitreVariable("Steel", "Steel", "Steel  commodities", commoditiesType, "Mass tonnes");
		BitreVariable sugar = new BitreVariable("Sugar", "Sugar", "Sugar commodities", commoditiesType, "Mass tonnes");
		BitreVariable timberLogs = new BitreVariable("Timber - Logs", "Timber logs", "Timber logs commodities", commoditiesType, "Mass tonnes");
		BitreVariable timberProducts = new BitreVariable("Timber - Products", "Timber prod.", "Timber products commodities", commoditiesType, "Mass tonnes");
		BitreVariable timberWoodchips = new BitreVariable("Timber - Wood Chips", "Timber wood chips", "Timber wood chips commodities", commoditiesType, "Mass tonnes");
		BitreVariable wool = new BitreVariable("Wool", "Wool", "Wool commodities", commoditiesType, "Mass tonnes");
		BitreVariable zincConcentrate = new BitreVariable("Zinc Concentrate", "Zc conc.", "Zinc concentrate commodities", commoditiesType, "Mass tonnes");
		BitreVariable zincOre = new BitreVariable("Zinc Ore", "Zc ore", "Zinc ore commodities", commoditiesType, "Mass tonnes");
		BitreVariable zincRefined = new BitreVariable("Refined Zinc", "Ref. Zc", "Refined Zinc commodities", commoditiesType, "Mass tonnes");
		session.save(alumina);
		session.save(aluminium);
		session.save(bauxite);
		session.save(coal);
		session.save(copperConcentrate);
		session.save(copperOre);
		session.save(copperRefined);
		session.save(cotton);
		session.save(coal);
		session.save(grain);
		session.save(ironOre);
		session.save(leadConcentrate);
		session.save(leadOre);
		session.save(leadRefined);
		session.save(liveStock);
		session.save(manganese);
		session.save(mineralSand);
		session.save(motorVehicle);
		session.save(nickel);
		session.save(oilPetroleum);
		session.save(silicaSand);
		session.save(steel);
		session.save(sugar);
		session.save(timberLogs);
		session.save(timberProducts);
		session.save(timberWoodchips);
		session.save(wool);
		session.save(zincConcentrate);
		session.save(zincOre);
		session.save(zincRefined);
		
		// Port of Eden
		Seaport eden = (Seaport)(session.get(Seaport.class, "AUQDN"));
		session.save(new BitreData(eden, bulkImport, "2000,56409;2001,49127;2002,18183;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(eden, bulkExport, "2000,735248;2001,757091;2002,736106;2003,753469;2004,887724;2005,939759;2006,1329001;2007,1272094;2008,1197917;2009,1127529;2010,1227599;2011,590525"));
		session.save(new BitreData(eden, generalImport, "2000,209;2001,0;2002,0;2003,0;2004,192;2005,870;2006,492;2007,585;2008,447;2009,119;2010,386;2011,24"));
		session.save(new BitreData(eden, generalExport, "2000,254;2001,0;2002,0;2003,16239;2004,65069;2005,106408;2006,204;2007,434;2008,808;2009,458;2010,61;2011,54"));
		session.save(new BitreData(eden, containerizedImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,20;2010,266;2011,24"));
		session.save(new BitreData(eden, containerizedExport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,348;2010,325;2011,54"));

		session.save(new BitreData(eden, bulkLiquids, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,1;2011,0"));
		session.save(new BitreData(eden, carCarriers, "2000,7;2001,5;2002,2;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(eden, containers, "2000,1;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(eden, dryBulk, "2000,16;2001,18;2002,20;2003,20;2004,28;2005,31;2006,40;2007,40;2008,36;2009,27;2010,30;2011,18"));
		session.save(new BitreData(eden, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(eden, generalCargo, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(eden, livestockCarriers, "2000,3;2001,0;2002,0;2003,0;2004,11;2005,32;2006,26;2007,31;2008,33;2009,44;2010,28;2011,8"));
		
		session.save(new BitreData(eden, oilPetroleum, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,2;2009,0;2010,0;2011,0"));
		session.save(new BitreData(eden, timberLogs, "2000,0;2001,0;2002,0;2003,16239;2004,45638;2005,91965;2006,205853;2007,295162;2008,289533;2009,228365;2010,219631;2011,93407"));
		session.save(new BitreData(eden, timberWoodchips, "2000,735248;2001,757091;2002,736106;2003,753469;2004,887724;2005,954028;2006,1123148;2007,976932;2008,908382;2009,899164;2010,1007643;2011,497118"));
		
		// Newcastle
		Seaport newcastle = (Seaport)(session.get(Seaport.class, "AUNTL"));
		session.save(new BitreData(newcastle, bulkImport, "2000,3330237;2001,3187571;2002,2893384;2003,2536076;2004,2552111;2005,2371034;2006,2755974;2007,2759079;2008,2703158;2009,2945904;2010,2987553;2011,3243373"));
		session.save(new BitreData(newcastle, bulkExport, "2000,69949941;2001,71747360;2002,73437704;2003,79635751;2004,80347268;2005,82545203;2006,82270372;2007,89930112;2008,92498365;2009,99481907;2010,110793822;2011,125210296"));
		session.save(new BitreData(newcastle, generalImport, "2000,98510;2001,192971;2002,145391;2003,223037;2004,337962;2005,304365;2006,331768;2007,288851;2008,323980;2009,283432;2010,295100;2011,372990"));
		session.save(new BitreData(newcastle, generalExport, "2000,451215;2001,369651;2002,364142;2003,315493;2004,322852;2005,352285;2006,334338;2007,336869;2008,314354;2009,315329;2010,499269;2011,456584"));
		session.save(new BitreData(newcastle, containerizedImport, "2000,10964;2001,4185;2002,10048;2003,26379;2004,24616;2005,22893;2006,29924;2007,22804;2008,16595;2009,16725;2010,10613;2011,22496"));
		session.save(new BitreData(newcastle, containerizedExport, "2000,105036;2001,98140;2002,108289;2003,115210;2004,164984;2005,168889;2006,155708;2007,153804;2008,148301;2009,154496;2010,190738;2011,170468"));
		
		session.save(new BitreData(newcastle, bulkLiquids, "2000,9;2001,12;2002,9;2003,2;2004,3;2005,9;2006,2;2007,13;2008,16;2009,26;2010,34;2011,26"));
		session.save(new BitreData(newcastle, carCarriers, "2000,58;2001,60;2002,85;2003,87;2004,71;2005,67;2006,70;2007,79;2008,92;2009,96;2010,107;2011,117"));
		session.save(new BitreData(newcastle, containers, "2000,1;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(newcastle, dryBulk, "2000,1259;2001,1230;2002,1099;2003,1134;2004,1152;2005,1150;2006,1125;2007,1216;2008,1229;2009,1315;2010,1459;2011,1546"));
		session.save(new BitreData(newcastle, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,1;2010,1;2011,2"));
		session.save(new BitreData(newcastle, generalCargo, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,1;2006,1;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(newcastle, livestockCarriers, "2000,172;2001,164;2002,185;2003,198;2004,224;2005,226;2006,204;2007,229;2008,218;2009,205;2010,252;2011,267"));
		
		session.save(new BitreData(newcastle, aluminium, "2000,258943;2001,214398;2002,202863;2003,159804;2004,132759;2005,136691;2006,139312;2007,129044;2008,105101;2009,123650;2010,120755;2011,166181"));
		session.save(new BitreData(newcastle, coal, "2000,67218835;2001,69321381;2002,71412573;2003,77712033;2004,77724045;2005,80277740;2006,80773674;2007,88882187;2008,90677723;2009,97077637;2010,108405829;2011,121904634"));
		session.save(new BitreData(newcastle, copperConcentrate, "2000,315007;2001,285977;2002,406379;2003,313834;2004,171211;2005,285980;2006,200485;2007,290280;2008,310744;2009,334748;2010,376176;2011,305624"));
		session.save(new BitreData(newcastle, grain, "2000,1610452;2001,1483901;2002,868949;2003,783820;2004,1804407;2005,;2006,1364709;2007,564562;2008,882090;2009,1210295;2010,1329803;2011,1686730"));
		session.save(new BitreData(newcastle, leadConcentrate, "2000,56542;2001,0;2002,0;2003,5697;2004,4112;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(newcastle, leadRefined, "2000,18461;2001,19263;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(newcastle, manganese, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,263;2008,350;2009,4527;2010,0;2011,0"));
		session.save(new BitreData(newcastle, mineralSand, "2000,11320;2001,16543;2002,2715;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(newcastle, motorVehicle, "2000,51;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,374;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(newcastle, silicaSand, "2000,35534;2001,30591;2002,15929;2003,23354;2004,20903;2005,16184;2006,15696;2007,21581;2008,5501;2009,5500;2010,5380;2011,5497"));
		session.save(new BitreData(newcastle, steel, "2000,74529;2001,38547;2002,30198;2003,20066;2004,29838;2005,44562;2006,51252;2007,60194;2008,21033;2009,24383;2010,60427;2011,58531"));
		session.save(new BitreData(newcastle, timberLogs, "2000,2112;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,125231;2011,8782"));
		session.save(new BitreData(newcastle, timberProducts, "2000,2073;2001,0;2002,2516;2003,1139;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,433"));
		session.save(new BitreData(newcastle, timberWoodchips, "2000,255266;2001,170531;2002,245312;2003,246849;2004,218013;2005,249598;2006,242111;2007,255906;2008,264530;2009,330721;2010,349441;2011,257764"));
		session.save(new BitreData(newcastle, zincConcentrate, "2000,100083;2001,61718;2002,79381;2003,164786;2004,135663;2005,77349;2006,102667;2007,90880;2008,100842;2009,71688;2010,77143;2011,83759"));
		session.save(new BitreData(newcastle, zincRefined, "2000,43541;2001,45062;2002,30107;2003,4945;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		
		// Port Kembla
		Seaport kembla = (Seaport)(session.get(Seaport.class, "AUPKL"));
		session.save(new BitreData(kembla, bulkImport, "2000,8985314;2001,8765896;2002,9292500;2003,9308784;2004,9273930;2005,9232962;2006,9137586;2007,9583765;2008,6737328;2009,7984614;2010,8186946;2011,6068593"));
		session.save(new BitreData(kembla, bulkExport, "2000,12853239;2001,12051792;2002,10188944;2003,10438099;2004,12045676;2005,13203775;2006,13100871;2007,16684623;2008,14959831;2009,15107871;2010,14484573;2011,17915909"));
		session.save(new BitreData(kembla, generalImport, "2000,229084;2001,100694;2002,293020;2003,314860;2004,505888;2005,215579;2006,293687;2007,312393;2008,592616;2009,1151199;2010,2096321;2011,824858"));
		session.save(new BitreData(kembla, generalExport, "2000,2960356;2001,2620698;2002,2936526;2003,2062849;2004,2559313;2005,3256933;2006,2881253;2007,10614;2008,2103952;2009,2932044;2010,5210106;2011,3011318"));
		session.save(new BitreData(kembla, containerizedImport, "2000,4531;2001,3170;2002,3476;2003,595;2004,2316;2005,668;2006,312;2007,2625;2008,4469;2009,11732;2010,15603;2011,23042"));
		session.save(new BitreData(kembla, containerizedExport, "2000,8584;2001,17370;2002,17130;2003,21565;2004,22720;2005,10023;2006,10490;2007,818;2008,5760;2009,21353;2010,14167;2011,80342"));

		session.save(new BitreData(kembla, bulkLiquids, "2000,1;2001,2;2002,0;2003,2;2004,0;2005,0;2006,0;2007,0;2008,6;2009,13;2010,8;2011,26"));
		session.save(new BitreData(kembla, carCarriers, "2000,26;2001,30;2002,27;2003,22;2004,26;2005,29;2006,30;2007,27;2008,34;2009,2;2010,0;2011,4"));
		session.save(new BitreData(kembla, containers, "2000,2;2001,0;2002,0;2003,1;2004,0;2005,0;2006,0;2007,0;2008,0;2009,3;2010,3;2011,0"));
		session.save(new BitreData(kembla, dryBulk, "2000,479;2001,468;2002,418;2003,398;2004,456;2005,445;2006,435;2007,476;2008,433;2009,420;2010,438;2011,435"));
		session.save(new BitreData(kembla, gas, "2000,9;2001,0;2002,0;2003,0;2004,1;2005,0;2006,1;2007,60;2008,214;2009,323;2010,322;2011,369"));
		session.save(new BitreData(kembla, generalCargo, "2000,0;2001,0;2002,1;2003,0;2004,0;2005,0;2006,0;2007,2;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(kembla, livestockCarriers, "2000,142;2001,152;2002,152;2003,144;2004,143;2005,117;2006,149;2007,191;2008,175;2009,240;2010,243;2011,176"));
		
		session.save(new BitreData(kembla, aluminium, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,2;2006,0;2007,0;2008,0;2009,0;2010,0;2011,166"));
		session.save(new BitreData(kembla, coal, "2000,9589508;2001,9186000;2002,8966033;2003,8330892;2004,9997360;2005,9665376;2006,11679376;2007,12654047;2008,13232503;2009,13726530;2010,14049205;2011,14555425"));
		session.save(new BitreData(kembla, copperOre, "2000,31537;2001,55000;2002,4714;2003,266716;2004,395079;2005,455684;2006,449232;2007,387071;2008,373505;2009,364769;2010,357140;2011,423883"));
		session.save(new BitreData(kembla, copperRefined, "2000,0;2001,0;2002,256;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(kembla, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,21"));
		// Grain 2005 = n/d
		session.save(new BitreData(kembla, grain, "2000,2824972;2001,2344304;2002,838148;2003,809190;2004,1190156;2005,0;2006,1625092;2007,349629;2008,815391;2009,680472;2010,1628073;2011,2878405"));
		session.save(new BitreData(kembla, ironOre, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,15875;2008,0;2009,0;2010,0;2011,74346"));
		session.save(new BitreData(kembla, leadRefined, "2000,0;2001,0;2002,0;2003,0;2004,1114;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(kembla, liveStock, "2000,0;2001,0;2002,350;2003,0;2004,0;2005,0;2006,0;2007,74;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(kembla, mineralSand, "2000,11762;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,123129"));
		session.save(new BitreData(kembla, motorVehicle, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,3418;2008,1656;2009,4033;2010,7483;2011,10287"));
		session.save(new BitreData(kembla, oilPetroleum, "2000,16168;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,60043"));
		session.save(new BitreData(kembla, steel, "2000,2947862;2001,2567000;2002,2804645;2003,2678163;2004,2577300;2005,3244723;2006,2802000;2007,3164358;2008,2059893;2009,2747758;2010,3175214;2011,1816354"));
		session.save(new BitreData(kembla, timberLogs, "2000,128543;2001,13280;2002,0;2003,19778;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(kembla, timberProducts, "2000,61;2001,440;2002,0;2003,0;2004,0;2005,0;2006,282;2007,0;2008,0;2009,0;2010,0;2011,484"));
		session.save(new BitreData(kembla, zincOre, "2000,0;2001,0;2002,40;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(kembla, zincRefined, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,53;2008,0;2009,0;2010,0;2011,0"));
		
		// Sydney
		Seaport sydney = (Seaport)(session.get(Seaport.class, "AUSYD"));
		session.save(new BitreData(sydney, bulkImport, "2000,12880976;2001,12240137;2002,12879086;2003,12915960;2004,13086909;2005,13761682;2006,13522154;2007,13889007;2008,12572572;2009,12651637;2010,13058132;2011,13218418"));
		session.save(new BitreData(sydney, bulkExport, "2000,1052854;2001,820189;2002,533612;2003,701537;2004,772279;2005,887295;2006,857459;2007,1122445;2008,669728;2009,569577;2010,842675;2011,1020883"));
		session.save(new BitreData(sydney, generalImport, "2000,5984209;2001,6163853;2002,5553640;2003,6645712;2004,6739526;2005,6376572;2006,7224484;2007,7972870;2008,7731460;2009,8046922;2010,8567779;2011,8515714"));
		session.save(new BitreData(sydney, generalExport, "2000,4798929;2001,5056687;2002,4610488;2003,4806330;2004,5271457;2005,4879206;2006,6191665;2007,6192472;2008,6780262;2009,6892595;2010,7263832;2011,7314163"));
		// Same data as above
		session.save(new BitreData(sydney, containerizedImport, "2000,5984209;2001,6163853;2002,5553640;2003,6645712;2004,6739526;2005,6376572;2006,7224484;2007,7972870;2008,7731460;2009,8046922;2010,8567779;2011,8515714"));
		session.save(new BitreData(sydney, containerizedExport, "2000,4798929;2001,5056687;2002,4610488;2003,4806330;2004,5271457;2005,4879206;2006,6191665;2007,6192472;2008,6780262;2009,6892595;2010,7263832;2011,7314163"));
		
		session.save(new BitreData(sydney, bulkLiquids, "2000,1138;2001,1067;2002,1079;2003,1091;2004,1183;2005,1307;2006,1349;2007,1342;2008,1204;2009,1067;2010,1116;2011,1129"));
		session.save(new BitreData(sydney, carCarriers, "2000,395;2001,364;2002,372;2003,428;2004,415;2005,384;2006,371;2007,442;2008,415;2009,419;2010,526;2011,537"));
		session.save(new BitreData(sydney, containers, "2000,59;2001,54;2002,62;2003,80;2004,52;2005,60;2006,86;2007,103;2008,86;2009,63;2010,47;2011,39"));
		session.save(new BitreData(sydney, dryBulk, "2000,193;2001,201;2002,214;2003,218;2004,176;2005,155;2006,151;2007,162;2008,164;2009,139;2010,145;2011,92"));
		session.save(new BitreData(sydney, gas, "2000,228;2001,232;2002,247;2003,270;2004,275;2005,349;2006,349;2007,281;2008,95;2009,0;2010,0;2011,0"));
		session.save(new BitreData(sydney, generalCargo, "2000,0;2001,0;2002,1;2003,2;2004,3;2005,4;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(sydney, livestockCarriers, "2000,124;2001,140;2002,356;2003,319;2004,343;2005,84;2006,82;2007,129;2008,168;2009,149;2010,102;2011,145"));
		
		session.save(new BitreData(sydney, aluminium, "2000,0;2001,0;2002,0;2003,368929;2004,410407;2005,395039;2006,368548;2007,354484;2008,440342;2009,472809;2010,513752;2011,529296"));
		session.save(new BitreData(sydney, alumina, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,39395;2006,107424;2007,25492;2008,3410;2009,3410;2010,3937;2011,847"));
		session.save(new BitreData(sydney, bauxite, "2000,0;2001,0;2002,0;2003,0;2004,1043;2005,173;2006,0;2007,0;2008,154;2009,428;2010,863;2011,377"));
		session.save(new BitreData(sydney, coal, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,788;2006,1681;2007,3320;2008,4554;2009,3363;2010,3684;2011,3443"));
		session.save(new BitreData(sydney, copperOre, "2000,0;2001,0;2002,0;2003,0;2004,845;2005,265;2006,133;2007,293;2008,671;2009,47;2010,265;2011,13475"));
		session.save(new BitreData(sydney, copperRefined, "2000,0;2001,0;2002,0;2003,81105;2004,72525;2005,64203;2006,55735;2007,59917;2008,60032;2009,60214;2010,52313;2011,50445"));
		session.save(new BitreData(sydney, cotton, "2000,353226;2001,283196;2002,247727;2003,191957;2004,159005;2005,105158;2006,196946;2007,102754;2008,55729;2009,132446;2010,173987;2011,342778"));
		session.save(new BitreData(sydney, gas, "2000,43683;2001,71373;2002,76789;2003,113547;2004,69275;2005,88917;2006,136767;2007,168430;2008,102756;2009,35013;2010,20560;2011,12224"));
		session.save(new BitreData(sydney, grain, "2000,0;2001,0;2002,0;2003,318593;2004,450481;2005,575886;2006,487380;2007,479900;2008,766258;2009,904970;2010,967625;2011,1003218"));
		session.save(new BitreData(sydney, ironOre, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,476;2006,218;2007,893;2008,2530;2009,769;2010,0;2011,0"));
		session.save(new BitreData(sydney, leadOre, "2000,0;2001,0;2002,0;2003,0;2004,5542;2005,7841;2006,9718;2007,6917;2008,21228;2009,8675;2010,7286;2011,13419"));
		session.save(new BitreData(sydney, leadRefined, "2000,0;2001,0;2002,0;2003,9803;2004,5987;2005,5625;2006,5468;2007,7318;2008,15652;2009,15345;2010,10170;2011,9027"));
		session.save(new BitreData(sydney, liveStock, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,126;2008,32;2009,10;2010,0;2011,0"));
		// Manganese 2008 = ?
		session.save(new BitreData(sydney, manganese, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,1890;2006,2039;2007,792;2008,0;2009,608;2010,189;2011,0"));
		session.save(new BitreData(sydney, mineralSand, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,809;2007,3977;2008,2740;2009,1932;2010,1620;2011,0"));
		session.save(new BitreData(sydney, motorVehicle, "2000,1724;2001,2162;2002,4346;2003,5604;2004,7000;2005,8968;2006,22476;2007,10445;2008,1089;2009,0;2010,0;2011,0"));
		session.save(new BitreData(sydney, nickel, "2000,0;2001,0;2002,0;2003,13987;2004,6157;2005,6602;2006,8861;2007,7763;2008,2253;2009,1878;2010,4603;2011,2846"));
		session.save(new BitreData(sydney, oilPetroleum, "2000,974496;2001,716030;2002,416839;2003,582552;2004,709912;2005,755011;2006,658770;2007,930324;2008,553004;2009,513809;2010,766420;2011,965563"));
		session.save(new BitreData(sydney, silicaSand, "2000,0;2001,0;2002,0;2003,3108;2004,1231;2005,3108;2006,1231;2007,674;2008,383;2009,1032;2010,963;2011,0"));
		session.save(new BitreData(sydney, steel, "2000,11457;2001,7259;2002,10881;2003,376815;2004,317519;2005,382597;2006,467548;2007,427982;2008,407755;2009,391421;2010,355968;2011,245878"));
		session.save(new BitreData(sydney, sugar, "2000,0;2001,0;2002,0;2003,7403;2004,5095;2005,17135;2006,20352;2007,20815;2008,15426;2009,18536;2010,24456;2011,17308"));
		session.save(new BitreData(sydney, timberLogs, "2000,0;2001,608;2002,0;2003,389;2004,315;2005,281;2006,20;2007,14970;2008,74522;2009,59294;2010,96962;2011,91433"));
		session.save(new BitreData(sydney, timberProducts, "2000,1056;2001,52487;2002,42934;2003,71431;2004,56240;2005,73734;2006,100396;2007,130685;2008,165801;2009,149399;2010,150821;2011,144579"));
		session.save(new BitreData(sydney, timberWoodchips, "2000,0;2001,69;2002,0;2003,0;2004,0;2005,0;2006,723;2007,2467;2008,3313;2009,5264;2010,4962;2011,545"));
		session.save(new BitreData(sydney, wool, "2000,179935;2001,148936;2002,103357;2003,96713;2004,111183;2005,258733;2006,108534;2007,93141;2008,85932;2009,88885;2010,87392;2011,74990"));
		session.save(new BitreData(sydney, zincOre, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,3108;2006,1863;2007,3291;2008,7893;2009,2225;2010,944;2011,323"));
		session.save(new BitreData(sydney, zincRefined, "2000,0;2001,0;2002,0;2003,8065;2004,1706;2005,2933;2006,2981;2007,4642;2008,1967;2009,2762;2010,1502;2011,576"));
		
		// Yamba
		Seaport yamba = (Seaport)(session.get(Seaport.class, "AUYBA"));
		session.save(new BitreData(yamba, bulkImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,36"));
		session.save(new BitreData(yamba, bulkExport, "2000,96;2001,410;2002,886;2003,816;2004,973;2005,951;2006,950;2007,922;2008,0;2009,0;2010,0;2011,1732"));
		session.save(new BitreData(yamba, generalImport, "2000,564;2001,579;2002,674;2003,427;2004,772;2005,2004;2006,476;2007,3456;2008,4709;2009,229;2010,489;2011,0"));
		session.save(new BitreData(yamba, generalExport, "2000,12174;2001,10283;2002,8499;2003,11434;2004,10317;2005,11400;2006,8442;2007,11825;2008,11293;2009,7482;2010,4854;2011,39"));
		session.save(new BitreData(yamba, containerizedImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(yamba, containerizedExport, "2000,112;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,39"));
		
		session.save(new BitreData(yamba, bulkLiquids, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,1;2011,2"));
		session.save(new BitreData(yamba, carCarriers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(yamba, containers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,1;2011,0"));
		session.save(new BitreData(yamba, dryBulk, "2000,1;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(yamba, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(yamba, generalCargo, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(yamba, livestockCarriers, "2000,55;2001,44;2002,35;2003,39;2004,44;2005,45;2006,38;2007,44;2008,46;2009,27;2010,20;2011,8"));
		
		session.save(new BitreData(yamba, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,32;2007,5;2008,4;2009,5;2010,1;2011,3"));
		session.save(new BitreData(yamba, motorVehicle, "2000,50;2001,92;2002,83;2003,97;2004,48;2005,44;2006,30;2007,39;2008,41;2009,20;2010,0;2011,0"));
		session.save(new BitreData(yamba, oilPetroleum, "2000,1357;2001,0;2002,1505;2003,1490;2004,1522;2005,1572;2006,1504;2007,624;2008,1402;2009,349;2010,0;2011,0"));
		session.save(new BitreData(yamba, steel, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,41;2008,8;2009,0;2010,0;2011,2"));
		session.save(new BitreData(yamba, timberLogs, "2000,5500;2001,4633;2002,3100;2003,6223;2004,5521;2005,6450;2006,3858;2007,4;2008,3952;2009,3860;2010,2178;2011,0"));
		session.save(new BitreData(yamba, timberProducts, "2000,154;2001,0;2002,0;2003,0;2004,0;2005,0;2006,261;2007,6039;2008,619;2009,204;2010,229;2011,1233"));
		
		// Albany
		Seaport albany = (Seaport)(session.get(Seaport.class, "AUALH"));
		session.save(new BitreData(albany, bulkImport, "2000,239980;2001,156314;2002,140694;2003,153910;2004,151647;2005,120545;2006,89651;2007,110926;2008,144368;2009,127280;2010,114305;2011,107335"));
		session.save(new BitreData(albany, bulkExport, "2000,1425426;2001,1434123;2002,1813468;2003,2684629;2004,2834170;2005,2538167;2006,3408478;2007,3485273;2008,4024311;2009,3292527;2010,2954333;2011,3348777"));
		session.save(new BitreData(albany, generalImport, "2000,4225;2001,4087;2002,7084;2003,2486;2004,4418;2005,1249;2006,2948;2007,751;2008,0;2009,0;2010,502;2011,0"));
		session.save(new BitreData(albany, generalExport, "2000,3779;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,69049;2008,0;2009,0;2010,0;2011,0"));
		// Not saving only 0 values
		//session.save(new BitreData(albany, containerizedImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		//session.save(new BitreData(albany, containerizedExport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		
		session.save(new BitreData(albany, bulkLiquids, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(albany, carCarriers, "2000,4;2001,4;2002,2;2003,2;2004,0;2005,2;2006,2;2007,5;2008,3;2009,5;2010,8;2011,7"));
		session.save(new BitreData(albany, containers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(albany, dryBulk, "2000,81;2001,73;2002,94;2003,107;2004,89;2005,86;2006,113;2007,117;2008,152;2009,136;2010,113;2011,117"));
		session.save(new BitreData(albany, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(albany, generalCargo, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(albany, livestockCarriers, "2000,5;2001,7;2002,8;2003,7;2004,31;2005,3;2006,4;2007,7;2008,0;2009,0;2010,1;2011,0"));
		
		session.save(new BitreData(albany, grain, "2000,1341576;2001,1239554;2002,1478364;2003,2303229;2004,2247688;2005,1292551;2006,1942348;2007,1785564;2008,2210174;2009,1841760;2010,1344251;2011,1762549"));
		session.save(new BitreData(albany, silicaSand, "2000,83850;2001,123829;2002,122258;2003,158215;2004,127750;2005,162300;2006,56065;2007,138224;2008,89650;2009,160875;2010,175522;2011,169599"));
		session.save(new BitreData(albany, timberLogs, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,69049;2008,97950;2009,65918;2010,0;2011,0"));
		session.save(new BitreData(albany, timberWoodchips, "2000,0;2001,70740;2002,212846;2003,223185;2004,458732;2005,1083316;2006,1410065;2007,1561485;2008,1626537;2009,1197443;2010,1382398;2011,1373491"));
		
		// Bunbury
		Seaport bunbury = (Seaport)(session.get(Seaport.class, "AUBUY"));
		session.save(new BitreData(bunbury, bulkImport, "2000,1156180;2001,1116171;2002,1147445;2003,1120387;2004,1200701;2005,1232389;2006,1361864;2007,1486165;2008,1538472;2009,1427814;2010,1583265;2011,1640291"));
		session.save(new BitreData(bunbury, bulkExport, "2000,10104313;2001,10321725;2002,10878709;2003,10467235;2004,11005185;2005,10959918;2006,12148967;2007,12106794;2008,11732802;2009,12392270;2010,12349687;2011,12595832"));
		session.save(new BitreData(bunbury, generalImport, "2000,4167;2001,0;2002,1503;2003,996;2004,1864;2005,0;2006,0;2007,6652;2008,0;2009,29188;2010,45506;2011,3515"));
		session.save(new BitreData(bunbury, generalExport, "2000,8589;2001,37892;2002,19142;2003,139558;2004,59176;2005,12724;2006,11505;2007,59137;2008,5975;2009,17615;2010,19108;2011,33972"));
		session.save(new BitreData(bunbury, containerizedImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,639;2011,101"));
		session.save(new BitreData(bunbury, containerizedExport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		
		session.save(new BitreData(bunbury, bulkLiquids, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(bunbury, carCarriers, "2000,46;2001,36;2002,38;2003,36;2004,33;2005,31;2006,35;2007,28;2008,42;2009,51;2010,50;2011,38"));
		session.save(new BitreData(bunbury, containers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(bunbury, dryBulk, "2000,287;2001,283;2002,290;2003,285;2004,287;2005,282;2006,313;2007,306;2008,288;2009,327;2010,330;2011,364"));
		session.save(new BitreData(bunbury, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(bunbury, generalCargo, "2000,0;2001,0;2002,0;2003,0;2004,1;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(bunbury, livestockCarriers, "2000,9;2001,13;2002,9;2003,8;2004,5;2005,4;2006,5;2007,3;2008,4;2009,13;2010,34;2011,9"));
		
		session.save(new BitreData(bunbury, alumina, "2000,7715209;2001,8188280;2002,8248673;2003,8435401;2004,8469142;2005,8531913;2006,9127092;2007,9315589;2008,9476391;2009,9663730;2010,9439269;2011,9496531"));
		session.save(new BitreData(bunbury, copperConcentrate, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,108507;2010,155739;2011,215846"));
		session.save(new BitreData(bunbury, mineralSand, "2000,932630;2001,837975;2002,1005415;2003,806308;2004,852912;2005,1014241;2006,1113234;2007,1096167;2008,732732;2009,720774;2010,482458;2011,486812"));
		session.save(new BitreData(bunbury, oilPetroleum, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,10691;2011,23286"));
		session.save(new BitreData(bunbury, silicaSand, "2000,383077;2001,375619;2002,459192;2003,348810;2004,334532;2005,257105;2006,289045;2007,135141;2008,218068;2009,250524;2010,245551;2011,350220"));
		session.save(new BitreData(bunbury, sugar, "2000,0;2001,0;2002,0;2003,10025;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(bunbury, timberLogs, "2000,0;2001,22986;2002,10403;2003,130312;2004,39129;2005,50445;2006,63583;2007,43015;2008,0;2009,87290;2010,85974;2011,42567"));
		session.save(new BitreData(bunbury, timberWoodchips, "2000,982842;2001,809623;2002,1045693;2003,734705;2004,1212621;2005,966090;2006,1283402;2007,1347925;2008,1094405;2009,1313394;2010,1522675;2011,1389988"));
		
		// Esperance
		Seaport esperance = (Seaport)(session.get(Seaport.class, "AUEPR"));
		session.save(new BitreData(esperance, bulkImport, "2000,375186;2001,424032;2002,431247;2003,366082;2004,379968;2005,362764;2006,498338;2007,690297;2008,489899;2009,362365;2010,397278;2011,717235"));
		session.save(new BitreData(esperance, bulkExport, "2000,3910219;2001,5754770;2002,5578288;2003,6929580;2004,7394155;2005,7943835;2006,9451014;2007,9188726;2008,9388909;2009,10758035;2010,10464965;2011,10616063"));
		session.save(new BitreData(esperance, generalImport, "2000,878;2001,0;2002,0;2003,1469;2004,0;2005,2564;2006,377;2007,18623;2008,11566;2009,9356;2010,16674;2011,51076"));
		session.save(new BitreData(esperance, generalExport, "2000,4738;2001,5410;2002,0;2003,0;2004,0;2005,0;2006,0;2007,36481;2008,64355;2009,137231;2010,241341;2011,366793"));
		session.save(new BitreData(esperance, containerizedImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,4961;2007,17333;2008,11566;2009,9356;2010,16674;2011,50837"));
		session.save(new BitreData(esperance, containerizedExport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,36481;2008,64355;2009,61613;2010,233375;2011,284256"));
		
		session.save(new BitreData(esperance, bulkLiquids, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,4;2007,0;2008,11;2009,16;2010,31;2011,58"));
		session.save(new BitreData(esperance, carCarriers, "2000,15;2001,15;2002,19;2003,14;2004,20;2005,31;2006,41;2007,36;2008,27;2009,19;2010,18;2011,22"));
		session.save(new BitreData(esperance, containers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(esperance, dryBulk, "2000,128;2001,147;2002,124;2003,136;2004,132;2005,136;2006,139;2007,138;2008,170;2009,167;2010,135;2011,135"));
		session.save(new BitreData(esperance, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(esperance, generalCargo, "2000,1;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(esperance, livestockCarriers, "2000,3;2001,3;2002,0;2003,2;2004,0;2005,6;2006,0;2007,1;2008,0;2009,0;2010,0;2011,0"));
		
		session.save(new BitreData(esperance, grain, "2000,990946;2001,1278762;2002,1106859;2003,1560923;2004,1797246;2005,1646154;2006,1625550;2007,1559536;2008,1697345;2009,1338209;2010,1607665;2011,1692394"));
		session.save(new BitreData(esperance, ironOre, "2000,2349133;2001,4177294;2002,4205182;2003,5107886;2004,5374973;2005,5998309;2006,7572075;2007,;2008,7392929;2009,9211331;2010,8807994;2011,8904585"));
		session.save(new BitreData(esperance, leadConcentrate, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,86262;2006,79588;2007,0;2008,8925;2009,0;2010,0;2011,0"));
		session.save(new BitreData(esperance, liveStock, "2000,2143;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(esperance, nickel, "2000,0;2001,0;2002,0;2003,260771;2004,221936;2005,213110;2006,173801;2007,229130;2008,328019;2009,323684;2010,290647;2011,299987"));
		
		// Fremantle
		Seaport fremantle = (Seaport)(session.get(Seaport.class, "AUFRE"));
		session.save(new BitreData(fremantle, bulkImport, "2000,7848851;2001,8509407;2002,8495272;2003,9004040;2004,8724958;2005,8600245;2006,8916422;2007,9645877;2008,9304843;2009,8934320;2010,9613314;2011,9593699"));
		session.save(new BitreData(fremantle, bulkExport, "2000,10263464;2001,9324487;2002,9611262;2003,11102529;2004,10981062;2005,10657116;2006,9599477;2007,8930222;2008,10228937;2009,10516865;2010,9632485;2011,11333945"));
		session.save(new BitreData(fremantle, generalImport, "2000,1880634;2001,2118586;2002,2527183;2003,2722891;2004,2708745;2005,2636069;2006,3089843;2007,3322593;2008,505339;2009,467704;2010,465218;2011,4039152"));
		session.save(new BitreData(fremantle, generalExport, "2000,2608197;2001,2699069;2002,2855840;2003,3109640;2004,3131477;2005,3150763;2006,3405723;2007,4185218;2008,497135;2009,531576;2010,533676;2011,3245025"));
		session.save(new BitreData(fremantle, containerizedImport, "2000,1672074;2001,1874418;2002,2245936;2003,2388669;2004,2249762;2005,2218007;2006,2590173;2007,3465252;2008,2734346;2009,2700961;2010,3012424;2011,3333531"));
		session.save(new BitreData(fremantle, containerizedExport, "2000,2091079;2001,2283266;2002,2437404;2003,2740174;2004,2586174;2005,2644289;2006,2903579;2007,4394801;2008,3316739;2009,3017047;2010,2865775;2011,2762577"));
		
		session.save(new BitreData(fremantle, bulkLiquids, "2000,563;2001,574;2002,520;2003,469;2004,1183;2005,483;2006,497;2007,519;2008,548;2009,517;2010,558;2011,479"));
		session.save(new BitreData(fremantle, carCarriers, "2000,206;2001,218;2002,228;2003,249;2004,249;2005,272;2006,264;2007,262;2008,257;2009,247;2010,254;2011,257"));
		session.save(new BitreData(fremantle, containers, "2000,10;2001,8;2002,9;2003,9;2004,6;2005,2;2006,6;2007,5;2008,8;2009,9;2010,3;2011,3"));
		session.save(new BitreData(fremantle, dryBulk, "2000,373;2001,355;2002,336;2003,382;2004,376;2005,350;2006,318;2007,301;2008,372;2009,371;2010,303;2011,377"));
		session.save(new BitreData(fremantle, gas, "2000,98;2001,106;2002,109;2003,127;2004,135;2005,141;2006,157;2007,170;2008,127;2009,151;2010,181;2011,202"));
		session.save(new BitreData(fremantle, generalCargo, "2000,147;2001,132;2002,126;2003,105;2004,88;2005,76;2006,95;2007,85;2008,86;2009,68;2010,70;2011,50"));
		session.save(new BitreData(fremantle, livestockCarriers, "2000,207;2001,196;2002,241;2003,204;2004,217;2005,251;2006,291;2007,318;2008,376;2009,307;2010,336;2011,366"));
		
		session.save(new BitreData(fremantle, alumina, "2000,2884528;2001,2765000;2002,2734201;2003,2814169;2004,2672378;2005,2934774;2006,2813148;2007,3050380;2008,2766998;2009,2845405;2010,2817320;2011,2876814"));
		session.save(new BitreData(fremantle, bauxite, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,128720;2010,0;2011,0"));
		session.save(new BitreData(fremantle, coal, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,42192;2007,410664;2008,579917;2009,545199;2010,1133279;2011,487740"));
		session.save(new BitreData(fremantle, gas, "2000,234553;2001,225000;2002,230006;2003,230046;2004,198709;2005,38557;2006,71485;2007,52333;2008,54482;2009,53604;2010,32351;2011,31904"));
		session.save(new BitreData(fremantle, grain, "2000,3676812;2001,3491868;2002,3308072;2003,5031921;2004,5348488;2005,5132539;2006,4061069;2007,3849820;2008,4147917;2009,4301339;2010,3185106;2011,3885257"));
		session.save(new BitreData(fremantle, ironOre, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,44000;2010,90995;2011,1827990"));
		session.save(new BitreData(fremantle, liveStock, "2000,283027;2001,232000;2002,219934;2003,164952;2004,167385;2005,206605;2006,194969;2007,186636;2008,194175;2009,162391;2010,189419;2011,118963"));
		session.save(new BitreData(fremantle, mineralSand, "2000,295683;2001,394000;2002,347656;2003,304357;2004,202261;2005,111809;2006,122430;2007,109675;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, motorVehicle, "2000,645;2001,2823;2002,1135;2003,1995;2004,3233;2005,2074;2006,2443;2007,6214;2008,1358;2009,2019;2010,5385;2011,3206"));
		session.save(new BitreData(fremantle, nickel, "2000,63818;2001,40000;2002,37745;2003,50293;2004,37648;2005,56471;2006,129485;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, oilPetroleum, "2000,2391877;2001,0;2002,2403372;2003,2578737;2004,2455538;2005,2076435;2006,2550308;2007,2365109;2008,2089237;2009,2051398;2010,2342488;2011,1705230"));
		session.save(new BitreData(fremantle, silicaSand, "2000,346414;2001,207000;2002,230411;2003,112165;2004,125311;2005,111533;2006,85932;2007,8086;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, steel, "2000,0;2001,7016;2002,11726;2003,25321;2004,56127;2005,378713;2006,339907;2007,123180;2008,832;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, sugar, "2000,0;2001,0;2002,1704;2003,36;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, timberLogs, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,17999;2007,21216;2008,5;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, timberProducts, "2000,26682;2001,30000;2002,28638;2003,16541;2004,0;2005,0;2006,23644;2007,17401;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(fremantle, wool, "2000,116002;2001,95000;2002,73194;2003,83225;2004,85823;2005,89841;2006,85165;2007,73599;2008,0;2009,0;2010,67864;2011,48628"));
		
		// Geraldton
		Seaport geraldton = (Seaport)(session.get(Seaport.class, "AUGET"));
		session.save(new BitreData(geraldton, bulkImport, "2000,290993;2001,297963;2002,325431;2003,387988;2004,336300;2005,312533;2006,200871;2007,227442;2008,267169;2009,471034;2010,1100388;2011,1100060"));
		session.save(new BitreData(geraldton, bulkExport, "2000,2529272;2001,2334310;2002,2156911;2003,3953577;2004,5140089;2005,4872419;2006,6222587;2007,6441830;2008,7346256;2009,8499706;2010,8861783;2011,9216470"));
		session.save(new BitreData(geraldton, generalImport, "2000,0;2001,12;2002,9267;2003,12463;2004,17537;2005,24526;2006,10633;2007,39596;2008,27364;2009,722;2010,40109;2011,107938"));
		session.save(new BitreData(geraldton, generalExport, "2000,0;2001,0;2002,0;2003,3980;2004,8603;2005,4530;2006,908;2007,0;2008,15332;2009,17094;2010,2104;2011,3013"));
		session.save(new BitreData(geraldton, containerizedImport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, containerizedExport, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		
		session.save(new BitreData(geraldton, bulkLiquids, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, carCarriers, "2000,7;2001,5;2002,7;2003,8;2004,11;2005,0;2006,0;2007,14;2008,19;2009,21;2010,15;2011,24"));
		session.save(new BitreData(geraldton, containers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,11;2006,9;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, dryBulk, "2000,170;2001,166;2002,168;2003,224;2004,258;2005,254;2006,263;2007,200;2008,258;2009,298;2010,302;2011,294"));
		session.save(new BitreData(geraldton, gas, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, generalCargo, "2000,16;2001,19;2002,17;2003,21;2004,20;2005,20;2006,17;2007,15;2008,23;2009,27;2010,2;2011,4"));
		session.save(new BitreData(geraldton, livestockCarriers, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,19;2006,16;2007,9;2008,12;2009,1;2010,12;2011,62"));
		
		session.save(new BitreData(geraldton, copperOre, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,7062"));
		session.save(new BitreData(geraldton, copperConcentrate, "2000,51529;2001,119602;2002,144752;2003,135662;2004,81881;2005,76220;2006,90508;2007,92988;2008,166558;2009,186457;2010,164872;2011,186871"));
		session.save(new BitreData(geraldton, grain, "2000,1463728;2001,1250100;2002,980962;2003,2194963;2004,2124150;2005,2024386;2006,1314679;2007,613102;2008,1932637;2009,2132348;2010,1828777;2011,2655438"));
		session.save(new BitreData(geraldton, ironOre, "2000,0;2001,0;2002,0;2003,386973;2004,1866491;2005,1706004;2006,3470666;2007,4433031;2008,4167085;2009,5315521;2010,5890591;2011,5261289"));
		session.save(new BitreData(geraldton, leadOre, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,7286;2011,13419"));
		session.save(new BitreData(geraldton, leadConcentrate, "2000,21903;2001,13813;2002,16932;2003,5410;2004,11015;2005,16314;2006,32959;2007,20750;2008,0;2009,0;2010,0;2011,22118"));
		session.save(new BitreData(geraldton, liveStock, "2000,6751;2001,6993;2002,10619;2003,12139;2004,13315;2005,9237;2006,10291;2007,9190;2008,15302;2009,16951;2010,2009;2011,1361"));
		session.save(new BitreData(geraldton, manganese, "2000,0;2001,0;2002,19725;2003,0;2004,0;2005,6531;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, mineralSand, "2000,569803;2001,771836;2002,848359;2003,866584;2004,907042;2005,836262;2006,1004759;2007,973781;2008,464964;2009,437959;2010,604486;2011,810135"));
		session.save(new BitreData(geraldton, nickel, "2000,29124;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,0;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, oilPetroleum, "2000,0;2001,0;2002,0;2003,0;2004,0;2005,0;2006,0;2007,5338;2008,0;2009,0;2010,0;2011,0"));
		session.save(new BitreData(geraldton, zincConcentrate, "2000,203613;2001,170553;2002,133875;2003,130753;2004,134300;2005,195531;2006,296975;2007,291925;2008,349624;2009,234964;2010,185817;2011,154193"));
	}
}
