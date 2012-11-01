package war.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import war.dao.*;
import war.model.* ;
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

@Controller
@RequestMapping("/createwb")
public class WorkBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	private FilesDao filesDao ;
	@Autowired
	private WorkBoardDao workboardDao;
	@Autowired
	private PersonDao personDao ;
	private Person person ;
	private WorkBoard workboard ;
//	private FilesService stringfiles ;

	private Files file ;
	private String splitvar = "\\." ;
	private String [] tokens;

	@ModelAttribute("person")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView getUserWorkBoard(@RequestParam(value="username",required=true) String firstName, Model model) {
		logger.info("Inside get Work Board !");
		person = personDao.find(firstName) ;
		workboard = workboardDao.getActiveWorkBoard(person) ; 
		if (workboard == null){
			ModelAndView mav = CreateWorkBoard(firstName,model) ;
			return mav;	
		}
		
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;	
	}
	
	
	@ModelAttribute("file")
	@RequestMapping(value= "/upload", method = RequestMethod.POST)
	public ModelAndView uploadfileinWorkBoard(@RequestParam(value="files",required=true) MultipartFile uploadfile,
			@RequestParam(value="workboardid",required=true) Integer workboardid, Model model) throws IOException{
       
        try {
        	byte [] bytes = uploadfile.getBytes() ;
            file.setFile(bytes) ;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        workboard = workboardDao.find(workboardid) ;
        String filename = uploadfile.getOriginalFilename() ;
		tokens = filename.split(splitvar) ;
		file.setFilename(tokens[0]) ;
		file.setType(tokens[1]) ;
		file.setWorkboard(workboard) ;	
		filesDao.save(file) ;
		
		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;	
       
	}
	
	public ModelAndView CreateWorkBoard(String firstName, Model model) {
		person = personDao.find(firstName) ;
		ModelAndView mav = new ModelAndView();
		WorkBoard workboard = new WorkBoard();
		workboard.setPerson(person) ;
		mav.setViewName("createwb");
 		mav.addObject("workboard", workboard);
		model.addAttribute("firstName",person.getFirstName()) ;
 		model.addAttribute("secondName",person.getLastName()) ;
		return mav;
	}
	
	@RequestMapping(value = "/add",method=RequestMethod.POST) 
	public ModelAndView addWorkboard(@ModelAttribute WorkBoard workboard,@RequestParam(value="firstName",required=true) String firstname, Model model) {
		logger.debug("Received object for workboard  "+ workboard);
	
		person = personDao.find(firstname) ;
		workboard.setPerson(person) ;
		workboard.setMode("active") ;
		workboardDao.save(workboard);

		ModelAndView mav = modelForActiveWBview(model, workboard);
		return mav;	
		
	}
	
	@RequestMapping(value ="/update",method=RequestMethod.POST)
	public ModelAndView updateWorkboard(@ModelAttribute FilesService stringfiles, @RequestParam(value="workboardid",required=true) Integer workboardid,Model model) {
		logger.debug("Received object for workboard  "+ workboard);

		List<Files> updatefiles =  stringfiles.getFiles() ;
		System.out.println("Inside update function " + updatefiles.size()) ;
		Files actualfile ;
		for(int i = 0, n = updatefiles.size(); i < n; i++) {
			actualfile = filesDao.find(updatefiles.get(i).getFileid()) ;
			actualfile.setFile(updatefiles.get(i).toBytes(updatefiles.get(i).getFilecontent().toString())) ;
			filesDao.save(actualfile);
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
		person = personDao.find(workboard.getPerson().getFirstName()) ;
		
 		
 		List<Files> convertedfiles = new ArrayList<Files>() ;
 		
 		try {
			/////// Converting the bytefiles to stringfiles     ///////
			List<Files> files = filesDao.getFiles(workboard);
			Files stringfile;
			for (int i = 0, n = files.size(); i < n; i++) {
				stringfile = new Files();
				swapfile = new Files();
				swapfile = files.get(i);
				stringfile.setFileid(swapfile.getFileid());
				stringfile.setFilename(swapfile.getFilename());
				stringfile.setType(swapfile.getType());
				stringfile.setWorkboard(swapfile.getWorkboard());
				stringfile.setFile(swapfile.getFile());
				stringfile
						.setFilecontent(swapfile.toString((swapfile.getFile())));
				System.out.println("The Actual Value of the File : " + swapfile.getFile());
//				System.out.println("The String Value : " + swapfile.toString((swapfile.getFile())));
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
		model.addAttribute("firstName",person.getFirstName()) ;
 		model.addAttribute("secondName",person.getLastName()) ;	
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
		
		ModelAndView mav = CreateWorkBoard(person.getFirstName(),model) ;
		
		return mav;	
	}
	
	
	@RequestMapping(value = "/deletefile",method=RequestMethod.GET) 
	public ModelAndView deleteDataelement(@ModelAttribute Files file,@RequestParam(value="dataelementid",required=true) Integer Id, Model model) {
		logger.debug("Received object for workboard  "+ file);
		file = filesDao.find(Id) ;
		workboard = file.getWorkboard() ;
		filesDao.removeFile(Id) ;

		ModelAndView mav = modelForActiveWBview(model,workboard);
		return mav;	
	}
}