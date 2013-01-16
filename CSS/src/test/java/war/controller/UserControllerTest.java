package war.controller;

import junit.framework.Assert;

import war.model.User;

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
	private UserController personController;

	/**
	 * Login should succeed
	 */
	@Test
	public void loginValidationSuccessTest() {
		User p = new User("testuser1", "password", "testuser1", "testuser1", User.Privilege.USER);
		ExtendedModelMap model = new ExtendedModelMap();
		String result = personController.loginValidation(p, model);
		
		Assert.assertNotNull(result);		
		Assert.assertEquals("redirect:/spring/workboard?user=" + p.getLogin(), result);
	}
	
	/**
	 * Login should fail because Person is null: in the model, 'controllerMessage' should be filled with ERR_MISSING_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationNullPersonTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		String result = personController.loginValidation(null, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_MISSING_LOGIN_PASSWORD, model.get("controllerMessage"));
	}
	
	/**
	 * Login should fail because login is missing: in the model, 'controllerMessage' should be filled with ERR_MISSING_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationLoginMissingTest() {
		User p = new User();
		// Login not set ON PURPOSE
		p.setPassword("password");
		p.setRole(User.Privilege.USER);
		
		ExtendedModelMap model = new ExtendedModelMap();
		String result = personController.loginValidation(p, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_MISSING_LOGIN_PASSWORD, model.get("controllerMessage"));
	}
	
	/**
	 * Login should fail because password is missing: in the model, 'controllerMessage' should be filled with ERR_MISSING_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationPasswordMissingTest() {
		User p = new User();
		p.setLogin("testuser1");
		// Password not set ON PURPOSE
		p.setRole(User.Privilege.USER);
		
		ExtendedModelMap model = new ExtendedModelMap();
		String result = personController.loginValidation(p, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_MISSING_LOGIN_PASSWORD, model.get("controllerMessage"));
	}
	
	/**
	 * Login should fail because password is invalid: in the model, 'controllerMessage' should be filled with ERR_BAD_LOGIN_PASSWORD
	 */
	@Test
	public void loginValidationPasswordInvalidTest() {
		User p = new User("testuser1", "WRONGPASSWORD", "testuser1", "testuser1", User.Privilege.USER);
		ExtendedModelMap model = new ExtendedModelMap();
		String result = personController.loginValidation(p, model);
		
		Assert.assertNotNull(result);
		Assert.assertEquals("login", result);
		
		Assert.assertEquals(UserController.ERR_BAD_LOGIN_PASSWORD, model.get("controllerMessage"));
	}
}