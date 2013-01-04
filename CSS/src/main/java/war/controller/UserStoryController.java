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
@RequestMapping("/createus")
public class UserStoryController {
		
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	private FilesDao filesDao ;
	@Autowired
	private WorkBoardDao workboardDao;
	@Autowired
	private PersonDao personDao ;
	private Person person ;
	private WorkBoard workboard ;
	
	private WorkBoardController cworkboard ;
	private Files file ;
	private String splitvar = "\\." ;
	private String [] tokens;

	@ModelAttribute("workboard")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserWorkBoard(@RequestParam(value="workboardid",required=true) Integer Id, Model model) {
		logger.info("Inside get Workboard !");

		cworkboard = new WorkBoardController();
		workboard = workboardDao.find(Id);
		person = personDao.find(workboard.getPerson().getLogin());
		workboard = workboardDao.getActiveWorkBoard(person);
		if (workboard == null) {
			ModelAndView mav = cworkboard.CreateWorkBoard(person.getLogin(), model);
			return mav;	
		}
		else {
			workboard = workboardDao.find(Id); 
		}
		//workboard.setMode("passive");
		workboardDao.save(workboard);
		
		ModelAndView mav = modelForPassiveWBview(model, workboard);
		return mav;	
	}
	
	private ModelAndView modelForPassiveWBview(Model model, WorkBoard workboard) {
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
 		model.addAttribute("workboardTitle", workboard.getWorkBoardName()) ;
 		model.addAttribute("workboardID", workboard.getWorkBoardID());
 		mav.addObject("stringfiles",stringfiles) ;
		mav.setViewName("passiveWB");
		return mav;
	}
	
	
	
	
	
}