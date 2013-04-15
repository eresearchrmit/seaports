package war.controller;

import helpers.DataElementPositionComparator;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import war.dao.CsiroDataBaselineDao;
import war.dao.UserStoryDao;
import war.model.AbsData;
import war.model.BitreData;
import war.model.CsiroData;
import war.model.DataElement;
import war.model.DataElementAbs;
import war.model.DataElementBitre;
import war.model.DataElementCsiro;
import war.model.DataElementEngineeringModel;
import war.model.DataElementFile;
import war.model.EngineeringModelData;
import war.model.UserStory;

/**
 * Controller for the public section of the application
 */
@Controller
public class PublicController {

	private static final Logger logger = LoggerFactory.getLogger(PublicController.class);
	
	@Autowired
	private UserStoryDao userStoryDao;
	
	@Autowired
	private CsiroDataBaselineDao csiroDataBaselineDao;
	
	/**
	 * Displays the home page view
	 */
	@RequestMapping(value = {"/", "/public"}, method = RequestMethod.GET)
	public String home(Model model) {		
		return "home";
	}
	
	/**
	 * Listing of all the published reports
	 */
	@RequestMapping(value = {"/public/reports/list"}, method = RequestMethod.GET)
	public ModelAndView getPublishedUserStoriesList(Model model) {
		logger.info("Inside getPublishedUserStoriesList");
		
		ModelAndView mav = new ModelAndView("userstoryPublicList");
		try {
			// Retrieve all published reports
			List<UserStory> userStoriesList = userStoryDao.getAllPublishedStories();
			mav.addObject("userStoriesList", userStoriesList);
	 		model.addAttribute("listingTitle", "Published Reports");
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_PUBLISHED_REPORT_LIST);
		}

		return mav;
	}
	
	@RequestMapping(value= "/public/reports/view", method = RequestMethod.GET)
	public ModelAndView getUserStoryPublicView(@RequestParam(value="id",required=true) Integer id, Model model) {
		logger.info("Inside getUserStoryPublicView");
		
		UserStory userStory = null;
		try {			
			userStory = userStoryDao.find(id);
		}
		catch (NoResultException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		
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
		
		model.addAttribute("publicView", true);
		
		return new ModelAndView("userstoryPublicView");
	}
	
	public static final String ERR_RETRIEVE_PUBLISHED_REPORT_LIST = "Impossible to retrieve the list of the published reports";
}
