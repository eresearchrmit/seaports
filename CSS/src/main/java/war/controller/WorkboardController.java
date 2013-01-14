package war.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.ui.Model;

import org.apache.commons.codec.binary.*;


@Controller
@RequestMapping("/workboard")
public class WorkboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private DataElementDao dataElementDao;
	
	@Autowired
	private UserStoryDao userStoryDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CsiroDataDao csiroDataDao;
	
	//@ModelAttribute("user")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserWorkBoard(@RequestParam(value="user",required=true) String login, Model model) {
		logger.info("Inside getUserWorkBoard");
		
		try {
			User user = userDao.find(login);
			UserStory userStory = userStoryDao.getWorkBoard(user); 
			if (userStory == null) {
				return CreateWorkBoard(user, model);
			}
			return modelForActiveWBview(model, userStory);
		}
		catch (NullPointerException e) {
			model.addAttribute("errorMessage", ERR_RETRIEVE_WORKBOARD);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value= "/upload", method = RequestMethod.POST)
	public ModelAndView uploadfileinWorkBoard(
			@RequestParam(value="file",required=true) MultipartFile uploadfile,
			@RequestParam(value="id",required=true) Integer userStoryId, 
			Model model) 
	throws IOException {
		logger.info("Inside uploadfileinWorkBoard");
		
		UserStory userStory = userStoryDao.find(userStoryId);
        try {
            String filename = uploadfile.getOriginalFilename();
            String [] tokens = filename.split("\\.");
            
	        DataElement dataElement = new DataElement();
			dataElement.setName(tokens[0]);
			dataElement.setType(tokens[1]);
			dataElement.setUserStory(userStory);
        
        	dataElement.setContent(uploadfile.getBytes());
        	dataElementDao.save(dataElement);
        	
        	model.addAttribute("successMessage", "The file was uploaded successfully to your workboard");
        }
        catch (Exception e) {
        	model.addAttribute("errorMessage", "Unable to upload the file to your workboard");
        }
        
		ModelAndView mav = modelForActiveWBview(model, userStory);
		
		return mav;	
	}
	
	//@ModelAttribute("csiroData")
	@RequestMapping(value= "/addCsiroData", method = RequestMethod.POST)
	public ModelAndView addCsiroDataToWorkBoard(
		@RequestParam(value="csiroVariable",required=true) String csiroVariable,
		@RequestParam(value="csiroEmissionScenario",required=true) String csiroEmissionScenario,
		@RequestParam(value="csiroClimateModel",required=true) String csiroClimateModel,
		@RequestParam(value="assessmentYear",required=true) String assessmentYear,
		@RequestParam(value="id",required=true) Integer userStoryId, Model model)
	{
		logger.info("Inside addDataToWorkBoard !");
		UserStory userStory = userStoryDao.find(userStoryId);
        
		DataElement dataElement = new DataElement();
		dataElement.setName("CSIRO Data");
		dataElement.setType("data");
		dataElement.setUserStory(userStory);
		
		try {
	        // Retrieve CSIRO data in the database according to the variable & parameters
			String content = "";
			if (csiroVariable.equals("All"))
			{
				List<CsiroData> dataList = csiroDataDao.find("East Coast South", csiroEmissionScenario, csiroClimateModel, Integer.valueOf(assessmentYear));
				
				content += "<table class=\"data display datatable\" id=\"example\">";
				content += "	<thead>";
				content += "		<tr>";
				content += "			<th>Variable</th>";
				content += "			<th>Value</th>";
				content += "			<th>Asessement Year</th>";
				content += "			<th>Emission Scenario</th>";
				content += "			<th>Climate Model</th>";
				content += "			<th>Region</th>";
				content += "		</tr>";
				content += "	</thead>";
				content += "	<tbody>";
				Integer i = 0;
				for (CsiroData data : dataList)
				{
					if (i % 2 == 0)
						content += "		<tr class=\"odd\">";
					else
						content += "		<tr class=\"even\">";
					
					content += "			<td>" + data.getVariable().getName() + "</td>";
					content += "			<td class=\"center\">" + data.getValue() + data.getVariable().getUom() + "</td>";
					content += "			<td>" + data.getParameters().getAssessmentYear() + "</td>";
					content += "			<td>" + data.getParameters().getEmissionScenario() + "</td>";
					content += "			<td>" + data.getParameters().getModelName() + "</td>";
					content += "			<td>" + data.getRegion().getName() + "</td>";
					content += "		</tr>";
				}
				content += "	</tbody>";
				content += "</table>";
			}
			else // Generate a text statement from the data & save it as the file content
			{
				// Retrieve the data for the specified variable
				CsiroData data = csiroDataDao.find("East Coast South", csiroEmissionScenario, csiroClimateModel, Integer.valueOf(assessmentYear), csiroVariable);
				String statementVerb = "not increase";
				if (data.getValue() > 0)
					statementVerb = "increase of <b>" + data.getValue() + " " + data.getVariable().getUom() + "</b>";
				else if (data.getValue() < 0)
					statementVerb = "decrease of <b>" + Math.abs(data.getValue()) + " " + data.getVariable().getUom() + "</b>";
				content += "<p>According to the <b>" + data.getParameters().getModelName() + "</b> climate model, ";
				content += "if the <b>" + data.getParameters().getEmissionScenario() + "</b> emissions scenario happens,";
				content += "the <b>" + data.getVariable().getName() + "</b> will " + statementVerb + " ";
				content += "by <b>" + data.getParameters().getAssessmentYear() + "</b> in the <b>" + data.getRegion().getName() + "</b> region.</p>";
			}
			
			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
			Date date = new Date();
			content += "<i>Based on data from CSIRO, generated on " + dateFormat.format(date) + ".</i>";
			
			dataElement.setContent(content.getBytes());
			dataElementDao.save(dataElement);
			model.addAttribute("successMessage", MSG_CSIRO_DATA_ADDED);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		ModelAndView mav = modelForActiveWBview(model, userStory);
		return mav;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST) 
	public ModelAndView addWorkboard(@ModelAttribute("userstory") UserStory userStory, @RequestParam(value="login",required=true) String login, Model model) {
		logger.debug("Received object for workboard  " + userStory);
        
		try {
			User user = userDao.find(login);
			userStory.setOwner(user);
			userStory.setMode("active");
			userStoryDao.save(userStory);
			
			return modelForActiveWBview(model, userStory);
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value ="/save",method=RequestMethod.POST)
	public ModelAndView saveWorkboard(@ModelAttribute UserStory workboard, @RequestParam(value="id",required=true) Integer userStoryId, Model model) {
		logger.debug("Inside saveWorkboard");
		
		UserStory userStory = userStoryDao.find(userStoryId);
		
		for (DataElement dataElement : workboard.getDataElements())
		{			
			if (!(dataElement.getType().contains("jpg") || dataElement.getType().contains("jpeg") || dataElement.getType().contains("data"))) {
				String stringContent = dataElement.getContent().toString();
				dataElement.setContent(stringContent.getBytes());
			}
			dataElementDao.save(dataElement);
			model.addAttribute("successMessage", "Workboard saved");
		}
		
		ModelAndView mav = modelForActiveWBview(model, userStory);
		return mav;		
	}

	@RequestMapping(value = "/delete",method=RequestMethod.GET) 
	public ModelAndView deleteWorkboard(@RequestParam(value="id", required=true) Integer userStoryId, Model model) {
		logger.debug("Inside deleteWorkboard");
		
		UserStory userStory = userStoryDao.find(userStoryId);
		userStoryDao.deleteUserStory(userStory);
		
		ModelAndView mav = CreateWorkBoard(userStory.getOwner(), model);
		
		return mav;	
	}
		
	@RequestMapping(value = "/deletedataelement",method=RequestMethod.GET) 
	public ModelAndView deleteDataElementFromUserStory(@RequestParam(value="dataelementid",required=true) Integer dataElementId, Model model) {
		logger.debug("Inside deleteDataElementFromUserStory");
		
		try {
			DataElement dataElement = dataElementDao.find(dataElementId);
			UserStory userStory = dataElement.getUserStory();
			
			dataElementDao.deleteDataElement(dataElementId);
			model.addAttribute("successMessage", "The Data Element was deleted successfully from your Workboard");
			
			ModelAndView mav = modelForActiveWBview(model, userStory);
			return mav;	
		}
		catch (Exception e)
		{
			model.addAttribute("errorMessage", "The Data Element could not be deleted.");
		}
		
		return modelForActiveWBview(model, null);
	}

	private ModelAndView CreateWorkBoard(User user, Model model) {
		ModelAndView mav = new ModelAndView();
		try {
			model.addAttribute("user", user);
			
			UserStory userStory = new UserStory();
			userStory.setOwner(user) ;
			mav.addObject("userstory", userStory);
			
			mav.setViewName("createWB");
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	private ModelAndView modelForActiveWBview(Model model, UserStory userStory) {
		logger.debug("Inside modelForActiveWBview");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("userstory", userStory);
		model.addAttribute("user", userStory.getOwner());
		
		try {
			List<DataElement> dataElements = dataElementDao.getDataElements(userStory);
	 		for (DataElement dataElement : dataElements) {
	 			dataElement.generateStringContent();
			}
	 		mav.addObject("dataelements", dataElements);
	 		mav.addObject(new DataElement());  // This file object is for the userwbmenu.jsp

			mav.setViewName("activeWB");
		}
		catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return mav;
	}
	
	public static final String ERR_RETRIEVE_WORKBOARD = "Impossible to retrieve your Workboard";
	public static final String MSG_CSIRO_DATA_ADDED = "The CSIRO Data has been added successfully to your workboard";
}