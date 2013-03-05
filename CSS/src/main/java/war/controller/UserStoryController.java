package war.controller;

import helpers.DataElementPositionComparator;
import helpers.SecurityHelper;

import java.util.Date;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import war.dao.*;
import war.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;

@Controller
@RequestMapping("auth/userstory")
public class UserStoryController {
		
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserStoryDao userStoryDao;
	
	@Autowired
	private DataElementDao dataElementDao;
	
	@Autowired
	private CsiroDataBaselineDao csiroDataBaselineDao;
	
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	public ModelAndView getUserStoriesList(@RequestParam(value="user",required=true) String username, Model model) {
		logger.info("Inside getUserStoriesList");
		
		ModelAndView mav = new ModelAndView("userstoryList");
		try {
			if (!(SecurityHelper.IsCurrentUserMatching(username))) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			// Retrieve user
			User user = userDao.find(username);
			mav.addObject("user", user);
			
			// Retrieve user's Stories
			List<UserStory> userStoriesList = userStoryDao.getUserStories(user);
			mav.addObject("userStoriesList", userStoriesList);
			
			// Define a title
	 		model.addAttribute("listingTitle", "My User Stories");

		}
		catch (AccessDeniedException e) {
			return ModelForUserStoryAccessDenied(model);
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
	public ModelAndView getUserStory(@RequestParam(value="id",required=true) Integer id, Model model) {
		logger.info("Inside getUserStory");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
		}
		catch (AccessDeniedException e) {
			return ModelForUserStoryAccessDenied(model);
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return ModelForUserStory(model, userStory);
	}
	
	@RequestMapping(value= "/view", method = RequestMethod.GET)
	public ModelAndView getUserStoryView(@RequestParam(value="id",required=true) Integer id, Model model) {
		logger.info("Inside getUserStoryView");
		
		ModelAndView mav = getUserStory(id, model);
		mav.setViewName("userstoryView");
		return mav;
	}
	
	@RequestMapping(value= "/lock", method = RequestMethod.GET)
	public String changeUserStoryPrivacy(@RequestParam(value="id",required=true) Integer id, @RequestParam(value="lock",required=true) Boolean lock, Model model) {
		logger.info("Inside getUserStoriesList !");
		try {
			UserStory userStory= userStoryDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			if (lock) // true == locked == private
				userStory.setAccess("private");
			else // false == unlocked == public
				userStory.setAccess("public");
			userStoryDao.save(userStory);
		}
		catch (AccessDeniedException e) {
        	return "redirect:/accessDenied";
        }
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}

	@RequestMapping(value="/create", method=RequestMethod.GET) 
	public ModelAndView createUserStory(@RequestParam(value="id",required=true) Integer id, Model model) {
		logger.info("Inside createUserStory");
		
		UserStory userStory = null;
		try {
			// Retrieve the user story
			userStory = userStoryDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			if (!(userStory.getMode().equals("published")))
			{
				userStory.setMode("passive");
				userStoryDao.save(userStory);
			}
			else
				model.addAttribute("errorMessage", ERR_STORY_ALREADY_PUBLISHED);
		}
		catch (AccessDeniedException e) {
        	return ModelForUserStoryAccessDenied(model);
        }
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return ModelForUserStory(model, userStory);
	}
		
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public ModelAndView saveUserStory(
			@RequestParam(value="comments",required=true) String[] updatedTexts, 
			@Valid @ModelAttribute("userstory") UserStory updatedUserStory, 
			Model model) {
		logger.info("Inside saveUserStory");
		
		UserStory userStory = null;
		try {
			// Retrieve the original user story
			userStory = userStoryDao.find(updatedUserStory.getId());
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			// Reorder the data elements in the user story
			for (DataElement de : userStory.getDataElements()) {
				for (DataElement updatedDataElement : updatedUserStory.getDataElements()) {
					if (de.getId() == updatedDataElement.getId()) {
						de.setPosition(updatedDataElement.getPosition());
						break;
					}
				}
			}
			// Save the user story after reordering
			userStoryDao.save(userStory);
			
			// Update content of the text data elements if they have been changed
			Collections.sort(userStory.getDataElements(), new DataElementPositionComparator());
			int i = 0;
			for (DataElement dataElement : userStory.getDataElements()) {
				if (dataElement.getClass().equals(DataElementText.class)) {
					DataElementText dataElementText = (DataElementText)(dataElement);
					if (updatedTexts.length > i && updatedTexts[i] != null && !updatedTexts[i].equals(dataElementText.getText())) {
						dataElementText.setText(updatedTexts[i]);
						dataElementDao.save(dataElementText);
					}
					i++;
				}
			}
			model.addAttribute("successMessage", MSG_USERSTORY_SAVED);
		}
		catch (AccessDeniedException e) {
        	return ModelForUserStoryAccessDenied(model);
        }
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return ModelForUserStory(model, userStory);
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public String deleteUserStory(@RequestParam(value="id", required=true) Integer userStoryId, Model model) {
		logger.debug("Inside deleteUserStory");
		
		try {
			UserStory userStory = userStoryDao.find(userStoryId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			userStoryDao.delete(userStory);
			
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
		}
		catch (AccessDeniedException e) {
        	return "redirect:/accessDenied";
        }
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_DELETE_USERSTORY);
		}
		return "userstoryList";
	}	

	
	@RequestMapping(value="/addText", method=RequestMethod.GET) 
	public ModelAndView addTextToUserStory(@RequestParam(value="story",required=true) Integer id, Model model) {
		logger.info("Inside saveUserStory");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			DataElementText newTextItem = new DataElementText(new Date(), "Story Text", true, 0, userStory, "Add some text here...");
			dataElementDao.save(newTextItem);
			userStory = userStoryDao.find(id);
		}
		catch (AccessDeniedException e) {
        	return ModelForUserStoryAccessDenied(model);
        }
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return ModelForUserStory(model, userStory);
	}
	
	@RequestMapping(value="/deleteText", method=RequestMethod.GET) 
	public ModelAndView removeTextFromUserStory(@RequestParam(value="text",required=true) Integer id, Model model) {
		logger.info("Inside removeTextFromUserStory");
		
		UserStory userStory = null;
		try {
			DataElement dataElement = dataElementDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			dataElementDao.delete(dataElement);
			userStory = userStoryDao.find(dataElement.getUserStory().getId());
		}
		catch (AccessDeniedException e) {
        	return ModelForUserStoryAccessDenied(model);
        }
		catch (Exception e) {
			model.addAttribute("errorMessage", ERR_REMOVE_TEXT);
		}
		return ModelForUserStory(model, userStory);
	}
	
	@RequestMapping(value="/includeDataElement", method=RequestMethod.GET) 
	public ModelAndView includeDataElementToUserStory(@RequestParam(value="dataelement",required=true) Integer dataElementId, Model model) {
		logger.info("Inside includeDataElementToUserStory");
		
		UserStory userStory = null;
		try {
			DataElement dataElement = dataElementDao.find(dataElementId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(dataElement.getUserStory()))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			dataElement.setIncluded(!dataElement.getIncluded());
			dataElementDao.save(dataElement);
			userStory = userStoryDao.find(dataElement.getUserStory().getId());
		}
		catch (AccessDeniedException e) {
        	return ModelForUserStoryAccessDenied(model);
        }
 		catch (NoResultException e) {
 			model.addAttribute("errorMessage", e.getMessage());
		}
 		
 		return ModelForUserStory(model, userStory);
	}
	
	
	private ModelAndView ModelForUserStory(Model model, UserStory userStory) {
		logger.debug("Inside ModelForUserStory");

		model.addAttribute("user", userDao.find(SecurityHelper.getCurrentlyLoggedInUsername()));
		
		if (userStory != null)
		{
			Collections.sort(userStory.getDataElements(), new DataElementPositionComparator());
			
			List<DataElement> dataElements = userStory.getDataElements();
	 		for (DataElement dataElement : dataElements) {
	 			if (dataElement.getClass().equals(DataElementFile.class)) {
	 				((DataElementFile)dataElement).generateStringContent();
	 			}
	 			else if (dataElement.getClass().equals(DataElementCsiro.class)) {
	 				for (CsiroData data : ((DataElementCsiro)dataElement).getCsiroDataList()) {
	 					data.setBaseline(csiroDataBaselineDao.find(data.getParameters().getRegion(), data.getVariable()));
	 				}
	 			}
	 			else if (dataElement.getClass().equals(DataElementEngineeringModel.class)) {
	 				List<EngineeringModelData> engineeringModelDataList = ((DataElementEngineeringModel)dataElement).getEngineeringModelDataList();
	 				for (EngineeringModelData data : engineeringModelDataList) {
	 					data.generateValues();
	 				}
	 			}
			}
			model.addAttribute("userstory", userStory);
		}
		
		return new ModelAndView("userstory");
	}
	
	private ModelAndView ModelForUserStoryAccessDenied(Model model) {
		logger.debug("Inside ModelForUserStoryAccessDenied");

		model.addAttribute("user", userDao.find(SecurityHelper.getCurrentlyLoggedInUsername()));
		
		return new ModelAndView("accessDenied");
	}
	
	public static final String ERR_ACCESS_DENIED = "You are not allowed to access this Story";
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Impossible to retrieve the list of your stories";
	public static final String MSG_NO_USER_STORY = "There is no story to display";
	public static final String MSG_USERSTORY_SAVED = "The story has been saved successfully";
	public static final String ERR_SAVE_USERSTORY = "Error saving the story. Please Try Again";
	public static final String ERR_DELETE_USERSTORY = "Error deleting the story. Please Try Again";
	public static final String ERR_REMOVE_TEXT = "Error removing the text. Please Try Again";
	public static final String ERR_STORY_ALREADY_PUBLISHED = "This story is already published";
}