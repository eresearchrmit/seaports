package war.controller;

import java.util.List;
import java.util.ArrayList;

import javax.validation.Valid;

import war.dao.*;
import war.model.* ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/createwb")
public class WorkBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private FilesDao filesDao ;
	@Autowired
	private WorkBoardDao workboardDao;
	@Autowired
	private PersonDao personDao ;
	private Person person ;
	private WorkBoard workboard ; 
	
	private List<Files> viewfiles;
//	private Files files ;


	@ModelAttribute("person")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getUserWorkBoard(@RequestParam(value="username",required=true) String firstName, Model model) {
		logger.info("Inside get Work Board !");
		ModelAndView mav = new ModelAndView();
		Files tmpfile, singlefile ;
//		System.out.println("Inside the Workboard Controller" + firstName);
		person = personDao.find(firstName) ;
//		System.out.println("Inside the Workboard Controller" + person.getLastName());
//		model.addAttribute("controllerMessage", "Correct Username and Password" + person.getLastName() );
		workboard = workboardDao.getActiveWorkBoard() ; 
		model.addAttribute("firstName",person.getFirstName()) ;
 		model.addAttribute("secondName",person.getLastName()) ;	
 		model.addAttribute("workboardTitle", workboard.getWorkBoardName()) ;
 		model.addAttribute("workboardID", workboard.getWorkBoardID());
 		viewfiles = new ArrayList<Files>() ;
 		List<Files> files = filesDao.getFiles(workboard) ;
 		
		for(int i = 0, n = files.size(); i < n; i++) {
	        singlefile = new Files() ;
	        tmpfile = new Files( );
			tmpfile = files.get(i) ;
			singlefile.setFileid(tmpfile.getFileid()) ;
			singlefile.setFilename(tmpfile.getFilename())  ;
			singlefile.setType(tmpfile.getType()) ; 
			singlefile.setWorkboard(tmpfile.getWorkboard()); 
			singlefile.setFile(tmpfile.getFile()) ;
			singlefile.setFilecontent(singlefile.toString((tmpfile.getFile()))) ;
			System.out.println("The Actual Value : " + tmpfile.getFile()) ;
			System.out.println("The String Value : " + singlefile.toString((tmpfile.getFile()))) ;	
			viewfiles.add(singlefile);
	    }
 		System.out.println("Inside the success query");
 		mav.addObject("workboard", workboard) ;
 		mav.addObject("files", viewfiles);
		mav.setViewName("activeWB");
		return mav;
	}
	
	
	@ModelAttribute("workboard")
	@RequestMapping(value ="/add",method = RequestMethod.GET)
	public ModelAndView CreateWorkBoard(@RequestParam(value="firstName",required=true) String firstName, Model model) {
		person = personDao.find(firstName) ;
		ModelAndView mav = new ModelAndView();
		WorkBoard workboard = new WorkBoard();
		workboard.setPerson(person) ;
		mav.setViewName("createwb");
 		mav.addObject("workboard", workboard);
		model.addAttribute("firstName",person.getFirstName()) ;
 		model.addAttribute("secondName",person.getLastName()) ;
		return mav;
	}
	
	
	@RequestMapping(value = "/add",method=RequestMethod.POST) 
	public ModelAndView addWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="firstName",required=true) String firstname, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		workboard.setPerson(person) ;
		workboardDao.save(workboard);
		ModelAndView mav = new ModelAndView();	
		model.addAttribute("workboardid",workboard.getWorkBoardID() );
		mav.addObject("workboard", workboard);
		mav.setViewName("createdWB") ;
		return mav ;		
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public ModelAndView updateWorkboard( @ModelAttribute("files") @Valid List<Files> files, 
			@RequestParam(value="workboardid",required=true) Integer workboardid, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		Files updatedfiles ;
		for(int i = 0, n = files.size(); i < n; i++) {
			updatedfiles = new Files() ;
			updatedfiles = files.get(i) ;
			System.out.println(updatedfiles.getFileid()) ;
	        System.out.println(updatedfiles.getFilecontent()) ;
		}
		workboard.setPerson(person) ;
		workboardDao.save(workboard);
		ModelAndView mav = new ModelAndView();	
		model.addAttribute("workboardid",workboard.getWorkBoardID() );
		mav.addObject("workboard", workboard);
		mav.setViewName("createdWB") ;
		return mav ;		
	}
	

	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public String deleteWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="workboardid",required=true) Integer Id, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		workboard = workboardDao.find(Id) ;
		person = workboard.getPerson() ;
		workboardDao.removeWorkBoard(Id) ;
		ModelAndView mav = new ModelAndView();
		WorkBoard wb = new WorkBoard();
		wb.setPerson(person) ;
 		mav.addObject("workboard", wb);
		model.addAttribute("firstName",person.getFirstName()) ;
		return "redirect:add" ;	
	}
	

/*	
 * @RequestMapping(method=RequestMethod.GET,value="list")
	public ModelAndView showPeopleView(@ModelAttribute Person person) {
		logger.debug("Received request to list persons");
		ModelAndView mav = new ModelAndView();
		String people = personDao.find() ;
		logger.debug("Person Listing count = "+people.size());
		mav.addObject("people",people);
		mav.setViewName("list");
		return mav;
	}
	*
	*/

}