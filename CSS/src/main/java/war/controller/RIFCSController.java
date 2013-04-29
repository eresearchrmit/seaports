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
	        mw.validate();
	        
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
        namepart.setValue("AP-35 Climate Smart Seaports");
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
        
        // Description
        service.addDescription("Climate Smart Seaports is an online decision support toolkit designed to help Australian seaports adapating to climate change and improving their resilience to it. The toolkit lets access data from various datasets such as CSIRO, BoM, ABS, BITRE as well as their own personal data. Climate Smart Seaports then allows writing and publishing reports based on this data and the user analysis.", "full", "en");
        
        // TODO: set the Access policy URL
        
        r.addService(service);
        return r;
	}
	
	public RegistryObject createRMITPartyRIFCS() throws RIFCSException {
		RegistryObject r = rifcs.newRegistryObject();
        r.setKey(KEY_PARTY_PREFIX + CSS_TEAM_PARTY);
        r.setGroup(RMIT_GROUP);
        r.setOriginatingSource(CSS_URL);
        
        Party rmit = r.newParty();
        rmit.setType("group");
        rmit.setDateModified(DATE_LAST_MODIFIED);
        
        // TODO: set electronic address with details about the group
        
        // Manages "Climate Smart Seaports" Service
        rmit.addRelatedObject(createRelatedObject("manages", KEY_SERVICE_PREFIX + "climatesmartseaport", rmit.newRelatedObject()));
        
        // TODO: isAdministeredBy "RMIT-AP35/1/2" (= e-Research Office).
        
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
        	id.setType("url");
        	id.setValue(KEY_PARTY_PREFIX + user.getUsername());
        }
        p.addIdentifier(id);
        
        // Location
        Location loc = p.newLocation();
        Address address = loc.newAddress();
        Electronic electronic = address.newElectronic();
        electronic.setType("url");
        electronic.setValue(KEY_PARTY_PREFIX + user.getUsername());
        address.addElectronic(electronic);
        loc.addAddress(address);
        p.addLocation(loc);
        
        // TODO: set subject (anzsrc-for) ?
        // TODO: set description, type "brief"
        
        r.addParty(p);
        return r;
	}
	
	public RegistryObject createCollectionRIFCS(UserStory story) throws RIFCSException {
		
		// Start by counting how many 
		
		RegistryObject r = rifcs.newRegistryObject();
        r.setKey(KEY_COLLECTION_PREFIX + "story" + story.getId());
        r.setGroup(RMIT_GROUP);
        r.setOriginatingSource(CSS_URL + "/public/reports/view?id=" + story.getId());
        
		Collection c = r.newCollection();
		
		c.setType("collection");
        c.addIdentifier(HANDLE_PREFIX + "story" + story.getId(), "handle");
        
        // Name
        Name name = c.newName();
        name.setType("primary");
        name.addNamePart(story.getName(), "");
        c.addName(name);
        
        //TODO: set description (auto-generated)
        c.addDescription("", "full", "en");
        c.addDescription("Report about climate change in the " + story.getSeaport().getRegion().getName() + " NRM region of Australia, focused on " + story.getSeaport().getName(), "brief", "en");
        
        // Location
        Location location = c.newLocation();
        Address address = location.newAddress();
        Electronic electronic = address.newElectronic();
        electronic.setType("");
        electronic.setValue(CSS_URL + "/public/reports/view?id=" + story.getId());
        address.addElectronic(electronic);
        location.addAddress(address);
        c.addLocation(location);
        
        // Related objects
        c.addRelatedObject(createRelatedObject("isProducedBy", KEY_SERVICE_PREFIX + CSS_APP_SERVICE, c.newRelatedObject()));
        c.addRelatedObject(createRelatedObject("hasCollector", KEY_PARTY_PREFIX + story.getOwner().getUsername(), c.newRelatedObject()));
        c.addRelatedObject(createRelatedObject("isManagedBy", KEY_PARTY_PREFIX + CSS_TEAM_PARTY, c.newRelatedObject()));
        
        // Fields of Research
        c.addSubject("040104", "anzsrc-for", "en"); // Climate Change Processes
        c.addSubject("040105", "anzsrc-for", "en"); // Climatology (excl. Climate Change Processes)
        c.addSubject("040107", "anzsrc-for", "en"); // Meteorology
        c.addSubject("140205", "anzsrc-for", "en"); // Environment and Resource Economics
        // TODO: ask Alexei about potential other "non-climate" codes
        c.addSubject("091599", "anzsrc-for", "en"); // Interdisciplinary Engineering not elsewhere classified
        
        // Temporal coverage (1880 to 2070) and spatial coverage (based on the NRM region)
        Coverage coverage = c.newCoverage();
        Temporal temporalCoverage = coverage.newTemporal();
        temporalCoverage.addDate("1880-01-01T00:00:00Z", "dateFrom", "W3CDTF");
        temporalCoverage.addDate("2070-12-31T00:00:00Z", "dateTo", "W3CDTF");
        coverage.addTemporal(temporalCoverage);
        Spatial spatialCoverage = coverage.newSpatial();
        spatialCoverage.setType("kmlPolyCoord");
        spatialCoverage.setType(story.getSeaport().getRegion().getCoordinates());
        coverage.addSpatial(spatialCoverage);
        c.addCoverage(coverage);
                
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
	
	@RequestMapping(value="public/rifcs-example", method = RequestMethod.GET)
	public void getRifcsXMLExample(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			RIFCSWrapper mw = new RIFCSWrapper();
	        rifcs = mw.getRIFCSObject();
	        RegistryObject r = rifcs.newRegistryObject();
	        r.setKey("collection1");
	        r.setGroup("ANDS");
	        r.setOriginatingSource("http://myrepository.au.edu");
	        Collection c = r.newCollection();
	        c.setType("collection");
	        c.addIdentifier("hdl:7651/myhandlesuffix", "handle");
	        Right right= c.newRight();
	        right.setAccessRights("Access Right Value", "Access Rights Uri", "Access Right Type");
	        right.setLicence("Licence Value", "Licence Uri", "Licence Type");
	        right.setRightsStatement("Right Statement Value", "Right Statement Uri");
	        c.addRight(right);
	        right= c.newRight();
	        right.setAccessRights("Access Right Value2", "Access Rights Uri2", "Access Right Type2");
	        right.setLicence("Licence Value2", "Licence Uri2", "Licence Type2");
	        right.setRightsStatement("Right Statement Value2", "Right Statement Uri2");
	        c.addRight(right);      
	        Name n = c.newName();
	        n.setType("primary");
	        NamePart np = n.newNamePart();
	        np.setValue("Sample Collection");
	        n.addNamePart(np);
	        c.addName(n);
	        Location l = c.newLocation();
	        Address a = l.newAddress();
	        Electronic e = a.newElectronic();
	        e.setValue("http://myrepository.au.edu/collections/collection1");
	        e.setType("url");
	        a.addElectronic(e);
	        l.addAddress(a);
	        c.addLocation(l);
	        RelatedObject ro = c.newRelatedObject();
	        ro.setKey("activity1");
	        ro.addRelation("isOutputOf", null, null, null);
	        c.addRelatedObject(ro);
	        RelatedObject ro2 = c.newRelatedObject();
	        ro2.setKey("party1");
	        ro2.addRelation("isOwnerOf", null, null, null);
	        c.addRelatedObject(ro2);
	        RelatedObject ro3 = c.newRelatedObject();
	        ro3.setKey("service1");
	        ro3.addRelation("supports", null, null, null);
	        c.addRelatedObject(ro3);
	        c.addSubject("subject1", "local", null);
	        c.addSubject("subject2", "local", null);
	        Coverage cov = c.newCoverage();
	        Spatial sp = cov.newSpatial();
	        Temporal tmp = cov.newTemporal();
	        tmp.addDate("1999-3-4", "dateFrom", "W3C");
	        tmp.addDate("1999-3-4", "dateFrom", "W3C");
	        sp.setValue("126.773437,-23.598894 127.652343,-27.405585 131.519531,-27.093039 131.167968,-24.081241 130.464843,-20.503868 127.828124,-19.843884 123.960937,-20.339134 123.433593,-22.141282 123.433593,-25.040485 123.785156,-28.183080 126.773437,-23.598894");
	        sp.setType("kmlPolyCoords");
	        cov.addSpatial(sp);
	        cov.addTemporal(tmp);
	        c.addCoverage(cov);
	        c.addDescription("This is a sample description", "brief", null);
	        RelatedInfo ri = c.newRelatedInfo();
	        ri.setIdentifier("http://external-server.edu/related-page.htm", "uri");
	        ri.setTitle("A related information resource");
	        ri.setNotes("Notes about the related information resource");
	        c.addRelatedInfo(ri);
	        CitationInfo ci = c.newCitationInfo();
	        ci.setCitation("sasdgasdgsdgasdgasdgasdgasdgasdgsadgasdgasdgsg", "howardsss");
	        c.addCitationInfo(ci);
	        CitationInfo ci2 = c.newCitationInfo();
	        CitationMetadata cim = ci2.newCitationMetadata();
	        cim.setIdentifier("sdjhksdghkashdgkjashgd", "pod");
	        Contributor cCont = cim.newContributor();
	        cCont.setSeq(0);
	        cCont.addNamePart("Monus", "surname");
	        cCont.addNamePart("Leo", "sgiven");
	        cim.addContributor(cCont);
	        cim.setTitle("ashgfjhsagfjashgf");
	        cim.setEdition("editionksjadhkjsahf");
	        cim.setPlacePublished("sjdhgkjahsdgkahgkahsdkghaksdghkajhg");
	        cim.setURL("sdjhgksjhgdk");
	        cim.setContext("shdgjsgjasgjahdsgjsd");
	        c.addCitationInfo(ci2);
	        ci2.addCitationMetadata(cim);
	        c.addDescription("description", "full", "eng");
	        r.addCollection(c);
	        rifcs.addRegistryObject(r);
	        //mw.write(System.out);
	        mw.validate();
	        
	        System.out.println(mw.toString());
	        response.getWriter().write(mw.toString());
	        
	        //model.addAttribute("content", mw.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Impossible to retrieve the list of published stories";
	
	public static final String RMIT_GROUP = "RMIT University";
	public static final String CSS_APP_SERVICE = "climatesmartseaports";
	public static final String CSS_TEAM_PARTY = "cssteam";
	
	//public static final String KEY_PREFIX = "au.edu.rmit.ands.ap35";
	public static final String KEY_COLLECTION_PREFIX = "http://www.rmit.edu.au/ap35/collection/";
	public static final String KEY_PARTY_PREFIX = "http://www.rmit.edu.au/ap35/party/";
	public static final String KEY_SERVICE_PREFIX = "http://www.rmit.edu.au/ap35/service/";
	public static final String CSS_URL = "http://www.climatesmartseaports.rmit.edu.au";
	public static final String HANDLE_PREFIX = "rmit:ap35/";
	
	public static final String DATE_LAST_MODIFIED = "2013-04-29T00:15:00+10:00";
}