package war.controller;

import junit.framework.Assert;

import war.dao.UserDao;
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
public class WorkBoardControllerTest {

	@Autowired
	private WorkBoardController workboardController;
	
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
	 * getUserWorkBoard should succeed & return an new UserStory for testuser3
	 */
	@Test
	public void getUserWorkBoardNoActiveWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("testuser3", model);
		
		// Make sure no error happened during
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
				
 		User refUser = new User("testuser3", "password", "testuser3", "testuser3", User.Privilege.USER);
 		UserStory refWorkboard = new UserStory();
 		refWorkboard.setUser(refUser);
 		
 		// Check the view name
 		Assert.assertEquals("createWB", result.getViewName());
 		
 		// Check the user set in the model
 		Assert.assertTrue(model.get("user").getClass().equals(User.class));
		Assert.assertEquals(refUser, (User)(model.get("user")));
		
		// Check the UserStory set in the ModelAndView's ModelMap
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory resWorkboard = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(refWorkboard.getUser(), resWorkboard.getUser());
	}
	
	
	/**
	 * getUserWorkBoard should succeed & return the active workboard of testuser1
	 */
	@Test
	public void getUserWorkBoardTest() {
		User refUser = new User("testuser1", "password", "testuser1", "testuser1", User.Privilege.USER);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("testuser1", model);
				
		// Makes sure no error occured
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("activeWB", result.getViewName());
		
		// Check the return UserStory
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory resWorkboard = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(1, resWorkboard.getId());
		Assert.assertEquals("User 1 WorkBoard", resWorkboard.getName());
		Assert.assertEquals(refUser, resWorkboard.getUser());
	}
}