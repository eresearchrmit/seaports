package war.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.UserLoginService;

import war.model.UserAuthority;
import war.model.User;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserDaoTest {

	List<User> usersForTest = new ArrayList<User>();
	
	@Autowired
	private UserDao userDao;

	@Before
	public void prepareData() {
		usersForTest.add(new User("testuser1", "password", "enabled", UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1"));
		usersForTest.add(new User("testuser2", "password", "enabled", UserLoginService.ROLE_USER, "email@company.com", "testuser2", "testuser2"));
		usersForTest.add(new User("testuser3", "password", "enabled", UserLoginService.ROLE_USER, "email@company.com", "testuser3", "testuser3"));
		usersForTest.add(new User("testuser4", "password", "enabled", UserLoginService.ROLE_USER, "email@company.com", "testuser4", "testuser4"));
		usersForTest.add(new User("testadmin1", "password", "enabled", UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "testadmin1", "testadmin1"));
		usersForTest.add(new User("testadmin2", "password", "enabled", UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "testadmin2", "testadmin2"));
	}

	@Test
	public void userSaveNewUserTest() {
		try {
			User savedUser = userDao.save(new User("login", "password", "enabled", UserLoginService.ROLE_USER, "email@company.com", "firstname", "lastname"));
		
			Assert.assertNotNull(savedUser.getUsername());
			Assert.assertNotNull(savedUser.getPassword());
			Assert.assertNotNull(savedUser.getFirstname());
			Assert.assertNotNull(savedUser.getLastname());
		}
		catch (IllegalArgumentException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void userSaveExistingUserTest() {
		User savedUser = userDao.save(usersForTest.get(0));
	
		Assert.assertNotNull(savedUser.getUsername());
		Assert.assertNotNull(savedUser.getPassword());
		Assert.assertNotNull(savedUser.getFirstname());
		Assert.assertNotNull(savedUser.getLastname());
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void userSaveNullLoginTest() throws IllegalArgumentException {
		userDao.save(new User(null, "password", "enabled", UserLoginService.ROLE_USER, "email@company.com", "firstname", "lastname"));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void userSaveNullPasswordTest() throws IllegalArgumentException {
		userDao.save(new User("login", null, "enabled", UserLoginService.ROLE_USER, "email@company.com", "firstname", "lastname"));
	}
	
	@Test
	public void userFindTest() {
		String login = "testuser2";
		User user = userDao.find(login);

		Assert.assertNotNull("User not found", user);
		
		Assert.assertEquals(user.getUsername(), "testuser2");
		Assert.assertEquals(user.getPassword(), "password");
		Assert.assertEquals(user.getFirstname(), "testuser2");
		Assert.assertEquals(user.getLastname(), "testuser2");
	}
	
	@Test
	public void getPeopleTest() {
		List<User> people = userDao.getPeople();
		
		Assert.assertNotNull(people);
		Assert.assertEquals(usersForTest.size(), people.size());
	}

}
