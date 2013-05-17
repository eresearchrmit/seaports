package war.controller;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import security.UserLoginService;
import war.dao.UserDao;
import war.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminControllerTest {
	
	@Autowired
	private AdminController adminController;
	
	@Autowired
	private UserDao userDao;

	User loggedInUser;
	User loggedInAdmin;
	SecurityContext securityContextUserLoggedIn;
	SecurityContext securityContextAdminLoggedIn;
	
	@Before
	public void prepareData() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL); // Optional
		
		loggedInUser = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		Authentication userAuth = new ConcreteAuthentication(loggedInUser);
		securityContextUserLoggedIn = new SecurityContextImpl();
		securityContextUserLoggedIn.setAuthentication(userAuth);
		
		loggedInAdmin = new User("testadmin1", "password", true, true, UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "testadmin1", "testadmin1");
		Authentication adminAuth = new ConcreteAuthentication(loggedInAdmin);
		securityContextAdminLoggedIn = new SecurityContextImpl();
		securityContextAdminLoggedIn.setAuthentication(adminAuth);
	}
	
	/**
	 * userList should succeed: a list of users should be retrieved
	 */
	@Test
	public void userListTest() {
		SecurityContextHolder.setContext(securityContextAdminLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = adminController.userList(model);
		
		// Check there is no error
		Assert.assertNull(model.get("errorMessage"));
		// Check the view name and the listing title are set
		Assert.assertEquals("userList", result.getViewName());
		Assert.assertEquals("All users", model.get("listingTitle"));
		
		// Check that a list of users is retrieved
		Assert.assertNotNull(result.getModelMap().get("usersList"));
		Assert.assertTrue(result.getModelMap().get("usersList").getClass().equals(ArrayList.class));
		@SuppressWarnings("unchecked")
		List<User> resUserList = (List<User>)(result.getModelMap().get("usersList"));
		Assert.assertTrue(resUserList.size() > 0);
	}
	
	/**
	 * The user doesn't have administrator rights: an access denied exception should be thrown
	 */
	@Test(expected = AccessDeniedException.class)
	public void userListNoAdminRightsTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		adminController.userList(new ExtendedModelMap());
	}
	
	/**
	 * userEnableDisable should suceed: the user is first disabled, then enable again
	 */
	@Test
	public void userEnableDisableTest() {
		SecurityContextHolder.setContext(securityContextAdminLoggedIn);
		
		User refUser = userDao.find("testuser1");
		Assert.assertTrue(refUser.isEnabled() == true);
		
		
		// Test disable user
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = adminController.userEnableDisable("disable", "testuser1", model);
		refUser = userDao.find("testuser1");
		Assert.assertTrue(refUser.isEnabled() == false);
		
		Assert.assertNull(model.get("errorMessage"));
		Assert.assertEquals("userList", result.getViewName());
		Assert.assertEquals("All users", model.get("listingTitle"));
		Assert.assertNotNull(result.getModelMap().get("usersList"));
		Assert.assertTrue(result.getModelMap().get("usersList").getClass().equals(ArrayList.class));
		@SuppressWarnings("unchecked")
		List<User> resUserList = (List<User>)(result.getModelMap().get("usersList"));
		Assert.assertTrue(resUserList.size() > 0);
		
		
		// Test Re-enable user
		model = new ExtendedModelMap();
		adminController.userEnableDisable("enable", "testuser1", model);
		refUser = userDao.find("testuser1");
		Assert.assertTrue(refUser.isEnabled() == true);
		
		Assert.assertNull(model.get("errorMessage"));
		Assert.assertEquals("userList", result.getViewName());
		Assert.assertEquals("All users", model.get("listingTitle"));
		Assert.assertNotNull(result.getModelMap().get("usersList"));
		Assert.assertTrue(result.getModelMap().get("usersList").getClass().equals(ArrayList.class));
		@SuppressWarnings("unchecked")
		List<User> resUserList2 = (List<User>)(result.getModelMap().get("usersList"));
		Assert.assertTrue(resUserList2.size() > 0);
	}
	
	/**
	 * The user doesn't have administrator rights: an access denied exception should be thrown
	 */
	@Test(expected = AccessDeniedException.class)
	public void userEnableDisableNoAdminRightsTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		adminController.userEnableDisable("enable", "testuser1", new ExtendedModelMap());
	}
	
	/**
	 * userEnableDisable should fail because the user is unknown: 
	 * an error message is set and a listing of the users is retrieved
	 */
	@Test
	public void userEnableDisableUnknownUserTest() {
		SecurityContextHolder.setContext(securityContextAdminLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = adminController.userEnableDisable("disable", "UNKNOWNUSER", model);
		
		// Check an error message is set
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(UserDao.ERR_NO_SUCH_USER, model.get("errorMessage"));
		
		// Check the view name and listing title are set
		Assert.assertEquals("userList", result.getViewName());
		Assert.assertEquals("All users", model.get("listingTitle"));
		
		// Check that a list of users is retrieved
		Assert.assertNotNull(result.getModelMap().get("usersList"));
		Assert.assertTrue(result.getModelMap().get("usersList").getClass().equals(ArrayList.class));
		@SuppressWarnings("unchecked")
		List<User> resUserList = (List<User>)(result.getModelMap().get("usersList"));
		Assert.assertTrue(resUserList.size() > 0);
	}
	
	/**
	 * userEnableDisable should fail because the user to disable is an administrator: 
	 * an error message is set and a listing of the users is retrieved
	 */
	@Test
	public void userEnableDisableAdministratorTest() {
		SecurityContextHolder.setContext(securityContextAdminLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = adminController.userEnableDisable("disable", "testadmin2", model);
		
		// Check an error message is set
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(AdminController.ERR_CANNOT_DISABLE_ADMIN, model.get("errorMessage"));
		
		// Check the view name and listing title are set
		Assert.assertEquals("userList", result.getViewName());
		Assert.assertEquals("All users", model.get("listingTitle"));
		
		// Check that a list of users is retrieved
		Assert.assertNotNull(result.getModelMap().get("usersList"));
		Assert.assertTrue(result.getModelMap().get("usersList").getClass().equals(ArrayList.class));
		@SuppressWarnings("unchecked")
		List<User> resUserList = (List<User>)(result.getModelMap().get("usersList"));
		Assert.assertTrue(resUserList.size() > 0);
	}
}