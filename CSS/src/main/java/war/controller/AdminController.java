package war.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import war.dao.UserDao;
import war.model.User;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
@RequestMapping("admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private UserDao userDao;
	
	/**
	 * Selects the home page and populates the model with a message
	 */
	@RequestMapping(value = {"/users/list"}, method = RequestMethod.GET)
	public ModelAndView userList(Model model) {
		logger.info("Inside userList");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("userList");
		
		try {			
			// Retrieve user
			List<User> usersList = userDao.getPeople();
			mav.addObject("usersList", usersList);
			
			// Define a title
	 		model.addAttribute("listingTitle", "All users");

		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_USERS_LIST);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return mav;
	}
	
	public static final String ERR_RETRIEVE_USERS_LIST = "Impossible to retrieve the list of users";
}
