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
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;

	@ModelAttribute("user")
	@RequestMapping(value = {"/login", "/register"}, method = RequestMethod.GET)
	public User getUser() {
		return new User(); 
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerNewUser(@ModelAttribute User user, Model model) {
		logger.debug("Inside registerNewUser");
		
		try {
			if (user.getLogin() == null || user.getLogin().length() < 1)
				throw(new Exception("Invalid login"));
			if (user.getPassword() == null || user.getPassword().length() < 5)
				throw(new Exception("Invalid password"));
			if (user.getFirstname() == null || user.getFirstname().length() < 1)
				throw(new Exception("Invalid first name"));
			if (user.getLastname() == null || user.getLastname().length() < 1)
				throw(new Exception("Invalid last name"));
			
			/*User userdb = */userDao.save(user);
			return "login";
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "register";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginValidation(@ModelAttribute User user, Model model) {
		logger.debug("Inside loginValidation");
		
		User userdb = null;
		try {
			if (user.getLogin() != null && user.getPassword() != null) {	
				userdb = userDao.find(user.getLogin());
					
				// Check password
				if (userdb.getPassword().equalsIgnoreCase(user.getPassword())) {						
					return "redirect:/spring/workboard?user=" + userdb.getLogin();
				}
				else {
					model.addAttribute("errorMessage", ERR_BAD_LOGIN_PASSWORD);
					return "login";
				}
			}
			else {
				model.addAttribute("errorMessage", ERR_MISSING_LOGIN_PASSWORD);
				return "login";
			}	
		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_MISSING_LOGIN_PASSWORD);
			return "login";
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "login";
		}
	}
	
	public static final String ERR_SIGNUP = "The application wasn't able to create your account. Please try again later";
	public static final String ERR_BAD_LOGIN_PASSWORD = "Invalid login and/or password";
	public static final String ERR_MISSING_LOGIN_PASSWORD = "Please enter a login and/or a password";
}
