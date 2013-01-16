package war.controller;

import java.util.List;

import junit.framework.Assert;

import war.dao.UserDao;
import war.dao.UserStoryDao;
import war.model.DataElement;
import war.model.User;
import war.model.UserStory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserStoryControllerTest {
	
	@Autowired
	private UserStoryController userStoryController;
	
	/**
	 * getUserStoriesList should fail to find a user, but still get the user stories listing view
	 */
	@Test
	public void getUserStoriesListUnknownUserTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = userStoryController.getUserStoriesList("UNKNOWN USER", model);
		
		// Check the error message
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(UserDao.ERR_NO_SUCH_USER, model.get("errorMessage"));
		
		// Check the view name
		Assert.assertEquals("listUS", result.getViewName());
	}
	
	/**
	 * getUserStoriesList should succeed but return an empty list as the user has no story
	 */
	@Test
	public void getUserStoriesListNoStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User refUser = new User("testuser3", "password", "testuser3", "testuser3", User.Privilege.USER);
		ModelAndView result = userStoryController.getUserStoriesList(refUser.getLogin(), model);
		
		// Check there is no error
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
		// Check the view name
		Assert.assertEquals("listUS", result.getViewName());
		
		// Check the UserStory set in the ModelAndView's ModelMap
		Assert.assertTrue(result.getModelMap().get("user").getClass().equals(User.class));
		User resUser = (User)(result.getModelMap().get("user"));
		Assert.assertEquals(refUser, resUser);
		
		Assert.assertEquals("My User Stories", model.get("listingTitle"));
		
		// Check that the result is a list of User Stories and that they are all passive
		@SuppressWarnings("unchecked")
		List<Object> resUserStoriesList = (List<Object>)(result.getModelMap().get("userStoriesList"));
		Assert.assertEquals(0, resUserStoriesList.size());
	}
	
	/**
	 * getUserStoriesList should succeed and return a list of the user's stories
	 */
	@Test
	public void getUserStoriesListTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User refUser = new User("testuser1", "password", "testuser1", "testuser1", User.Privilege.USER);
		ModelAndView result = userStoryController.getUserStoriesList(refUser.getLogin(), model);
		
		// Check there is no error
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
		// Check the view name
		Assert.assertEquals("listUS", result.getViewName());
		
		// Check the User set in the ModelAndView's ModelMap
		Assert.assertTrue(result.getModelMap().get("user").getClass().equals(User.class));
		User resUser = (User)(result.getModelMap().get("user"));
		Assert.assertEquals(refUser, resUser);
		
		Assert.assertEquals("My User Stories", model.get("listingTitle"));
		
		// Check that the result is a list of User Stories and that they are all passive
		@SuppressWarnings("unchecked")
		List<Object> resUserStoriesList = (List<Object>)(result.getModelMap().get("userStoriesList"));
		for (Object obj : resUserStoriesList) {
			if (obj instanceof UserStory) {
				UserStory us = (UserStory)obj;
				Assert.assertEquals("passive", us.getMode());
			}	
			else
				Assert.fail();
		}
	}
	
	/**
	 * getUserStory should fail because there is no User Story with this ID
	 */
	@Test
	public void getUserStoryUnknownIdTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = userStoryController.getUserStory(99999, model);
		
		// Check the error message
		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("passiveUS", result.getViewName());
	}
	
	/**
	 * getUserStory should fail because there is no User Story with this ID
	 */
	@Test
	public void getUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User refUser = new User("testuser1", "password", "testuser1", "testuser1", User.Privilege.USER);
		int id = 1; // ID of a User Story owned by testuser1
		ModelAndView result = userStoryController.getUserStory(id, model);
		
		// Check the error message
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("passiveUS", result.getViewName());
 		
		// Check the User set in the ModelAndView's ModelMap
		Assert.assertTrue(model.get("user").getClass().equals(User.class));
		User resUser = (User)(model.get("user"));
		Assert.assertEquals(refUser, resUser);
		
		// Check that the result is a list of Data Elements and that they all belong to the user story
		@SuppressWarnings("unchecked")
		List<Object> resDataElements = (List<Object>)(result.getModelMap().get("dataelements"));
		for (Object obj : resDataElements) {
			if (obj instanceof DataElement) {
				DataElement de = (DataElement)obj;
				Assert.assertEquals(id, de.getUserStory().getId());
				Assert.assertEquals(refUser.getLogin(), de.getUserStory().getOwner().getLogin());
			}
			else
				Assert.fail();
		}

	}
	
	// TODO: Test includeDataElementToUserStory
}