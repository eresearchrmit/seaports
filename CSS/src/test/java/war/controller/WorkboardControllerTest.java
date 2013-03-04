package war.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import security.UserLoginService;
import war.dao.ClimateEmissionScenarioDao;
import war.dao.ClimateParamsDao;
import war.dao.CsiroVariableDao;
import war.dao.DataElementDao;
import war.dao.UserDao;
import war.dao.UserStoryDao;
import war.model.UserAuthority;
import war.model.DataElement;
import war.model.DataElementFile;
import war.model.Region;
import war.model.User;
import war.model.UserStory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorkboardControllerTest {
	
	@Autowired
	private WorkboardController workboardController;
	
	/**
	 * getUserWorkBoard should fail because User is Unknown: the model is filled with an error message
	 */
	@Test
	public void getUserWorkBoardUnknownUserTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("UNKNOWN USER", model);
		
		Assert.assertNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(UserDao.ERR_NO_SUCH_USER, model.get("errorMessage"));
	}
	
	/**
	 * getUserWorkBoard should succeed & return an new workboard for testuser3
	 */
	@Test
	public void getUserWorkBoardNoActiveWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("testuser3", model);
		
		// Make sure no error happened during
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
				
		User refUser = new User("testuser3", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser3", "testuser3");
 		UserStory refWorkboard = new UserStory();
 		refWorkboard.setOwner(refUser);
 		
 		// Check the view name
 		Assert.assertEquals("workboardCreation", result.getViewName());
 		
 		// Check the user set in the model
 		Assert.assertTrue(model.get("user").getClass().equals(User.class));
		Assert.assertEquals(refUser, (User)(model.get("user")));
		
		// Check the UserStory set in the ModelAndView's ModelMap
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory resWorkboard = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(refWorkboard.getOwner(), resWorkboard.getOwner());
	}
	
	/**
	 * getUserWorkBoard should succeed & return the active workboard of testuser1
	 */
	@Test
	public void getUserWorkBoardTest() {
		User refUser = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("testuser1", model);
				
		// Makes sure no error occured
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
		
		// Check the return UserStory
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory resWorkboard = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(1, resWorkboard.getId());
		Assert.assertEquals("User 1 WorkBoard", resWorkboard.getName());
		Assert.assertEquals(refUser, resWorkboard.getOwner());
	}

	// TODO: Test uploadfileinWorkBoard
	
	@Test
	public void uploadfileinWorkBoardSuccessTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		MockMultipartFile mockMultipartFileText = new MockMultipartFile("content", "test.txt", "text/plain", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileText, 1, model);
		Assert.assertNull(model.get("errorMessage"));
		
		MockMultipartFile mockMultipartFileXml = new MockMultipartFile("content", "test.xml", "text/xml", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileXml, 1, model);
		Assert.assertNull(model.get("errorMessage"));

		MockMultipartFile mockMultipartFileHtml = new MockMultipartFile("content", "test.html", "text/html", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileHtml, 1, model);
		
		MockMultipartFile mockMultipartFileCsv = new MockMultipartFile("content", "test.csv", "text/csv", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileCsv, 1, model);
		

		MockMultipartFile mockMultipartFileJpeg = new MockMultipartFile("content", "test.jpeg", "image/jpeg", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileJpeg, 1, model);
		Assert.assertNull(model.get("errorMessage"));
		
		MockMultipartFile mockMultipartFileGif = new MockMultipartFile("content", "test.gif", "image/gif", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileGif, 1, model);
		Assert.assertNull(model.get("errorMessage"));
		
		MockMultipartFile mockMultipartFilePng = new MockMultipartFile("content", "test.png", "image/png", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFilePng, 1, model);
		Assert.assertNull(model.get("errorMessage"));
	}
	
	@Test
	public void uploadfileinWorkBoardInvalidTypeTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		MockMultipartFile mockMultipartFileText = new MockMultipartFile("content", "test.css", "text/css", "Hello World".getBytes());
		workboardController.uploadfileinWorkBoard(mockMultipartFileText, 1, model);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(WorkboardController.ERR_INVALID_FILETYPE, model.get("errorMessage"));
	}
	
	// TODO: Test addEngineeringDataToWorkBoard
	
	/**
	 * addCsiroDataToWorkBoardTest should succeed & return a confirmation message
	 */
	@Test
	public void addCsiroDataToWorkBoardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.addCsiroDataToWorkBoard("Temperature", 
				"A1B", "Hotter and Drier", "2030", 1, model);
		
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_CSIRO_DATA_ADDED, model.get("successMessage"));
	}
	
	/**
	 * addCsiroDataToWorkBoardTest should succeed & return a confirmation message
	 */
	@Test
	public void addCsiroDataToWorkBoardBadParametersTest() {
		// UNKNOWN VARIABLE
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.addCsiroDataToWorkBoard("UNKNOWN VARIABLE", 
				"A1B", "Hotter and Drier", "2030", 1, model);
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(CsiroVariableDao.ERR_NO_RESULT, model.get("errorMessage"));
		
		// UNKNOWN SCENARIO
		model = new ExtendedModelMap();
		result = workboardController.addCsiroDataToWorkBoard("Temperature", 
				"UNKNOWN SCENARIO", "Hotter and Drier", "2030", 1, model);
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(ClimateEmissionScenarioDao.ERR_NO_RESULT, model.get("errorMessage"));
		
		// UNKNOWN MODEL
		model = new ExtendedModelMap();
		result = workboardController.addCsiroDataToWorkBoard("Temperature", 
				"A1B", "UNKNOWN MODEL", "2030", 1, model);
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(ClimateParamsDao.ERR_NO_RESULT, model.get("errorMessage"));
	}

	/**
	 * addWorkBoardAlreadyCurrentWorkboardTest : Creation should fail because the user already have a current workboard
	 */
	@Test
	public void addWorkBoardAlreadyCurrentWorkboardTest() {
		User refUser = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory userStory = new UserStory();
		userStory.setName("addWorkBoardTest");
		ModelAndView result = workboardController.addWorkboard(userStory, model);

		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(WorkboardController.ERR_ALREADY_CURRENT_WORKBOARD, model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
	}
	
	/**
	 * addWorkBoardUnknownUserTest : Creation should fail because the user doesn't exist
	 */
	@Test
	public void addWorkBoardUnknownUserTest() {
		User refUser = new User("UNKNOWNUSERNAME", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory userStory = new UserStory();
		userStory.setRegion(new Region("East Coast South"));
		userStory.setName("addWorkBoardTest");
		ModelAndView result = workboardController.addWorkboard(userStory, model);

		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(UserDao.ERR_NO_SUCH_USER, model.get("errorMessage"));
	}
	
	/**
	 * addWorkBoardUnknownUserTest : Creation should succeed and fill the :odelAndView with the new Workboard
	 */
	@Test
	public void addWorkBoardTest() {
		// This user has no Workboard (= active user story) so a new workboard can be created
		User refUser = new User("testuser3", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser3", "testuser3");
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory refUserStory = new UserStory();
		refUserStory.setRegion(new Region("East Coast South"));
		refUserStory.setName("addWorkBoardTest");
		ModelAndView result = workboardController.addWorkboard(refUserStory, model);

		// Check there was no error
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
 		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
 		
 		// Check the user story is set in the model
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory userStory = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(refUserStory.getName(), userStory.getName());
		
		// Check the user story is active and private
		Assert.assertEquals("active", userStory.getMode());
		Assert.assertEquals("private", userStory.getAccess());
	}
	
	/**
	 * deleteWorkboardUnknownIdTest : Delete should fail because the ID provided doesn't correspond to an existing Workboard
	 */
	@Test
	public void deleteWorkboardUnknownIdTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory refWorkboard = new UserStory();
		refWorkboard.setName("deleteWorkboardUnknownIdTest");
		ModelAndView result = workboardController.deleteWorkboard(99999, model);
		
		// Check the error message
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, model.get("errorMessage"));
	}
	
	/**
	 * deleteWorkboardTest : should succeed
	 */
	@Test
	public void deleteWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.deleteWorkboard(1, model);
		
		// Check there was no error
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboardCreation", result.getViewName());
 		
 		// Check the user story is set in the model
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory userStory = (UserStory)(result.getModelMap().get("userstory"));
		
		// Check the user story is active and private
		User refUser = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		Assert.assertEquals(refUser, userStory.getOwner());
	}

	/**
	 * deleteDataElementUnknownIdTest : Delete should fail because the ID provided doesn't correspond to an existing data element
	 */
	@Test
	public void deleteDataElementUnknownIdTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.deleteDataElementFromUserStory(99999, model);
		
		// Check the error message
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(DataElementDao.ERR_NO_SUCH_DATA_ELEMENT, model.get("errorMessage"));
	}
	
	/**
	 * deleteDataElementTest : Delete should succeed
	 */
	@Test
	public void deleteDataElementTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		int id = 7; // ID of the data element to delete
		ModelAndView result = workboardController.deleteDataElementFromUserStory(id, model);
		
		// Check the success message
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		Assert.assertNotNull(model.get("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_DATA_ELEMENT_DELETED, model.get("successMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
	}
}