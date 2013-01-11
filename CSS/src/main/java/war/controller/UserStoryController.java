package war.controller;

import java.util.ArrayList;
import java.util.List;

import war.dao.*;
import war.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;

import org.apache.commons.codec.binary.* ; 


@Controller
@RequestMapping("/userstory")
public class UserStoryController {
		
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private DataElementDao dataElementDao;
	@Autowired
	private UserStoryDao userStoryDao;
	@Autowired
	private UserDao userDao;

	@RequestMapping(value= "/list", method = RequestMethod.GET)
	public ModelAndView getUserStoriesList(@RequestParam(value="user",required=true) String login, Model model) {
		logger.info("Inside getUserStoriesList !");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("listUS");
		
		try {			
			// Retrieve user
			User user = userDao.find(login);
			mav.addObject("user", user);
			
			// Retrieve user's Stories
			List<UserStory> userStoriesList = userStoryDao.getUserStories(user);
			mav.addObject("userStoriesList", userStoriesList);
			
			// Define a title
	 		model.addAttribute("listingTitle", "My User Stories");

		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_USERSTORY_LIST);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return mav;
	}
	
	@ModelAttribute("userstory")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserStoryFromUser(@RequestParam(value="id",required=true) Integer Id, Model model) {
		logger.info("Inside getUserStoryFromUser");

		UserStory userstory = userStoryDao.find(Id);
		//userstory.setMode("passive");
		//userStoryDao.save(userstory);
		
		ModelAndView mav = modelForUserStoryView(model, userstory);
		return mav;	
	}

	@RequestMapping(value="/includeDataElement", method=RequestMethod.GET) 
	public ModelAndView includeDataElementToUserStory(
			@ModelAttribute DataElement dataelement, 
			@RequestParam(value="dataelement",required=true) Integer dataElementId, 
			@RequestParam(value="story",required=true) Integer id, 
			Model model) {
		logger.info("Inside includeDataElementToUserStory");
		
		UserStory userstory = userStoryDao.find(id);
		/*file = filesDao.find(fileid);
		dataelement = new DataElement();
		dataelement.setDataelement(file.getName()) ;
		dataelement.setDataelementname(file.getName()) ;
		dataelement.setExtension(file.getType()) ;
		dataelement.setUserstory(userstory) ;
		
		String format = null ; 
		List<DataElement> dataelements = new ArrayList<DataElement>() ;
 		try {
 			
 			boolean TWO = false, THREE = false, FIVE = false, NONE = false ; 

			dataelements = dataelementDao.getDataElements(userstory) ;
			for (int i = 0, n = dataelements.size(); i < n; i++) {	
				if (dataelements.get(i).getFormat().equalsIgnoreCase("TWO")) TWO = true ;
				if (dataelements.get(i).getFormat().equalsIgnoreCase("THREE")) THREE = true ;
				if (dataelements.get(i).getFormat().equalsIgnoreCase("FIVE")) FIVE = true ;
			}
			System.out.println("Inside the " + TWO + " " + THREE + " " + FIVE);
			
			format = TWO ? (THREE ? (FIVE ? "NONE" : "FIVE") : "THREE") : (THREE ? "TWO" : "TWO")  ;
			System.out.println("The value in the data is : " + format);

 		} catch (NullPointerException e) {
				System.out.println ("No dataelement found") ;
		}
 		
 		dataelement.setFormat(format) ;
 		if (!format.equalsIgnoreCase("NONE")) dataelementDao.save(dataelement) ;
		*/
		ModelAndView mav = modelForUserStoryView(model, userstory);
		return mav;	
	}
	
	private ModelAndView modelForUserStoryView(Model model, UserStory userStory) {
		logger.debug("Inside modelForUserStoryView");

		ModelAndView mav = new ModelAndView();
		mav.addObject("userstory", userStory);
		
		try {
			model.addAttribute("user", userStory.getUser()) ;
			
			List<DataElement> dataElements = dataElementDao.getDataElements(userStory);
	 		for (DataElement dataElement : dataElements) {
	 			dataElement.generateStringContent();
			}
	 		mav.addObject("dataelements", dataElements);
	 		mav.addObject(new DataElement());  // This DataElement object is for the userwbmenu.jsp
	 		
 			mav.setViewName("passiveUS");
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Error: Impossible to retrieve the list of your stories";
	
}