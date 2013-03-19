package war.controller;

import security.UserLoginService;
import war.dao.*;
import war.model.*;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginFailed(Model model) {
		logger.debug("Inside loginFailed");
		model.addAttribute("error", true);
		return "login";
	}
	
	@RequestMapping(value = "/accessDenied")
    public String accessDenied() {
          return "accessDenied";
     }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerNewUser(@ModelAttribute User user, Model model) {
		logger.debug("Inside registerNewUser");
		
		try {
			if (user.getUsername() == null || user.getUsername().isEmpty())
				throw(new Exception(ERR_SIGNUP_INVALID_LOGIN));
			if (user.getPassword() == null || user.getPassword().length() < 5)
				throw(new Exception(ERR_SIGNUP_INVALID_PASSWORD));
			if (user.getFirstname() == null || user.getFirstname().isEmpty())
				throw(new Exception(ERR_SIGNUP_INVALID_FIRSTNAME));
			if (user.getLastname() == null || user.getLastname().isEmpty())
				throw(new Exception(ERR_SIGNUP_INVALID_LASTNAME));
			if (user.getEmail() == null || user.getEmail().isEmpty() || !(EmailValidator.getInstance().isValid(user.getEmail())))
				throw(new Exception(ERR_SIGNUP_INVALID_EMAIL));
			
			user.setRoles(UserLoginService.ROLE_USER);
			user.setNonLocked(true);
			user.setEnabled(true);
			
			userDao.save(user);
			return "login";
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "register";
		}
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginValidation(@ModelAttribute User user, Model model) {
		logger.debug("Inside loginValidation");
		
		User userdb = null;
		try {
			if (user.getUsername() != null && user.getPassword() != null) {	
				userdb = userDao.find(user.getUsername());
					
				// Check password
				if (userdb.getPassword().equalsIgnoreCase(user.getPassword())) {						
					return "redirect:/auth/workboard?user=" + userdb.getUsername();
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
	public static final String ERR_SIGNUP_INVALID_LOGIN = "Invalid login";
	public static final String ERR_SIGNUP_INVALID_PASSWORD = "Invalid password";
	public static final String ERR_SIGNUP_INVALID_FIRSTNAME = "Invalid first name";
	public static final String ERR_SIGNUP_INVALID_LASTNAME = "Invalid last name";
	public static final String ERR_SIGNUP_INVALID_EMAIL = "Invalid e-mail address";
	
	public static final String ERR_BAD_LOGIN_PASSWORD = "Invalid login and/or password";
	public static final String ERR_MISSING_LOGIN_PASSWORD = "Please enter a login and/or a password";
}
