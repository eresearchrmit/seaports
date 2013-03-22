package war.controller;

import helpers.SecurityHelper;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserLoginService;

import war.dao.UserDao;
import war.model.User;

/**
 * Controller for the administration tool
 */
@Controller
@RequestMapping("admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = {"/users/list"}, method = RequestMethod.GET)
	public ModelAndView userList(Model model) {
		logger.info("Inside userList");
		
		ModelAndView mav = new ModelAndView("userList");
		
		try {
			User currentUser = userDao.find(SecurityHelper.getCurrentlyLoggedInUsername());
			
			if (!(currentUser.getRoles().equals(UserLoginService.ROLE_ADMINISTRATOR))) // Security: admin rights check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			model.addAttribute("user", currentUser);
			
			// Retrieve users list
			List<User> usersList = userDao.getPeople();
			mav.addObject("usersList", usersList);
			
			// Define a title
	 		model.addAttribute("listingTitle", "All users");
		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_USERS_LIST);
		}
		
		return mav;
	}
	
	@RequestMapping(value = {"/users/list/{isEnabled}"}, method = RequestMethod.GET)
	public ModelAndView userEnableDisable(@PathVariable String isEnabled, @RequestParam(value="user",required=true) String username, Model model) {
		logger.info("Inside userEnableDisable");
		
		try {
			// Security: admin rights check
			User currentUser = userDao.find(SecurityHelper.getCurrentlyLoggedInUsername());
			if (!(currentUser.getRoles().equals(UserLoginService.ROLE_ADMINISTRATOR))) // Security: admin rights check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			// Prevent disabling Administrator accounts
			User user = userDao.find(username);
			if (user.getRoles().equals(UserLoginService.ROLE_ADMINISTRATOR))
				throw new IllegalArgumentException(ERR_CANNOT_DISABLE_ADMIN);
			
			// Disable or enable the User account
			if (isEnabled.equals("enable"))
				user.setEnabled(true);
			else if (isEnabled.equals("disable"))
				user.setEnabled(false);
			userDao.save(user);
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return userList(model);
	}
	
	public static final String ERR_ACCESS_DENIED = "You don't have the rights to access this page";
	
	public static final String ERR_RETRIEVE_USERS_LIST = "Impossible to retrieve the list of users";
	public static final String ERR_CANNOT_DISABLE_ADMIN = "Administrator accounts cannot be disabled";
}
