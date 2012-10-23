package war.controller;

//import java.util.List;

import war.dao.*;
import war.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/login")

public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonDao personDao;
	
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
	@ModelAttribute("person")
	@RequestMapping(method=RequestMethod.GET) 
	public Person loginPerson() {
		return new Person(); 
	}
	
/*	
	@RequestMapping(method=RequestMethod.GET) 
	public String HandleLoginPerson(){
		return "login" ;
	}
*/
	
	@RequestMapping(method=RequestMethod.POST) 
	public ModelAndView loginValidation(@ModelAttribute Person person, Model model) {
		logger.debug("Received postback on person "+ person);
		Person persondb = null ;
		ModelAndView mav = new ModelAndView();
		
		if (person.getFirstName() != null) {	
				persondb = personDao.find(person.getFirstName()) ;
				Boolean val = persondb.getPassWord().equalsIgnoreCase(person.getPassWord()) ;
				if (val) {	
		 			mav.setViewName("list") ;
		 	 		mav.addObject("person", persondb);
				} else {

					System.out.println (val) ;
					System.out.println (persondb.getPassWord()) ;
					System.out.println (person.getPassWord()) ;
					model.addAttribute("controllerMessage","Please enter correct" + "</br>" + "username and password");
					mav.setViewName("login") ;	
				}
		}	
		return mav ;
	}	
	
/*	@RequestMapping(method=RequestMethod.GET,value="list")
	public ModelAndView showPeopleView(@ModelAttribute Person person) {
		logger.debug("Received request to list persons");
		ModelAndView mav = new ModelAndView();
		String people = personDao.find() ;
		logger.debug("Person Listing count = "+people.size());
		mav.addObject("people",people);
		mav.setViewName("list");
		return mav;
	}*/

}
