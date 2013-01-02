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

import org.apache.commons.codec.binary.* ;
import org.springframework.web.servlet.tags.* ; 
import org.apache.commons.lang.* ; 


@Controller
@RequestMapping("/createDE")
public class DataElementController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	@Autowired
	private CsiroDataDao dataDao;
	
	private CsiroData data;
	private Person person;
	private WorkBoard workboard;

	private Files file;
	private String splitvar = "\\.";
	private String [] tokens;

	@RequestMapping(value = "/add",method=RequestMethod.GET) 
	public ModelAndView getUserWorkBoard(@RequestParam(value="workboardid",required=true) int id, Model model) {
		logger.info("Inside get CSIRO Data !");
		
		ModelAndView mav = new ModelAndView();
		
		// TODO: research data
		data = dataDao.find(42);
 		mav.addObject("csiroData", data);
		mav.setViewName("createDE");
		
		return mav;	
	}
}