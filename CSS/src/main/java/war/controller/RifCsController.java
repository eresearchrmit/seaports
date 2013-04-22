package war.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import war.dao.*;
import war.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class RifCsController extends AbstractController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserStoryDao userStoryDao;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Inside RIF-CS generation");
		
		ModelAndView mav = new ModelAndView("RifCs");
		try {			
			// Retrieve all published reports
			List<UserStory> userStoriesList = userStoryDao.getAllPublishedStories();
			mav.addObject("userStoriesList", userStoriesList);
		}
		catch (NoResultException e) {
			//model.addAttribute("errorMessage", ERR_RETRIEVE_USERSTORY_LIST);
		}
		return mav;
	}
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Impossible to retrieve the list of published stories";
}