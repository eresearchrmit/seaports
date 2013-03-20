package war.controller;

import junit.framework.Assert;

import security.UserLoginService;
import war.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserControllerTest {
	
	@Autowired
	private UserController userController;

	User userForTest;
	
	@Before
	public void prepareData() {
		userForTest = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com.au", "testuser1", "testuser1");
	}
	
	/**
	 * Registration should succeed
	 */
	@Test
	public void registerNewUserSuccessTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String result = userController.registerNewUser(userForTest, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("login", result);
	}
	
	/**
	 * Registration should fail because the login is null
	 */
	@Test
	public void registerNewUserNullLoginTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setUsername(null);
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_LOGIN, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the login is empty
	 */
	@Test
	public void registerNewUserEmptyLoginTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setUsername("");
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_LOGIN, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the password is null
	 */
	@Test
	public void registerNewUserNullPasswordTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setPassword(null);
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_PASSWORD, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the password is less than 5 characters
	 */
	@Test
	public void registerNewUserEmptyPasswordTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setPassword("abcd");
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_PASSWORD, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the first name is null
	 */
	@Test
	public void registerNewUserNullFirstnameTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setFirstname(null);
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_FIRSTNAME, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the first name is empty
	 */
	@Test
	public void registerNewUserEmptyFirstnameTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setFirstname("");
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_FIRSTNAME, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the last name is null
	 */
	@Test
	public void registerNewUserNullLastNameTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setLastname(null);
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_LASTNAME, model.get("errorMessage"));
	}
	
	/**
	 * Registration should fail because the last name is empty
	 */
	@Test
	public void registerNewUserEmptyLastnameTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		User user = userForTest;
		user.setLastname("");
		String result = userController.registerNewUser(user, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("register", result);
		
		Assert.assertEquals(UserController.ERR_SIGNUP_INVALID_LASTNAME, model.get("errorMessage"));
	}
	
	/**
	 * Login should succeed
	 */
	@Test
	public void loginValidationSuccessTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String result = userController.loginValidation(userForTest, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("redirect:/auth/workboard?user=" + userForTest.getUsername(), result);
	}
	
	/**
	 * Login should fail because Person is null: in the model, 'controllerMessage' should be filled with ERR_MISSING_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationNullPersonTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String result = userController.loginValidation(null, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_MISSING_LOGIN_PASSWORD, model.get("errorMessage"));
	}
	
	/**
	 * Login should fail because login is missing: in the model, 'controllerMessage' should be filled with ERR_MISSING_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationLoginMissingTest() {
		User p = new User();
		// Login not set ON PURPOSE
		p.setPassword("password");
		p.setRoles(UserLoginService.ROLE_USER);
		
		ExtendedModelMap model = new ExtendedModelMap();
		String result = userController.loginValidation(p, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_MISSING_LOGIN_PASSWORD, model.get("errorMessage"));
	}
	
	/**
	 * Login should fail because password is missing: in the model, 'controllerMessage' should be filled with ERR_MISSING_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationPasswordMissingTest() {
		User p = new User();
		p.setUsername("testuser1");
		// Password not set ON PURPOSE
		p.setRoles(UserLoginService.ROLE_USER);
		
		ExtendedModelMap model = new ExtendedModelMap();
		String result = userController.loginValidation(p, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_MISSING_LOGIN_PASSWORD, model.get("errorMessage"));
	}
	
	/**
	 * Login should fail because password is invalid: in the model, 'controllerMessage' should be filled with ERR_BAD_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationPasswordInvalidTest() {
		User u= new User("testuser1", "WRONGPASSWORD", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		ExtendedModelMap model = new ExtendedModelMap();
		String result = userController.loginValidation(u, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_BAD_LOGIN_PASSWORD, model.get("errorMessage"));
	}
}