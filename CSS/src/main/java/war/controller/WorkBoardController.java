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
	
/*	
 * @RequestMapping(method=RequestMethod.GET,value="edit")
	public ModelAndView editPerson(@RequestParam(value="id",required=false) Long id) {		
		logger.debug("Received request to edit person id : "+id);				
		ModelAndView mav = new ModelAndView();		
 		mav.setViewName("edit");
 		Person person = null;
 		if (id == null) {
 			person = new Person();
 		} else {
 			person = personDao.find(id);
 		}	
 		mav.addObject("person", person);
		return mav;	
	}
	*/
	
//	@ModelAttribute("workboard")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView CreateWorkBoard(@RequestParam(value="firstName",required=true) String firstName, Model model) {
		person = personDao.find(firstName) ;
		ModelAndView mav = new ModelAndView();
		WorkBoard workboard = new WorkBoard();
		workboard.setPerson(person) ;
		System.out.println(workboard.getWorkBoardID()) ;
		mav.setViewName("createwb");
 		mav.addObject("workboard", workboard);
		model.addAttribute("firstName",person.getFirstName()) ;
 		model.addAttribute("secondName",person.getLastName()) ;
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST) 
	public ModelAndView saveWorkboard(@ModelAttribute WorkBoard workboard) {
		logger.debug("Received object for workboard  "+ workboard);
		System.out.println(workboard.getPerson()) ;
//		person = personDao.find(firstName);
		workboard.setPerson(person) ;
		
		workboardDao.save(workboard);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("createdWB") ;
		mav.addObject("workboard", workboard);
		return mav ;
		
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