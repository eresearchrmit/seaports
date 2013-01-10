package war.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import war.dao.*;
import war.model.*;
import war.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;

import org.apache.commons.codec.binary.* ;
import org.springframework.web.servlet.tags.* ; 
import org.apache.commons.lang.* ; 


@Controller
@RequestMapping("/userstory")
public class UserStoryController {
		
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	private FilesDao filesDao;
	@Autowired
	private DataElementDao dataelementDao;
	@Autowired
	private UserStoryDao userstoryDao;
	@Autowired
	private WorkBoardDao workboardDao;
	@Autowired
	private PersonDao personDao;
	
	private Person person;
	private WorkBoard workboard;
	private UserStory userstory;
	
	private WorkBoardController cworkboard;
	private Files file;

	@ModelAttribute("workboard")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserWorkBoard(@RequestParam(value="workboardid",required=true) Integer Id, Model model) {
		logger.info("Inside get Workboard !");

		cworkboard = new WorkBoardController();
		workboard = workboardDao.find(Id);
		person = workboard.getPerson();

		workboard.setMode("passive");
		workboardDao.save(workboard);
		
		userstory = userstoryDao.getPrivateUserStory(workboard);
		if (userstory == null) {
			userstory = new UserStory();
			userstory.setUserstoryname(workboard.getWorkBoardName());
			userstory.setAccess("private");
			userstory.setWorkboard(workboard);
			userstoryDao.save(userstory);
			addDefaultDataElements(userstory);
		}
		
		ModelAndView mav = modelForPassiveWBview(model, workboard, userstory);
		return mav;	
	}
	
	public void addDefaultDataElements(UserStory userstory) {
		
		DataElement dataelement ;
		String header  = "Type the Title of the User Story", 
		       content = "Type the Content of the User Story";
		
		dataelement = new DataElement() ;		
		dataelement.setDataelement(header.getBytes()) ;
		dataelement.setDataelementname("Header") ;
		dataelement.setExtension("txt") ;
		dataelement.setFormat("ONE") ;
		dataelement.setUserstory(userstory) ;
		dataelementDao.save(dataelement) ;
		
		dataelement = new DataElement() ;		
		dataelement.setDataelement(content.getBytes()) ;
		dataelement.setDataelementname("Content") ;
		dataelement.setExtension("txt") ;
		dataelement.setFormat("FOUR") ;
		dataelement.setUserstory(userstory) ;
		dataelementDao.save(dataelement) ;
	}
	
	@RequestMapping(value="/addfile", method=RequestMethod.GET) 
	public ModelAndView addDataelement(@ModelAttribute DataElement dataelement,@RequestParam(value="fileid",required=true) Integer fileid, 
			@RequestParam(value="workboardid",required=true) Integer workboardid, Model model) {
		
		workboard = workboardDao.find(workboardid) ;
		userstory = userstoryDao.getPrivateUserStory(workboard) ;
		file = filesDao.find(fileid) ;
		dataelement = new DataElement() ;
		
		dataelement.setDataelement(file.getFile()) ;
		dataelement.setDataelementname(file.getFilename()) ;
		dataelement.setExtension(file.getType()) ;
		dataelement.setUserstory(userstory) ;
		
		String format = null ; 
		List<DataElement> dataelements = new ArrayList<DataElement>() ;
 		try {
 			
 			boolean TWO = false, THREE = false, FIVE = false, NONE = false ; 

			dataelements = dataelementDao.getDataElements(userstory) ;
			for (int i = 0, n = dataelements.size(); i < n; i++) {	
				if (dataelements.get(i).getFormat().equalsIgnoreCase("TWO")) TWO = true ;
				if (dataelements.get(i).getFormat().equalsIgnoreCase("THREE")) THREE = true ;
				if (dataelements.get(i).getFormat().equalsIgnoreCase("FIVE")) FIVE = true ;
			}
			System.out.println("Inside the " + TWO + " " + THREE + " " + FIVE);
			
			format = TWO ? (THREE ? (FIVE ? "NONE" : "FIVE") : "THREE") : (THREE ? "TWO" : "TWO")  ;
			System.out.println("The value in the data is : " + format);

 		} catch (NullPointerException e) {
				System.out.println ("No dataelement found") ;
		}
 		
 		dataelement.setFormat(format) ;
 		if (!format.equalsIgnoreCase("NONE")) dataelementDao.save(dataelement) ;
		
		ModelAndView mav = modelForPassiveWBview(model, workboard, userstory);
		return mav;	
	}
	
	private ModelAndView modelForPassiveWBview(Model model, WorkBoard workboard, UserStory userstory) {
		logger.debug("Inside modelForPassiveWBview");

		ModelAndView mav = new ModelAndView();
		mav.addObject("workboard", workboard);
		List<Files> dataElements = new ArrayList<Files>();
		
		try {
			person = workboard.getPerson();
			model.addAttribute("user", person) ;
			
			List<Files> files = filesDao.getFiles(workboard);
	 		Files tmpFile;
	 		for (Files file : files) {
	 			tmpFile = file;
	 			if (file.getType() == "jpg" || file.getType() == "jpeg" )
	 				tmpFile.setFilecontent(Base64.encodeBase64String(file.getFile()));
				else
					tmpFile.setFilecontent(file.generateFileContent((file.getFile())));
	 			dataElements.add(tmpFile);
			}
	 		file = new Files();
	 		mav.addObject(file);  // This file object id for the userwbmenu.jsp
	 		mav.addObject("dataelements", dataElements);
	 		
	 		
			/* Operation to get the dataelements and to direct activeUS.jsp View */
		 	List<DataElement> commentsDataElement = new ArrayList<DataElement>() ;
	 		try {
				// Converting the bytefiles to stringfiles
				List<DataElement> dataelements = dataelementDao.getDataElements(userstory);
				DataElement stringdataelement;
				for (DataElement dataelement : dataelements) {
					stringdataelement = dataelement;
					if (dataelement.getExtension() == "jpg" || dataelement.getExtension() == "jpeg" )
						stringdataelement.setDataelementcontent(Base64.encodeBase64String(dataelement.getDataelement())); 
					else
						stringdataelement.setDataelementcontent(dataelement.toString((dataelement.getDataelement())));
					commentsDataElement.add(stringdataelement);
				}
			}
	 		catch (NullPointerException e) {
	 			commentsDataElement.add(null) ;
			}
			mav.addObject("userstory", userstory);
	 		mav.addObject("commentsDataElement", commentsDataElement);
	 		
 			mav.setViewName("passiveWB");
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	public ModelAndView getUserStoriesList(@RequestParam(value="user",required=true) String login, Model model) {
		logger.info("Inside getUserStoriesList !");
		try {
			person = personDao.find(login) ;
			List<WorkBoard> wbList = workboardDao.getUserStoriesList(person); 
			ModelAndView mav = new ModelAndView();
			mav.addObject("user", person);
			mav.addObject("wbList", wbList);
	 		model.addAttribute("listingTitle", "My User Stories");
	 		mav.setViewName("listUS");
			return mav;
		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_USERSTORY_LIST);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return null;
	}
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Error: Impossible to retrieve the list of your stories";
	
}