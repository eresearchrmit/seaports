package war.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

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

@Controller
@RequestMapping("/userstory")
public class UserStoryController {
		
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
	
	@RequestMapping(value= "/lock", method = RequestMethod.GET)
	public String changeUserStoryPrivacy(@RequestParam(value="user",required=true) String login, @RequestParam(value="id",required=true) Integer id, @RequestParam(value="lock",required=true) Boolean lock, Model model) {
		logger.info("Inside getUserStoriesList !");
		try {
			UserStory userStory= userStoryDao.find(id);
			if (lock)
				userStory.setAccess("private");
			else
				userStory.setAccess("public");
			userStoryDao.save(userStory);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return "redirect:/spring/userstory/list?user=" + login;
	}
	
	@ModelAttribute("userstory")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserStory(@RequestParam(value="id",required=true) Integer Id, Model model) {
		logger.info("Inside getUserStoryFromUser");
		
		UserStory userStory = null;
		
		try {
			userStory = userStoryDao.find(Id);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return modelForUserStoryView(model, userStory);
	}

	@RequestMapping(value="/create", method=RequestMethod.GET) 
	public ModelAndView createUserStory(@RequestParam(value="id",required=true) Integer Id, Model model) {
		logger.info("Inside includeDataElementToUserStory");
		
		try {
			// Retrieve the user story
			UserStory userstory = userStoryDao.find(Id);
			userstory.setMode("passive");
			userStoryDao.save(userstory);
			
			return modelForUserStoryView(model, userstory);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return modelForUserStoryView(model, null);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public ModelAndView saveUserStory(@Valid @ModelAttribute UserStory reorderedUserStory, @RequestParam(value="id",required=true) Integer Id, Model model) {
		logger.info("Inside saveUserStory");
		
		try {
			// Retrieve the original user story
			UserStory userStory = userStoryDao.find(Id);
			
			// Reorder the data elements in the user story
			userStory = userStoryDao.find(Id);
			for (DataElement de : userStory.getDataElements()) {
				for (DataElement reorderedDE : reorderedUserStory.getDataElements()) {
					// Reorder the existing data elements
					if (de.getId() == reorderedDE.getId())
					{
						de.setPosition(reorderedDE.getPosition());
						// Update the content if the data element is of type 'comment'
						if (reorderedDE.getType().equals("comment"))
							de.setStringContent(reorderedDE.getStringContent());
						break;
					}
				}
			}
			
			// Create the new 'comment' Data Elements and add them to the user story
			List<DataElement> dataElements = userStory.getDataElements();
			for (DataElement reorderedDE : reorderedUserStory.getDataElements()) {
				if (reorderedDE.getId() == 0 && reorderedDE.getType().equals("comment")) {
					reorderedDE.setUserStory(userStory);
					dataElements.add(reorderedDE);
				}
			}
			userStory.setDataElements(dataElements);
			
			// Save the user story after reordering
			userStoryDao.save(userStory);
			
			model.addAttribute("successMessage", MSG_USERSTORY_SAVED);
			return modelForUserStoryView(model, userStory);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return modelForUserStoryView(model, null);
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public String deleteUserStory(@RequestParam(value="id", required=true) Integer userStoryId, Model model) {
		logger.debug("Inside deleteUserStory");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("listUS");
		
		try {
			UserStory userStory = userStoryDao.find(userStoryId);
			mav.addObject("user", userStory.getOwner().getLogin());
			String ownerLogin = userStory.getOwner().getLogin();
			userStoryDao.deleteUserStory(userStory);
			return "redirect:/spring/userstory/list?user=" + ownerLogin;
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return "listUS";
	}	
	
	/*
	@RequestMapping(value="/includeDataElement", method=RequestMethod.GET) 
	public ModelAndView includeDataElementToUserStory(
			@ModelAttribute DataElement dataelement, 
			@RequestParam(value="dataelement",required=true) Integer dataElementId, 
			@RequestParam(value="story",required=true) Integer id, 
			Model model) {
		logger.info("Inside includeDataElementToUserStory");
		
		UserStory userstory = userStoryDao.find(id);
		file = filesDao.find(fileid);
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
		
		ModelAndView mav = modelForUserStoryView(model, userstory);
		return mav;	
	}*/
	
	
	private ModelAndView modelForUserStoryView(Model model, UserStory userStory) {
		logger.debug("Inside modelForUserStoryView");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("passiveUS");
		
		if (userStory != null)
		{
			Collections.sort(userStory.getDataElements(), new DateElementPositionComparator());
			model.addAttribute("userstory", userStory);
			model.addAttribute("user", userStory.getOwner());
			/*try {
				model.addAttribute("user", userStory.getOwner()) ;
				
				List<DataElement> dataElements = dataElementDao.getDataElements(userStory);
		 		for (DataElement dataElement : dataElements) {
		 			dataElement.generateStringContent();
				}
		 		mav.addObject("dataelements", dataElements);
		 		mav.addObject(new DataElement());
			}
			catch (Exception e) {
				model.addAttribute("errorMessage", e.getMessage());
			}*/
		}
		return mav;
	}
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Impossible to retrieve the list of your stories";
	public static final String MSG_NO_USER_STORY = "There is no story to display";
	public static final String MSG_USERSTORY_SAVED = "The story has been saved successfully";
	public static final String ERR_SAVE_USERSTORY = "Error while saving the story. Please Try Again";
}