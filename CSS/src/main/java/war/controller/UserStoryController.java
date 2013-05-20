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
import war.model.DataElement.DisplayType;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	/*@RequestMapping(value= "/userstory-created", method = RequestMethod.GET)
	public String workboardCreated(@RequestParam(value="id",required=true) Integer userstoryId, Model model) {
		model.addAttribute("userstoryId", userstoryId);
		return "userstoryCreated";
	}*/
	
	@RequestMapping(value= "/list", method = RequestMethod.GET)
	public ModelAndView getUserStoriesList(@RequestParam(value="user",required=true) String username, Model model) {
		logger.info("Inside getUserStoriesList");
		
		ModelAndView mav = new ModelAndView("userstoryList");
		try {
			User curentUser = userDao.find(SecurityHelper.getCurrentlyLoggedInUsername());
			mav.addObject("user", curentUser);
			
			// Retrieve user
			User user = userDao.find(username);
			
			if (!(SecurityHelper.IsCurrentUserMatching(user.getUsername()) || SecurityHelper.IsCurrentUserAdmin())) // Security: ownership check
				throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			// Retrieve user's Stories
			List<UserStory> userStoriesList = userStoryDao.getUserStories(user);
			mav.addObject("userStoriesList", userStoriesList);
			
			// Define a title
	 		model.addAttribute("listingTitle", "My Reports");
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_USERSTORY_LIST);
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
		catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
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
	public String changeUserStoryPrivacy(
			@RequestParam(value="id",required=true) Integer id, 
			@RequestParam(value="lock",required=true) Boolean lock, 
			RedirectAttributes attributes) {
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
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_SAVE_USERSTORY);
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
			
			if (!(userStory.getMode().equals("published"))) {
				userStory.setMode("passive");
				userStoryDao.save(userStory);
				
				// Initial ordering of the data elements in the user story
				int i = 1;
				for (DataElement de : userStory.getDataElements()) {
					de.setPosition(i);
					i++;
				}
				userStoryDao.save(userStory);
				model.addAttribute("userstory", userStory);
				return new ModelAndView("userstoryCreated");
			}
			else
				model.addAttribute("errorMessage", ERR_STORY_ALREADY_PUBLISHED);
		}
		catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		return ModelForUserStory(model, userStory);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST) 
	public String saveUserStory(
			@RequestParam(value="comments",required=false) String[] updatedTexts, 
			@Valid @ModelAttribute("userstory") UserStory updatedUserStory, 
			RedirectAttributes attributes) {
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
			
			Collections.sort(userStory.getDataElements(), new DataElementPositionComparator());
			
			// Update content of the text data elements if they have been changed
			if (updatedTexts != null) {
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
			}
			attributes.addFlashAttribute("successMessage", MSG_USERSTORY_SAVED);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_SAVE_USERSTORY);
		}
		
		if (userStory != null)
			return "redirect:/auth/userstory?id=" + userStory.getId();
		else
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public String deleteUserStory(@RequestParam(value="id", required=true) Integer userStoryId, RedirectAttributes attributes) {
		logger.debug("Inside deleteUserStory");
		
		try {
			UserStory userStory = userStoryDao.find(userStoryId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			userStoryDao.delete(userStory);
			attributes.addFlashAttribute("successMessage", MSG_USERSTORY_DELETED);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_DELETE_USERSTORY);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_DELETE_USERSTORY);
		}
		
		return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}	

	@RequestMapping(value="/addText", method=RequestMethod.POST) 
	public String addTextToUserStory(@RequestParam(value="userStoryId",required=true) Integer id, 
			@RequestParam(value="textContent",required=true) String textContent, 
			@RequestParam(value="textInsertPosition",required=true) String insertTextAfter, RedirectAttributes attributes) {
		logger.info("Inside saveUserStory");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			Integer newTextPosition = Integer.parseInt(insertTextAfter) + 1;
			
			// Increment the positions of all data elements after the new one
			int i = 0;
			for (DataElement de : userStory.getDataElements()) {
				if (de.getPosition() >= newTextPosition)
					de.setPosition(de.getPosition() + 1);
				i++;
			}
			// Save the user story after reordering
			userStoryDao.save(userStory);
			
			DataElementText newTextItem = new DataElementText(new Date(), "Report Text", true, newTextPosition, DisplayType.PLAIN, userStory, textContent);
			dataElementDao.save(newTextItem);
			
			attributes.addFlashAttribute("successMessage", MSG_TEXT_ADDED);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_ADD_TEXT);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_ADD_TEXT);
		}
		
		if (userStory != null)
			return "redirect:/auth/userstory?id=" + userStory.getId();
		else
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	@RequestMapping(value="/editText", method=RequestMethod.POST) 
	public String editText(@RequestParam(value="dataElementId") Integer dataElementId,
			@RequestParam(value="textContent") String textContent, RedirectAttributes attributes) {
		logger.info("Inside editText");
		
		DataElement dataElement = null;
		try {
			// Retrieve the data element
			dataElement = dataElementDao.find(dataElementId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(dataElement.getUserStory()))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			if (dataElement instanceof DataElementText) {
				DataElementText deText = (DataElementText)(dataElement);
				deText.setText(textContent);
				dataElementDao.save(deText);
			}
			attributes.addFlashAttribute("successMessage", MSG_TEXT_EDITED);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_EDIT_TEXT);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_EDIT_TEXT);
		}
		
		if (dataElement != null)
			return "redirect:/auth/userstory?id=" + dataElement.getUserStory().getId();
		else
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	@RequestMapping(value="/deleteText", method=RequestMethod.GET) 
	public String removeTextFromUserStory(@RequestParam(value="text",required=true) Integer id, RedirectAttributes attributes) {
		logger.info("Inside removeTextFromUserStory");
		
		DataElement dataElement = null;
		try {
			dataElement = dataElementDao.find(id);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(dataElement.getUserStory()))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			if (!(dataElement instanceof DataElementText))
				throw new IllegalArgumentException();
			
			dataElementDao.delete(dataElement);
			attributes.addFlashAttribute("successMessage", MSG_TEXT_REMOVED);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_REMOVE_TEXT);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_REMOVE_TEXT);
		}
		
		if (dataElement != null)
			return "redirect:/auth/userstory?id=" + dataElement.getUserStory().getId();
		else
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	@RequestMapping(value="/includeDataElement", method=RequestMethod.GET) 
	public String includeDataElementToUserStory(@RequestParam(value="dataelement",required=true) Integer dataElementId, RedirectAttributes attributes) {
		logger.info("Inside includeDataElementToUserStory");
		
		DataElement dataElement = null;
		try {
			dataElement = dataElementDao.find(dataElementId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(dataElement.getUserStory()))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			dataElement.setIncluded(!dataElement.getIncluded());
			dataElementDao.save(dataElement);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", e.getMessage());
		}
 		
		if (dataElement != null)
			return "redirect:/auth/userstory?id=" + dataElement.getUserStory().getId();
		else
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	@RequestMapping(value = "/publish", method=RequestMethod.GET) 
	public String publishUserStory(@RequestParam(value="id", required=true) Integer userStoryId, RedirectAttributes attributes) {
		logger.debug("Inside publishUserStory");
		
		UserStory userStory = null;
		try {
			userStory = userStoryDao.find(userStoryId);
			
			if (!(SecurityHelper.IsCurrentUserAllowedToAccess(userStory))) // Security: ownership check
    			throw new AccessDeniedException(ERR_ACCESS_DENIED);
			
			if (!userStory.getMode().equals("published")) {
				userStory.setAccess("public");
				userStory.setMode("published");
				userStory.setPublishDate(new Date());
								
				userStoryDao.save(userStory);
				
				attributes.addFlashAttribute("successMessage", MSG_STORY_PUBLISHED);
			}
			else
				attributes.addFlashAttribute("warningMessage", ERR_STORY_ALREADY_PUBLISHED);
		}
		catch (IllegalArgumentException e) {
			attributes.addFlashAttribute("errorMessage", ERR_DELETE_USERSTORY);
		}
		catch (NoResultException e) {
			attributes.addFlashAttribute("errorMessage", ERR_DELETE_USERSTORY);
		}

		if (userStory != null)
			return "redirect:/auth/userstory?id=" + userStory.getId();
		else
			return "redirect:/auth/userstory/list?user=" + SecurityHelper.getCurrentlyLoggedInUsername();
	}
	
	public ModelAndView ModelForUserStory(Model model, UserStory userStory) {
		logger.debug("Inside ModelForUserStory");

		model.addAttribute("user", userDao.find(SecurityHelper.getCurrentlyLoggedInUsername()));
		
		if (userStory != null)
		{
			Collections.sort(userStory.getDataElements(), new DataElementPositionComparator());
			
			List<DataElement> dataElements = userStory.getDataElements();
	 		for (DataElement dataElement : dataElements) {
	 			if (dataElement.getClass().equals(DataElementAbs.class)) {
	 				List<AbsData> absDataList = ((DataElementAbs)dataElement).getAbsDataList();
	 				for (AbsData data : absDataList) {
	 					data.generateValues();
	 				}
	 			}
	 			else if (dataElement.getClass().equals(DataElementBitre.class)) {
	 				List<BitreData> bitreDataList = ((DataElementBitre)dataElement).getBitreDataList();
	 				for (BitreData data : bitreDataList) {
	 					data.generateValues();
	 				}
	 			}
	 			else if (dataElement.getClass().equals(DataElementFile.class)) {
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
	
	public static final String ERR_ACCESS_DENIED = "You are not allowed to access this Story";
	
	public static final String ERR_RETRIEVE_USERSTORY_LIST = "Impossible to retrieve the list of your reports";
	public static final String MSG_NO_USER_STORY = "There is no report to display";
	public static final String MSG_USERSTORY_SAVED = "The report has been saved successfully";
	public static final String ERR_SAVE_USERSTORY = "Error saving the report. Please Try Again";
	public static final String MSG_USERSTORY_DELETED = "The report has been deleted successfully";
	public static final String ERR_DELETE_USERSTORY = "Error deleting the report. Please Try Again";
	
	public static final String MSG_TEXT_ADDED = "Text added successfully";
	public static final String ERR_ADD_TEXT = "Error adding text. Please Try Again";
	public static final String MSG_TEXT_EDITED = "Text edited successfully";
	public static final String ERR_EDIT_TEXT = "Error editing the text. Please Try Again";
	public static final String MSG_TEXT_REMOVED = "Text deleted successfully";
	public static final String ERR_REMOVE_TEXT = "Error deleting the text. Please Try Again";
	
	public static final String MSG_STORY_PUBLISHED = "The report is now published. It appears publicly on this portal and will be listed on Reasearch Data Australia search results.";
	public static final String ERR_STORY_ALREADY_PUBLISHED = "This report is already published";
}