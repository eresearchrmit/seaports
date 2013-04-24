package war.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ands.rifcs.base.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class XMLController {
 
	private static RIFCS rifcs = null;
	
	@RequestMapping(value="public/rifcs", method = RequestMethod.GET)
	public void getRifcsXML(HttpServletRequest request, HttpServletResponse response) {
		
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
}