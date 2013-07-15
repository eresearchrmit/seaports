/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ands.rifcs.base.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import war.dao.UserStoryDao;
import war.model.User;
import war.model.UserStory;

@Controller
public class RIFCSController {
 
	private static RIFCS rifcs = null;

	@Autowired UserStoryDao userStoryDao;
	
	@RequestMapping(value="public/rifcs", method = RequestMethod.GET)
	public void getRifcsXML(HttpServletRequest request, HttpServletResponse response) {
	
		try {
			RIFCSWrapper mw = new RIFCSWrapper();
	        rifcs = mw.getRIFCSObject();
	        rifcs.addRegistryObject(createCSSServiceRIFCS());
	        rifcs.addRegistryObject(createRMITPartyRIFCS());
	        
	        List<User> users = new ArrayList<User>();
	        List<UserStory> stories = userStoryDao.getAllPublishedStories();
	        for (UserStory story : stories) {
	        	if (!users.contains(story.getOwner()))
	        	{
	        		users.add(story.getOwner());
	        		rifcs.addRegistryObject(createPartyRIFCS(story.getOwner())); // Create a party record for the owner of the story
	        	}
	        	rifcs.addRegistryObject(createCollectionRIFCS(story)); // Create a collection record for the story
	        }
	        
	        //mw.write(System.out);
	        //mw.validate();
	        
	        response.getWriter().write(mw.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistryObject createCSSServiceRIFCS() throws RIFCSException {
		RegistryObject r = rifcs.newRegistryObject();
        r.setKey(KEY_SERVICE_PREFIX + CSS_APP_SERVICE);
        r.setGroup(RMIT_GROUP);
        r.setOriginatingSource(CSS_URL);
        
        Service service = r.newService();
        service.setType("transform");
        service.setDateModified(DATE_LAST_MODIFIED);
        
        // Name
        Name name = service.newName();
        name.setType("primary");
        NamePart namepart = name.newNamePart();
        namepart.setValue("Climate Smart Seaports");
        name.addNamePart(namepart);
        service.addName(name);
        
        // Location
        Location location = service.newLocation();
        Address address = location.newAddress();
        Electronic electronic = address.newElectronic();
        electronic.setType("url");
        electronic.setValue(CSS_URL);
        address.addElectronic(electronic);
        location.addAddress(address);
        service.addLocation(location);
        
        service.addRelatedObject(createRelatedObject("isManagedBy", ERESEARCH_PARTY_KEY, service.newRelatedObject()));
        
        // Description
        service.addDescription("Software", "deliverymethod", "en");
        service.addDescription("Climate Smart Seaports is an online decision support toolkit designed to help Australian seaports adapating to climate change and improving their resilience to it. The toolkit lets users access data from various datasets such as CSIRO, BoM, ABS, BITRE as well as their own personal data. Climate Smart Seaports then allows writing and publishing reports based on this data and the user analysis.", "full", "en");
        
        // Rights
        Right right = service.newRight();
        right.setAccessRights("Open access for browsing reports. Registration required to create reports.");
        right.setLicence("3-clause 'Revised BSD' license", "http://seaports.eres.rmit.edu.au:8080/public/terms-of-service", "BSD");
        service.addRight(right);
        
        r.addService(service);
        return r;
	}
	
	public RegistryObject createRMITPartyRIFCS() throws RIFCSException {
		RegistryObject r = rifcs.newRegistryObject();
        r.setKey(ERESEARCH_PARTY_KEY);
        r.setGroup(RMIT_GROUP);
        r.setOriginatingSource(CSS_URL);
        
        Party rmit = r.newParty();
        rmit.setType("group");
        rmit.setDateModified(DATE_LAST_MODIFIED);
        
        // Name
        Name name = rmit.newName();
        name.setType("primary");
        NamePart namepart = name.newNamePart();
        namepart.setValue("RMIT e-Research Office");
        name.addNamePart(namepart);
        rmit.addName(name);
        
        // Identifier
        Identifier id = rmit.newIdentifier();
        id.setType("local");
        id.setValue(ERESEARCH_PARTY_KEY);
        rmit.addIdentifier(id);
        
        // URL & Email
        Location loc = rmit.newLocation();
        Address address = loc.newAddress();
        Electronic url = address.newElectronic();
        url.setType("url");
        url.setValue("http://www.rmit.edu.au/research/eres");
        address.addElectronic(url);
        Electronic email = address.newElectronic();
        email.setType("email");
        email.setValue("eresearch@rmit.edu.au");
        address.addElectronic(email);
        loc.addAddress(address);
        rmit.addLocation(loc);
        
        rmit.addDescription("&lt;p&gt; The RMIT e-Research Office has been established to facilitate IT systems, software and services support to researchers with high-performance computing (HPC), high-bandwidth network and data-intensive collaborative spaces needs.&lt;/p&gt; &lt;p&gt; Services are centered on major research ICT infrastructure items, such as those funded through ARC Linkage Infrastructure, Equipment and Facilities grants, assistance with multi-institution and multi-disciplinary ICT support, and major state and national e-Research initiatives.&lt;/p&gt; &lt;p&gt; The e-Research office is conducting a survey regarding RMIT&amp;#39;s HPC needs, which will enable the e-Research Office to:&lt;/p&gt; &lt;ul class=\"bulleted1\"&gt; &lt;li&gt; Offer software installation and upgrade support to our HPC community in RMIT through the ITS helpdesk&lt;/li&gt; &lt;li&gt; Compile a web page of our ICT-enabled research projects&lt;/li&gt; &lt;li&gt; Understand better the needs of HPC/Grid/Cloud-enabled research&lt;/li&gt; &lt;li&gt; Represent RMIT in the Victorian and national e-research network&lt;/li&gt; &lt;/ul&gt;", "brief", "en");
        
        Subject subject = rmit.newSubject();
        subject.setValue("0806"); // Information Systems -> Information and Computing Sciences
        subject.setType("anzsrc-for");
        
        // TODO: LVL3 link to an Activity records
        // TODO: LVL3 set existence date
        
        rmit.addRelatedObject(createRelatedObject("isPartOf", "http://nla.gov.au/nla.party-1307378", rmit.newRelatedObject()));
        
        r.addParty(rmit);
        return r;
	}
	
	public RegistryObject createPartyRIFCS(User user) throws RIFCSException {		
		RegistryObject r = rifcs.newRegistryObject();
        r.setKey(KEY_PARTY_PREFIX + user.getUsername());
        r.setGroup(RMIT_GROUP);
        r.setOriginatingSource(CSS_URL);
        
        Party p = r.newParty();
        p.setType("person");
        
        // Name
        Name name = p.newName();
        name.setType("primary");
        name.addNamePart(user.getFirstname(), "given");
        name.addNamePart(user.getLastname(), "family");
        p.addName(name);
        
        Identifier id = p.newIdentifier();
        if (user.getNlaNumber() != null && user.getNlaNumber().length() > 0) {
        	id.setType("AU-ANL:PEAU");
        	id.setValue(user.getNlaNumber());
        }
        else {
        	id.setType("uri");
        	id.setValue(CSS_URL + "public/user/" + user.getUsername());
        }
        p.addIdentifier(id);
        
        // Location
        Location loc = p.newLocation();
        Address address = loc.newAddress();
        Electronic electronic = address.newElectronic();
        electronic.setType("email");
        electronic.setValue(user.getEmail());
        address.addElectronic(electronic);
        loc.addAddress(address);
        p.addLocation(loc);
        
        //p.addRelatedObject(createRelatedObject("isManagedBy", ERESEARCH_PARTY_KEY, p.newRelatedObject()));
        
        // TODO: LVL3 link to an Activity records
        // TODO: LVL3 set subject anzsrc-for
        // TODO: LVL3 set description, type "brief"
        // TODO: LVL3 set existence date
        
        r.addParty(p);
        return r;
	}
	
	public RegistryObject createCollectionRIFCS(UserStory story) throws RIFCSException {
		
		RegistryObject r = rifcs.newRegistryObject();
        r.setKey(KEY_COLLECTION_PREFIX + story.getId());
        r.setGroup(RMIT_GROUP);
        r.setOriginatingSource(CSS_URL + "public/reports/view?id=" + story.getId());
        
		Collection c = r.newCollection();
		
		c.setType("collection");
		c.addIdentifier(HANDLE_PREFIX + "report" + story.getId(), "local");
		c.addIdentifier(CSS_URL + "public/reports/view?id=" + story.getId(), "uri");
        c.setDateModified(story.getPublishDate());
        
        // TODO: LVL3 Add "Dates" element (not available in RIF-CS API 1.3.0)
        
        // Name
        Name name = c.newName();
        name.setType("primary");
        
        NamePart namepart = name.newNamePart();
        namepart.setValue(story.getName());
        name.addNamePart(namepart);
        c.addName(name);
                
        c.addDescription(story.getShortDescription(), "brief", "en");
        c.addDescription(story.getFullDescription(), "full", "en");
        
        // Location
        Location location = c.newLocation();
        Address address = location.newAddress();
        Electronic electronic = address.newElectronic();
        electronic.setType("url");
        electronic.setValue(CSS_URL + "public/reports/view?id=" + story.getId());
        address.addElectronic(electronic);
        location.addAddress(address);
        c.addLocation(location);
        
        // Related objects
        c.addRelatedObject(createRelatedObject("isProducedBy", KEY_SERVICE_PREFIX + CSS_APP_SERVICE, c.newRelatedObject()));
        c.addRelatedObject(createRelatedObject("hasCollector", KEY_PARTY_PREFIX + story.getOwner().getUsername(), c.newRelatedObject()));
        //c.addRelatedObject(createRelatedObject("isManagedBy", KEY_PARTY_PREFIX + CSS_TEAM_PARTY, c.newRelatedObject()));
        c.addRelatedObject(createRelatedObject("isManagedBy", ERESEARCH_PARTY_KEY, c.newRelatedObject()));
        // TODO: LVL3 Link to an Activity record
        
        // Fields of Research
        c.addSubject("040104", "anzsrc-for", "en"); // Climate Change Processes
        c.addSubject("040105", "anzsrc-for", "en"); // Climatology (excl. Climate Change Processes)
        c.addSubject("040107", "anzsrc-for", "en"); // Meteorology
        c.addSubject("140205", "anzsrc-for", "en"); // Environment and Resource Economics
        c.addSubject("091599", "anzsrc-for", "en"); // Interdisciplinary Engineering not elsewhere classified
        // TODO: ask Alexei about potential other "non-climate" codes
        
        // Temporal coverage (1880 to 2070) and spatial coverage (based on the NRM region)
        Coverage coverage = c.newCoverage();
        Temporal temporalCoverage = coverage.newTemporal();
        temporalCoverage.addDate("1880-01-01T00:00:00Z", "dateFrom", "W3CDTF");
        temporalCoverage.addDate("2070-12-31T00:00:00Z", "dateTo", "W3CDTF");
        coverage.addTemporal(temporalCoverage);
        Spatial spatialCoverage = coverage.newSpatial();
        spatialCoverage.setType("kmlPolyCoords");
        spatialCoverage.setValue(story.getSeaport().getRegion().getCoordinates());
        coverage.addSpatial(spatialCoverage);
        c.addCoverage(coverage);
        
        // Rights
        Right right = c.newRight();
        right.setLicence("Non-Derivative licence", "http://creativecommons.org/licenses/by-nc-nd/3.0/", "CC-BY-NC-ND");
        right.setRightsStatement("Creative Commons Attribution Non-Commercial No-Derivatives (CC-BY-NC-ND 3.0)", "http://creativecommons.org/licenses/by-nc-nd/3.0/");
        c.addRight(right);
        
        // TODO: LVL3 Add citation info ?
        
        r.addCollection(c);
        return r;
	}
	
	public RelatedObject createRelatedObject(String type, String key, RelatedObject relatedObject) throws RIFCSException {
        relatedObject.setKey(key);
        Relation relation = relatedObject.newRelation();
        relation.setType(type);
        relatedObject.addRelation(relation);
        
        return relatedObject;
	}
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Impossible to retrieve the list of published stories";
	
	public static final String ERESEARCH_PARTY_KEY = "RMIT-AP35/1/2";
	public static final String RMIT_GROUP = "RMIT University";
	
	public static final String CSS_APP_SERVICE = "climatesmartseaports";
	public static final String CSS_TEAM_PARTY = "cssteam";
	
	public static final String KEY_COLLECTION_PREFIX = "RMIT-AP35/collection/";
	public static final String KEY_PARTY_PREFIX = "RMIT-AP35/party/";
	public static final String KEY_SERVICE_PREFIX = "RMIT-AP35/service/";
	
	public static final String CSS_URL = "http://seaports.eres.rmit.edu.au:8080/";
	public static final String HANDLE_PREFIX = "rmit:ap35/";
	
	
	
	public static final String DATE_LAST_MODIFIED = "2013-04-29T00:15:00+10:00";
}