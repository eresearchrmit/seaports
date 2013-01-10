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
//import org.springframework.web.servlet.ModelAndView;

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
	public String loginValidation(@ModelAttribute Person person, Model model) {
		logger.debug("Received postback on person " + person);
		Person persondb = null;
		
		try {
			if (person.getLogin() != null && person.getPassword() != null) {	
					persondb = personDao.find(person.getLogin());
					
					// Check password
					if (persondb.getPassword().equalsIgnoreCase(person.getPassword())) {
						//TODO check whether the user have an active Work Board
						//TODO If yes print the Work Board Name and the elements in that WorkBoard
			 	 		String request = "redirect:/spring/workboard?user=" + persondb.getLogin();
			 	 		return request;
					}
					else {
						model.addAttribute("controllerMessage", ERR_BAD_LOGIN_PASSWORD);
						return "login";
					}
			}
			else {
				model.addAttribute("controllerMessage", ERR_MISSING_LOGIN_PASSWORD);
				return "login";
			}	
		}
		catch (NullPointerException e) {
			model.addAttribute("controllerMessage", ERR_MISSING_LOGIN_PASSWORD);
			return "login";
		}
		catch (Exception e) {
			model.addAttribute("controllerMessage", e.getMessage());
			return "login";
		}
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
	public static final String ERR_BAD_LOGIN_PASSWORD = "Invalid login and/or password";
	public static final String ERR_MISSING_LOGIN_PASSWORD = "Please enter a login and/or a password";
}
