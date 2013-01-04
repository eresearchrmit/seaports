package war.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import war.dao.*;
import war.model.*;
import war.service.*;

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
public class WorkBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	private FilesDao filesDao ;
	@Autowired
	private WorkBoardDao workboardDao;
	@Autowired
	private PersonDao personDao ;
	@Autowired
	private CsiroDataDao dataDao;
	
	private Person person ;
	private WorkBoard workboard ;
//	private FilesService stringfiles ;

	private Files file ;
	private String splitvar = "\\." ;
	private String [] tokens;

	@ModelAttribute("person")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getUserWorkBoard(@RequestParam(value="username",required=true) String login, Model model) {
		logger.info("Inside get Work Board !");
		try {
			person = personDao.find(login) ;
			workboard = workboardDao.getActiveWorkBoard(person); 
			if (workboard == null){
				ModelAndView mav = CreateWorkBoard(login, model);
				return mav;
			}
		}
		catch (NullPointerException e)
		{
			model.addAttribute("errorMessage", "Error: Impossible to retrieve your Workboard.");
		}
		
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;
	}
	
	@ModelAttribute("file")
	@RequestMapping(value= "/upload", method = RequestMethod.POST)
	public ModelAndView uploadfileinWorkBoard(@RequestParam(value="files",required=true) MultipartFile uploadfile,
			@RequestParam(value="workboardid",required=true) Integer workboardid, Model model) throws IOException{
		
		if (workboard == null)
			workboard = workboardDao.find(workboardid);
		
        String filename = uploadfile.getOriginalFilename() ;
		tokens = filename.split(splitvar) ;
		file.setFilename(tokens[0]) ;
		file.setType(tokens[1]) ;
		file.setWorkboard(workboard) ;	
        try {
        	
        	System.out.println("Inside the image converstion........." + tokens[0] + tokens[1]);
        	byte [] bytes ;
        	if (tokens[1].contains("jpg")  || tokens[1].contains("jpeg")){
        		bytes = Base64.encodeBase64(uploadfile.getBytes()) ;
        		System.out.println("Inside the image converstion.........");
        		//if (Base64.isArrayByteBase64(bytes)) System.out.println("Yes the image is base 64"); else System.out.println("Yes the image is not base 64") ;
        	} else {
        		bytes = uploadfile.getBytes();
        	}
            file.setFile(bytes) ;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		filesDao.save(file) ;
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;	
       
	}
	
	@ModelAttribute("file")
	@RequestMapping(value= "/addCsiroData", method = RequestMethod.POST)
	public ModelAndView addCsiroDataToWorkBoard(
		@RequestParam(value="csiroVariable",required=true) String csiroVariable,
		@RequestParam(value="csiroEmissionScenario",required=true) String csiroEmissionScenario,
		@RequestParam(value="csiroClimateModel",required=true) String csiroClimateModel,
		@RequestParam(value="assessmentYear",required=true) String assessmentYear,
		@RequestParam(value="workboardid",required=true) Integer workboardid, Model model) 
		throws IOException 
	{
		logger.info("Inside addDataToWorkBoard !");
		if (workboard == null)
			workboard = workboardDao.find(workboardid);
        
		file.setFilename("CSIRO Data");
		file.setType("data");
		file.setWorkboard(workboard);
		
		try {
	        // Retrieve CSIRO data in the database according to the variable & parameters
			String content = "";
			if (csiroVariable.equals("All"))
			{
				List<CsiroData> dataList = dataDao.find("East Coast South", csiroEmissionScenario, csiroClimateModel, Integer.valueOf(assessmentYear));
				
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
				CsiroData data = dataDao.find("East Coast South", csiroEmissionScenario, csiroClimateModel, Integer.valueOf(assessmentYear), csiroVariable);
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
			
			file.setFile(content.getBytes());
			filesDao.save(file);
			model.addAttribute("controllerMessageSuccess", "The CSIRO Data has been added successfully to your workboard");
		}
		catch (Exception e) {
			model.addAttribute("controllerMessageError", "No CSIRO data found corresponding to the specified parameters");
		}
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;
	}
	
	public ModelAndView CreateWorkBoard(String login, Model model) {
		person = personDao.find(login);
		ModelAndView mav = new ModelAndView();
		WorkBoard workboard = new WorkBoard();
		workboard.setPerson(person) ;
		mav.setViewName("workboard");
 		mav.addObject("workboard", workboard);
		model.addAttribute("firstname",person.getFirstname());
 		model.addAttribute("secondname",person.getLastname());
		return mav;
	}
	
	@RequestMapping(value = "/add",method=RequestMethod.POST) 
	public ModelAndView addWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="login",required=true) String login, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
	
		person = personDao.find(login);
		workboard.setPerson(person);
		workboard.setMode("active");
		workboardDao.save(workboard);

		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;	
		
	}
	
	@RequestMapping(value ="/update",method=RequestMethod.POST)
	public ModelAndView updateWorkboard(@ModelAttribute FilesService stringfiles, @RequestParam(value="workboardid",required=true) Integer workboardid,Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		if (workboard == null)
			workboard = workboardDao.find(workboardid);
		
		List<Files> filesList = stringfiles.getFiles();
		System.out.println("Inside update function " + filesList.size());
		Files actualfile;
		for (Files file : filesList)
		{
			actualfile = filesDao.find(file.getFileid());
			if (!(actualfile.getType().contains("jpg") || actualfile.getType().contains("jpeg")
					|| actualfile.getType().contains("data"))) {
				actualfile.setFile(file.toBytes(file.getFilecontent().toString())) ;
			}
			//if (Base64.isArrayByteBase64(Base64.decodeBase64(updatefiles.get(i).getFilecontent()))){
				//System.out.println("Yes the image is base 64") ; 
			    //actualfile.setFile(Base64.decodeBase64(updatefiles.get(i).getFilecontent())) ;
			//}
			//else { 
				//encodebyte = Base64.encodeBase64(Base64.decodeBase64(updatefiles.get(i).getFilecontent())) ;
				//actualfile.setFile(encodebyte) ;
				//System.out.println("Yes the image is not base 64") ;	
			//}
			filesDao.save(actualfile);
			model.addAttribute("controllerMessageSuccess", "Workboard saved");
		}
		
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;		
	}

	private ModelAndView modelForActiveWBview(Model model, WorkBoard workboard) {
		FilesService stringfiles;
		Files swapfile;
		
		System.out.println("Inside the refractor" + person +" "+ personDao + " " + workboard);
		
		/* ** CRUD operation for the file and to redirect activeWB.jsp View** */
		ModelAndView mav = new ModelAndView();
		person = personDao.find(workboard.getPerson().getLogin());
		
 		
 		List<Files> convertedfiles = new ArrayList<Files>();
 		
 		try {
			/////// Converting the bytefiles to stringfiles     ///////
			List<Files> files = filesDao.getFiles(workboard);
			Files stringfile;
			//StringBuffer imagestring ;
			for (int i = 0, n = files.size(); i < n; i++) {
				stringfile = new Files();
				swapfile = new Files();
				swapfile = files.get(i);
				stringfile.setFileid(swapfile.getFileid());
				stringfile.setFilename(swapfile.getFilename());
				stringfile.setType(swapfile.getType());
				stringfile.setWorkboard(swapfile.getWorkboard());
				stringfile.setFile(swapfile.getFile());
				if (swapfile.getType() == "jpg" || swapfile.getType() == "jpeg" ) {
					System.out.println("Inside upload " + swapfile.getFile());
					//imagestring = new StringBuffer(Base64.encodeBase64String(swapfile.getFile()));
					stringfile.setFilecontent(Base64.encodeBase64String(swapfile.getFile())); 
				}
				else {
					stringfile.setFilecontent(swapfile.toString((swapfile.getFile())));
				}
				
				
				System.out.println("The Actual Value of the File : " + swapfile.getFile());
				//System.out.println("The String Value : " + swapfile.toString((swapfile.getFile())));
				convertedfiles.add(stringfile);
			}
		} catch (NullPointerException e) {
				convertedfiles.add(null) ;
		}
		stringfiles = new FilesService() ;
 		stringfiles.setFiles(convertedfiles) ;
 		
 		mav.addObject("workboard", workboard) ;
 		file = new Files() ;
 		mav.addObject(file) ;  // This file object id for the userwbmenu.jsp
 		model.addAttribute("login",person.getLogin());
 		model.addAttribute("firstname",person.getFirstname());
 		model.addAttribute("lastname",person.getLastname());	
 		model.addAttribute("workboardTitle", workboard.getWorkBoardName()) ;
 		model.addAttribute("workboardID", workboard.getWorkBoardID());
 		mav.addObject("stringfiles",stringfiles) ;
		mav.setViewName("activeWB");
		return mav;
	}
	
	@RequestMapping(value = "/deleteWB",method=RequestMethod.GET) 
	public ModelAndView deleteWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="workboardid",required=true) Integer Id, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
		workboard = workboardDao.find(Id) ;
		person = workboard.getPerson() ;
		workboardDao.removeWorkBoard(Id,workboard) ;
		
		ModelAndView mav = CreateWorkBoard(person.getLogin(),model) ;
		
		return mav;	
	}
		
	@RequestMapping(value = "/deletefile",method=RequestMethod.GET) 
	public ModelAndView deleteDataelement(@ModelAttribute Files file,@RequestParam(value="dataelementid",required=true) Integer Id, Model model) {
		logger.debug("Received object for workboard  "+ file);
		try {
			file = filesDao.find(Id);
			workboard = file.getWorkboard();
			filesDao.removeFile(Id);
			model.addAttribute("controllerMessageSuccess", "The Data Element was deleted successfully from your Workboard");
		}
		catch (Exception e)
		{
			model.addAttribute("controllerMessageError", "The Data Element '" + file.getFilename() + "' could not be deleted.");
		}
		
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;	
	}
}