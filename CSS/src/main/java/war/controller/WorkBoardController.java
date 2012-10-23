package war.controller;

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
	private WorkBoardDao workboardDao;
	@Autowired
	private PersonDao personDao ;
	private Person person ;

	

	@ModelAttribute("workboard")
	@RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
	public String Uploadfile(Model model) {
		logger.info("Inside File upload !");
		model.addAttribute("controllerMessage", "Please Login to Create Workboard");
		return "uploadfile";
	}
	
	
	@ModelAttribute("workboard")
	@RequestMapping(value ="/add",method = RequestMethod.GET)
	public ModelAndView CreateWorkBoard(@RequestParam(value="firstName",required=true) String firstName, Model model) {
		System.out.println("Inside CreateWorkBoard" + firstName) ;
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
	public ModelAndView saveWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="firstName",required=true) String firstname, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		String username = person.getFirstName() ;
		workboard.setPerson(person) ;
		System.out.println("Inside saveWorkboard 1 : " + workboard.getPerson() + username) ;	
		System.out.println("Inside saveWorkboard 2 : " + workboard.getWorkBoardName() ) ;
		workboardDao.save(workboard);
		ModelAndView mav = new ModelAndView();	
		System.out.println("Inside saveWorkboard 3 : " + workboard.getWorkBoardID() ) ;
		model.addAttribute("workboardid",workboard.getWorkBoardID() );
		mav.addObject("workboard", workboard);
		mav.setViewName("createdWB") ;
		return mav ;
		
	}
	

	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public String deleteWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="workboardid",required=true) Integer Id, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		System.out.println("Inside saveWorkboard 4 Id : " + Id ) ;
		workboard = workboardDao.find(Id) ;
		System.out.println("Inside saveWorkboard 5 workboard : " + workboard ) ;
		person = workboard.getPerson() ;
		System.out.println("Inside saveWorkboard 8.1 workboard : " + Id ) ;
		workboardDao.removeWorkBoard(Id) ;
		System.out.println("Inside saveWorkboard 9 workboard : " + Id ) ;
		ModelAndView mav = new ModelAndView();
		WorkBoard wb = new WorkBoard();
		System.out.println("Inside saveWorkboard 10 workboard : " + wb ) ;
		wb.setPerson(person) ;
//		mav.setViewName("createwb");
 		mav.addObject("workboard", wb);
		model.addAttribute("firstName",person.getFirstName()) ;
// 		model.addAttribute("secondName",person.getLastName()) ;
		return "redirect:add" ;
//		return mav;		
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