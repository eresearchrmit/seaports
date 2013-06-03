/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import war.model.*;

/**
 * Class used to load ACORN-SAT dataset in the database
 * @author Guillaume Prevost
 */
@SuppressWarnings("deprecation")
public class AcornSatDataLoader {
	
	/**
	 * Main method used to load ACORN-SAT data only.
	 * On an existing database, this may duplicate data.
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
		
		LoadAcornSatData(session);
		
		session.getTransaction().commit();
	}
	
	/**
	 * Loads the ACORN-SAT dataset in the database
	 * @param session: the Hibernate Session object which takes care of persisting objects in the database
	 */
	public static void LoadAcornSatData(Session session)
	{
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat yearFormatter = new SimpleDateFormat("yyyy");
		
		Region r1 = (Region)(session.get(Region.class, 1));
		Region r2 = (Region)(session.get(Region.class, 2));
		Region r3 = (Region)(session.get(Region.class, 3));
		
		// ACORN-Sat Stations
		AcornSatStation yamba = new AcornSatStation(058012D, "Yamba", -29.43, 153.36, r1);
		AcornSatStation coffsHarbour = new AcornSatStation(059040D, "Coffs Harbour", -30.31, 153.12, r1);
		AcornSatStation williamtown = new AcornSatStation(061078D, "Williamtown", -32.79, 151.84, r1);
		AcornSatStation sydney = new AcornSatStation(066062D, "Sydney", -33.86, 151.21, r1);
		
		AcornSatStation moruyaHeads = new AcornSatStation(069018D, "Moruya Heads", -35.91, 150.15, r2);
		AcornSatStation gaboIslands = new AcornSatStation(084016D, "Gabo Island", -37.57, 149.92, r2);
		AcornSatStation sale = new AcornSatStation(085072D, "Sale", -38.12, 147.13, r2);
		AcornSatStation wilsonPromontory = new AcornSatStation(085096D, "Wilsons Promontory", -39.13, 146.42, r2);
		
		AcornSatStation geraldton = new AcornSatStation(008051D, "Geraldton", -28.80, 114.70, r3);
		AcornSatStation perth = new AcornSatStation(009021D, "Perth", -31.93, 115.98, r3);
		AcornSatStation bridgetown = new AcornSatStation(009510D, "Bridgetown", -33.96, 116.14, r3);
		AcornSatStation capeLeeuwin = new AcornSatStation(009518D, "Cape Leeuwin", -34.37, 115.14, r3);
		AcornSatStation albany = new AcornSatStation(009741D, "Albany", -34.94, 117.80, r3);
		AcornSatStation esperance = new AcornSatStation(009789D, "Esperance", -33.83, 121.89, r3);
		
		session.save(yamba);
		session.save(coffsHarbour);
		session.save(williamtown);
		session.save(sydney);
		session.save(moruyaHeads);
		session.save(gaboIslands);
		session.save(sale);
		session.save(wilsonPromontory);
		session.save(geraldton);
		session.save(perth);
		session.save(bridgetown);
		session.save(capeLeeuwin);
		session.save(albany);
		session.save(esperance);
		
		// ACORN-Sat Variables
		AcornSatVariable meanTemp = new AcornSatVariable("Surface Temperature", "T", "Measured sea surface temperature", "&#8451;", "&#8451;");
		AcornSatVariable meanRainfall = new AcornSatVariable("Rainfall", "RF", "Measured rain fall", "mm");
		AcornSatVariable meanRelHumidity = new AcornSatVariable("Measured relative humidity", "RH", "Measured relative humidity", "%");
		AcornSatVariable meanWindSpeed = new AcornSatVariable("Wind Speed", "WS", "Measured wind speed", "km/h");
		AcornSatVariable maxTemp = new AcornSatVariable("Highest temperature", "Max.T", "Highest measured temperature", "&#8451;", "&#8451;");
		AcornSatVariable maxRainfall = new AcornSatVariable("Highest daily rainfall", "Max.RF", "Highest measured rainfall", "mm");
		AcornSatVariable maxWindSpeed = new AcornSatVariable("Maximum wind gust", "Max.WS", "Maximum measured wind speed", "km/h");
		
		session.save(meanTemp);
		session.save(meanRainfall);
		session.save(meanRelHumidity);
		session.save(meanWindSpeed);
		session.save(maxTemp);
		session.save(maxRainfall);
		session.save(maxWindSpeed);
		
		// ACORN-Sat Data		
		Date periodStart;
		try { periodStart = yearFormatter.parse("1981"); }
		catch (ParseException e) { periodStart = new Date(); }
		Date periodEnd;
		try { periodEnd = yearFormatter.parse("2010"); }
		catch (ParseException e) { periodEnd = new Date(); }

		SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
		Date morning;
		try { morning = timeFormatter.parse("09:00:00"); }
		catch (ParseException e) { morning = new Date(); }
		Date afternoon;
		try { afternoon = timeFormatter.parse("15:00:00"); }
		catch (ParseException e) { afternoon = new Date(); }
		Date highestRecordDate;
		
		
		// Yamba
		session.save(new AcornSatData(yamba, meanTemp, false, periodStart, periodEnd, 19.6, null));
		session.save(new AcornSatData(yamba, meanRainfall, false, periodStart, periodEnd, 1473.6, null));
		session.save(new AcornSatData(yamba, meanRelHumidity, false, periodStart, periodEnd, 76.0, morning));
		session.save(new AcornSatData(yamba, meanRelHumidity, false, periodStart, periodEnd, 68.0, afternoon));
		session.save(new AcornSatData(yamba, meanWindSpeed, false, periodStart, periodEnd, 11.8, morning));
		session.save(new AcornSatData(yamba, meanWindSpeed, false, periodStart, periodEnd, 18.1, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2002-01-12"); 
			session.save(new AcornSatData(yamba, maxTemp, true, periodStart, periodEnd, 42.5, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1999-03-02");
			session.save(new AcornSatData(yamba, maxRainfall, true, periodStart, periodEnd, 300.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Coffs Harbour
		session.save(new AcornSatData(coffsHarbour, meanTemp, false, periodStart, periodEnd, 18.9, null));
		session.save(new AcornSatData(coffsHarbour, meanRainfall, false, periodStart, periodEnd, 1658.8, null));
		session.save(new AcornSatData(coffsHarbour, meanRelHumidity, false, periodStart, periodEnd, 67.0, morning));
		session.save(new AcornSatData(coffsHarbour, meanRelHumidity, false, periodStart, periodEnd, 63.0, afternoon));
		session.save(new AcornSatData(coffsHarbour, meanWindSpeed, false, periodStart, periodEnd, 13.2, morning));
		session.save(new AcornSatData(coffsHarbour, meanWindSpeed, false, periodStart, periodEnd, 19.4, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2002-01-12");
			session.save(new AcornSatData(coffsHarbour, maxTemp, true, periodStart, periodEnd, 43.3, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("2009-11-07");
			session.save(new AcornSatData(coffsHarbour, maxRainfall, true, periodStart, periodEnd, 371.0, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1985-07-09");
			session.save(new AcornSatData(coffsHarbour, maxWindSpeed, true, periodStart, periodEnd, 124.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Williamtown
		session.save(new AcornSatData(williamtown, meanTemp, false, periodStart, periodEnd, 17.9, null));
		session.save(new AcornSatData(williamtown, meanRainfall, false, periodStart, periodEnd, 1126.5, null));
		session.save(new AcornSatData(williamtown, meanRelHumidity, false, periodStart, periodEnd, 73.0, morning));
		session.save(new AcornSatData(williamtown, meanRelHumidity, false, periodStart, periodEnd, 56.0, afternoon));
		session.save(new AcornSatData(williamtown, meanWindSpeed, false, periodStart, periodEnd, 14.5, morning));
		session.save(new AcornSatData(williamtown, meanWindSpeed, false, periodStart, periodEnd, 21.3, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2006-01-01");
			session.save(new AcornSatData(williamtown, maxTemp, true, periodStart, periodEnd, 44.4, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1990-02-03");
			session.save(new AcornSatData(williamtown, maxRainfall, true, periodStart, periodEnd, 276.0, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1992-08-12");
			session.save(new AcornSatData(williamtown, maxWindSpeed, true, periodStart, periodEnd, 122.0, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1998-06-23");
			session.save(new AcornSatData(williamtown, maxWindSpeed, true, periodStart, periodEnd, 122.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Sydney
		session.save(new AcornSatData(sydney, meanTemp, false, periodStart, periodEnd, 18.5, null));
		session.save(new AcornSatData(sydney, meanRainfall, false, periodStart, periodEnd, 1222.7, null));
		session.save(new AcornSatData(sydney, meanRelHumidity, false, periodStart, periodEnd, 70.0, morning));
		session.save(new AcornSatData(sydney, meanRelHumidity, false, periodStart, periodEnd, 56.0, afternoon));
		session.save(new AcornSatData(sydney, meanWindSpeed, false, periodStart, periodEnd, 8.3, morning));
		session.save(new AcornSatData(sydney, meanWindSpeed, false, periodStart, periodEnd, 13.9, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2006-01-01");
			session.save(new AcornSatData(sydney, maxTemp, true, periodStart, periodEnd, 44.2, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1986-08-06");
			session.save(new AcornSatData(sydney, maxRainfall, true, periodStart, periodEnd, 327.6, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1981-09-27");
			session.save(new AcornSatData(sydney, maxWindSpeed, true, periodStart, periodEnd, 117.0, highestRecordDate));
		} catch (ParseException e) {}
		
		
		
		// Moruya Heads
		session.save(new AcornSatData(moruyaHeads, meanTemp, false, periodStart, periodEnd, 16.1, null));
		session.save(new AcornSatData(moruyaHeads, meanRainfall, false, periodStart, periodEnd, 951.4, null));
		session.save(new AcornSatData(moruyaHeads, meanRelHumidity, false, periodStart, periodEnd, 76.0, morning));
		session.save(new AcornSatData(moruyaHeads, meanRelHumidity, false, periodStart, periodEnd, 66.0, afternoon));
		session.save(new AcornSatData(moruyaHeads, meanWindSpeed, false, periodStart, periodEnd, 12.1, morning));
		session.save(new AcornSatData(moruyaHeads, meanWindSpeed, false, periodStart, periodEnd, 22.7, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2003-01-30");
			session.save(new AcornSatData(moruyaHeads, maxTemp, true, periodStart, periodEnd, 43.2, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1999-01-29");
			session.save(new AcornSatData(moruyaHeads, maxRainfall, true, periodStart, periodEnd, 206.2, highestRecordDate));
		} catch (ParseException e) {}
		
		// Gabo Island
		session.save(new AcornSatData(gaboIslands, meanTemp, false, periodStart, periodEnd, 15.3, null));
		session.save(new AcornSatData(gaboIslands, meanRainfall, false, periodStart, periodEnd, 913.5, null));
		session.save(new AcornSatData(gaboIslands, meanRelHumidity, false, periodStart, periodEnd, 79.0, morning));
		session.save(new AcornSatData(gaboIslands, meanRelHumidity, false, periodStart, periodEnd, 75.0, afternoon));
		session.save(new AcornSatData(gaboIslands, meanWindSpeed, false, periodStart, periodEnd, 24.8, morning));
		session.save(new AcornSatData(gaboIslands, meanWindSpeed, false, periodStart, periodEnd, 32.8, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("1990-01-03");
			session.save(new AcornSatData(gaboIslands, maxTemp, true, periodStart, periodEnd, 40.8, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1999-01-24");
			session.save(new AcornSatData(gaboIslands, maxRainfall, true, periodStart, periodEnd, 237.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Sale
		session.save(new AcornSatData(sale, meanTemp, false, periodStart, periodEnd, 14.0, null));
		session.save(new AcornSatData(sale, meanRainfall, false, periodStart, periodEnd, 559.2, null));
		session.save(new AcornSatData(sale, meanRelHumidity, false, periodStart, periodEnd, 78.0, morning));
		session.save(new AcornSatData(sale, meanRelHumidity, false, periodStart, periodEnd, 56.0, afternoon));
		session.save(new AcornSatData(sale, meanWindSpeed, false, periodStart, periodEnd, 12.4, morning));
		session.save(new AcornSatData(sale, meanWindSpeed, false, periodStart, periodEnd, 20.6, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("1982-01-24");
			session.save(new AcornSatData(sale, maxTemp, true, periodStart, periodEnd, 44.6, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("2004-04-24");
			session.save(new AcornSatData(sale, maxRainfall, true, periodStart, periodEnd, 108.8, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1998-12-27");
			session.save(new AcornSatData(sale, maxWindSpeed, true, periodStart, periodEnd, 152.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Wilsons Promontory
		session.save(new AcornSatData(wilsonPromontory, meanTemp, false, periodStart, periodEnd, 14.2, null));
		session.save(new AcornSatData(wilsonPromontory, meanRainfall, false, periodStart, periodEnd, 1059.0, null));
		session.save(new AcornSatData(wilsonPromontory, meanRelHumidity, false, periodStart, periodEnd, 75.0, morning));
		session.save(new AcornSatData(wilsonPromontory, meanRelHumidity, false, periodStart, periodEnd, 72.0, afternoon));
		session.save(new AcornSatData(wilsonPromontory, meanWindSpeed, false, periodStart, periodEnd, 34.1, morning));
		session.save(new AcornSatData(wilsonPromontory, meanWindSpeed, false, periodStart, periodEnd, 35.8, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2009-02-07");
			session.save(new AcornSatData(wilsonPromontory, maxTemp, true, periodStart, periodEnd, 42.0, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1988-05-20");
			session.save(new AcornSatData(wilsonPromontory, maxRainfall, true, periodStart, periodEnd, 123.2, highestRecordDate));
		} catch (ParseException e) {}
		
		
		
		// Geraldton
		session.save(new AcornSatData(geraldton, meanTemp, false, periodStart, periodEnd, 19.9, null));
		session.save(new AcornSatData(geraldton, meanRainfall, false, periodStart, periodEnd, 416.3, null));
		session.save(new AcornSatData(geraldton, meanRelHumidity, false, periodStart, periodEnd, 59.0, morning));
		session.save(new AcornSatData(geraldton, meanRelHumidity, false, periodStart, periodEnd, 48.0, afternoon));
		session.save(new AcornSatData(geraldton, meanWindSpeed, false, periodStart, periodEnd, 19.4, morning));
		session.save(new AcornSatData(geraldton, meanWindSpeed, false, periodStart, periodEnd, 25.0, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("1985-02-20");
			session.save(new AcornSatData(geraldton, maxTemp, true, periodStart, periodEnd, 47.3, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1999-03-20");
			session.save(new AcornSatData(geraldton, maxRainfall, true, periodStart, periodEnd, 100.8, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1994-05-23");
			session.save(new AcornSatData(geraldton, maxWindSpeed, true, periodStart, periodEnd, 128.8, highestRecordDate));
		} catch (ParseException e) {}
		
		
		// Perth
		session.save(new AcornSatData(perth, meanTemp, false, periodStart, periodEnd, 18.6, null));
		session.save(new AcornSatData(perth, meanRainfall, false, periodStart, periodEnd, 725.0, null));
		session.save(new AcornSatData(perth, meanRelHumidity, false, periodStart, periodEnd, 62.0, morning));
		session.save(new AcornSatData(perth, meanRelHumidity, false, periodStart, periodEnd, 45.0, afternoon));
		session.save(new AcornSatData(perth, meanWindSpeed, false, periodStart, periodEnd, 15.4, morning));
		session.save(new AcornSatData(perth, meanWindSpeed, false, periodStart, periodEnd, 19.5, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("1991-02-23");
			session.save(new AcornSatData(perth, maxTemp, true, periodStart, periodEnd, 46.7, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1992-02-09");
			session.save(new AcornSatData(perth, maxRainfall, true, periodStart, periodEnd, 132.0, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1990-07-22");
			session.save(new AcornSatData(perth, maxWindSpeed, true, periodStart, periodEnd, 124.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Bridgetown
		session.save(new AcornSatData(bridgetown, meanTemp, false, periodStart, periodEnd, 15.6, null));
		session.save(new AcornSatData(bridgetown, meanRainfall, false, periodStart, periodEnd, 756.7, null));
		session.save(new AcornSatData(bridgetown, meanRelHumidity, false, periodStart, periodEnd, 79.0, morning));
		session.save(new AcornSatData(bridgetown, meanRelHumidity, false, periodStart, periodEnd, 57.0, afternoon));
		session.save(new AcornSatData(bridgetown, meanWindSpeed, false, periodStart, periodEnd, 5.8, morning));
		session.save(new AcornSatData(bridgetown, meanWindSpeed, false, periodStart, periodEnd, 8.2, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("1991-01-31");
			session.save(new AcornSatData(bridgetown, maxTemp, true, periodStart, periodEnd, 43.5, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1985-04-09");
			session.save(new AcornSatData(bridgetown, maxRainfall, true, periodStart, periodEnd, 84.2, highestRecordDate));
		} catch (ParseException e) {}
		
		// Cape Leeuwin
		session.save(new AcornSatData(capeLeeuwin, meanTemp, false, periodStart, periodEnd, 18.0, null));
		session.save(new AcornSatData(capeLeeuwin, meanRainfall, false, periodStart, periodEnd, 890.9, null));
		session.save(new AcornSatData(capeLeeuwin, meanRelHumidity, false, periodStart, periodEnd, 71.0, morning));
		session.save(new AcornSatData(capeLeeuwin, meanRelHumidity, false, periodStart, periodEnd, 66.0, afternoon));
		session.save(new AcornSatData(capeLeeuwin, meanWindSpeed, false, periodStart, periodEnd, 30.2, morning));
		session.save(new AcornSatData(capeLeeuwin, meanWindSpeed, false, periodStart, periodEnd, 30.7, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2007-03-07");
			session.save(new AcornSatData(capeLeeuwin, maxTemp, true, periodStart, periodEnd, 39.9, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1994-08-11");
			session.save(new AcornSatData(capeLeeuwin, maxRainfall, true, periodStart, periodEnd, 174.0, highestRecordDate));
		} catch (ParseException e) {}
		
		
		// Albany
		session.save(new AcornSatData(albany, meanTemp, false, periodStart, periodEnd, 15.4, null));
		session.save(new AcornSatData(albany, meanRainfall, false, periodStart, periodEnd, 784.7, null));
		session.save(new AcornSatData(albany, meanRelHumidity, false, periodStart, periodEnd, 73.0, morning));
		session.save(new AcornSatData(albany, meanRelHumidity, false, periodStart, periodEnd, 61.0, afternoon));
		session.save(new AcornSatData(albany, meanWindSpeed, false, periodStart, periodEnd, 17.3, morning));
		session.save(new AcornSatData(albany, meanWindSpeed, false, periodStart, periodEnd, 22.4, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("1991-01-31");
			session.save(new AcornSatData(albany, maxTemp, true, periodStart, periodEnd, 45.3, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("2005-04-02");
			session.save(new AcornSatData(albany, maxRainfall, true, periodStart, periodEnd, 108.0, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1983-09-01");
			session.save(new AcornSatData(albany, maxWindSpeed, true, periodStart, periodEnd, 130.0, highestRecordDate));
		} catch (ParseException e) {}
		
		// Esperance
		session.save(new AcornSatData(esperance, meanTemp, false, periodStart, periodEnd, 17.0, null));
		session.save(new AcornSatData(esperance, meanRainfall, false, periodStart, periodEnd, 602.4, null));
		session.save(new AcornSatData(esperance, meanRelHumidity, false, periodStart, periodEnd, 66.0, morning));
		session.save(new AcornSatData(esperance, meanRelHumidity, false, periodStart, periodEnd, 57.0, afternoon));
		session.save(new AcornSatData(esperance, meanWindSpeed, false, periodStart, periodEnd, 19.6, morning));
		session.save(new AcornSatData(esperance, meanWindSpeed, false, periodStart, periodEnd, 24.3, afternoon));
		try {
			highestRecordDate = dateFormatter.parse("2010-01-06");
			session.save(new AcornSatData(esperance, maxTemp, true, periodStart, periodEnd, 46.9, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("2007-01-05");
			session.save(new AcornSatData(esperance, maxRainfall, true, periodStart, periodEnd, 153.2, highestRecordDate));
		} catch (ParseException e) {}
		try {
			highestRecordDate = dateFormatter.parse("1993-05-28");
			session.save(new AcornSatData(esperance, maxWindSpeed, true, periodStart, periodEnd, 152.0, highestRecordDate));
		} catch (ParseException e) {}
	}
}
