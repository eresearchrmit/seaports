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
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;
	
	@ModelAttribute("user")
	@RequestMapping(method = RequestMethod.GET)
	public User getUser() {
		return new User(); 
	}

	@RequestMapping(method=RequestMethod.POST)
	public String loginValidation(@ModelAttribute User user, Model model) {
		logger.debug("Received postback on user " + user);
		
		User userdb = null;
		
		try {
			if (user.getLogin() != null && user.getPassword() != null) {	
					userdb = userDao.find(user.getLogin());
					
					// Check password
					if (userdb.getPassword().equalsIgnoreCase(user.getPassword())) {
						//TODO check whether the user have an active Work Board
						//TODO If yes print the Work Board Name and the elements in that WorkBoard
						
			 	 		return "redirect:/spring/workboard?user=" + userdb.getLogin();
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
	
	public static final String ERR_BAD_LOGIN_PASSWORD = "Invalid login and/or password";
	public static final String ERR_MISSING_LOGIN_PASSWORD = "Please enter a login and/or a password";
}
