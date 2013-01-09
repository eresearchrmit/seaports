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
		FilesService stringfiles;
		Files swapfile;
		
		System.out.println("Inside the refractor" + person +" "+ personDao + " " + workboard);
		
		
		/* ** CRUD operation for the file and to redirect activeWB.jsp View** */
		ModelAndView mav = new ModelAndView();
		person = personDao.find(workboard.getPerson().getLogin()) ;
		
 		
 		List<Files> convertedfiles = new ArrayList<Files>() ;
 		
 		try {
			/////// Converting the bytefiles to stringfiles     ///////
			List<Files> files = filesDao.getFiles(workboard);
			Files stringfile;
			for (int i = 0, n = files.size(); i < n; i++) {
				stringfile = new Files();
				swapfile = new Files();
				swapfile = files.get(i);
				stringfile.setFileid(swapfile.getFileid());
				stringfile.setFilename(swapfile.getFilename());
				stringfile.setType(swapfile.getType());
				stringfile.setWorkboard(swapfile.getWorkboard());
				stringfile.setFile(swapfile.getFile());
				if (swapfile.getType() == "jpg" || swapfile.getType() == "jpeg" ) {
					System.out.println("Inside upload " + swapfile.getFile()) ;
					stringfile.setFilecontent(Base64.encodeBase64String(swapfile.getFile())) ; 
				}else {
					stringfile.setFilecontent(swapfile.toString((swapfile.getFile())));
				}
				convertedfiles.add(stringfile);
			}
		} catch (NullPointerException e) {
				convertedfiles.add(null) ;
		}
		stringfiles = new FilesService() ;
 		stringfiles.setFiles(convertedfiles) ;
 		
 		mav.addObject("workboard", workboard) ;
 		file = new Files() ;
 		mav.addObject(file) ;  // This file object id for the userwbmenu.jsp
		model.addAttribute("firstname",person.getFirstname()) ;
		model.addAttribute("secondname",person.getLastname()) ;	
		model.addAttribute("user", person) ;	
 		model.addAttribute("workboardTitle", workboard.getWorkBoardName()) ;
 		model.addAttribute("workboardID", workboard.getWorkBoardID());
 		mav.addObject("stringfiles",stringfiles) ;
 		
 		// This file object id for the userwbmenu.jsp 
 		file = new Files() ;
 		mav.addObject(file) ;  
		model.addAttribute("firstname",person.getFirstname()) ;
 		model.addAttribute("secondname",person.getLastname()) ;	
 		
		/* ** Operation to get the dataelements and to direct activeUS.jsp View** */
		DataElementService stringdataelements ;
		DataElement swapdataelement;
		
		System.out.println("Inside the userstory " + person +" "+ personDao + " " + userstory);

	 	List<DataElement> converteddataelement = new ArrayList<DataElement>() ;
 		
 		try {
			/////// Converting the bytefiles to stringfiles     ///////
			List<DataElement> dataelements = dataelementDao.getDataElements(userstory) ;
			DataElement stringdataelement;
			for (int i = 0, n = dataelements.size(); i < n; i++) {
				stringdataelement = new DataElement();
				swapdataelement = new DataElement();
				swapdataelement = dataelements.get(i);

				stringdataelement.setDataelementid(swapdataelement.getDataelementid());
				stringdataelement.setDataelement(swapdataelement.getDataelement()) ;
				stringdataelement.setDataelementname(swapdataelement.getDataelementname()) ;
				stringdataelement.setFormat(swapdataelement.getFormat()) ;   
				stringdataelement.setUserstory(swapdataelement.getUserstory()) ;

				stringdataelement.setExtension(swapdataelement.getExtension()) ;
				if (swapdataelement.getExtension() == "jpg" || swapdataelement.getExtension() == "jpeg" ) {
					stringdataelement.setDataelementcontent(Base64.encodeBase64String(swapdataelement.getDataelement())) ; 
				}else {
					stringdataelement.setDataelementcontent(swapdataelement.toString((swapdataelement.getDataelement()))) ;
				}
				converteddataelement.add(stringdataelement);
			}
		} catch (NullPointerException e) {
				converteddataelement.add(null) ;
		}
		
		stringdataelements = new DataElementService() ;
 		stringdataelements.setDataElements(converteddataelement) ;
 		
		mav.addObject("userstory", userstory) ; 
 		model.addAttribute("userstoryName", userstory.getUserstoryname()) ;
 		model.addAttribute("userstoryID", userstory.getUserstoryid());
 		System.out.println("The object value of the dataelement : " + stringdataelements) ;
 		mav.addObject("stringdataelements",stringdataelements) ;	
 		
		//mav.setViewName("activeUS");
		mav.setViewName("passiveWB");
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
			model.addAttribute("errorMessage", "Error: Impossible to retrieve the list of your stories");
		}
		
		return null;
	}
}