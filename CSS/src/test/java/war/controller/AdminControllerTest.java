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
public class AdminControllerTest {
	
	@Autowired
	private UserController personController;

	User userForTest;
	
	@Before
	public void prepareData() {
		userForTest = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
	}
	
	/**
	 * TODO: REPLACE WITH ADMIN TESTS
	 */
	@Test
	public void AdminTest() {
		
	}
}