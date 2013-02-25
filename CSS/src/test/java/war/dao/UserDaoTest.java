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
		usersForTest.add(new User("testuser1", "password", "testuser1", "testuser1", User.Privilege.USER));
		usersForTest.add(new User("testuser2", "password", "testuser2", "testuser2", User.Privilege.USER));
		usersForTest.add(new User("testuser3", "password", "testuser3", "testuser3", User.Privilege.USER));
		usersForTest.add(new User("testuser4", "password", "testuser4", "testuser4", User.Privilege.USER));
		usersForTest.add(new User("testadmin1", "password", "testadmin1", "testadmin1", User.Privilege.ADMIN));
		usersForTest.add(new User("testadmin2", "password", "testadmin2", "testadmin2", User.Privilege.ADMIN));
	}

	@Test
	public void userSaveNewUserTest() {
		try {
			User savedUser = userDao.save(new User("login", "password", "firstname", "lastname", User.Privilege.USER));
		
			Assert.assertNotNull(savedUser.getLogin());
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
	
		Assert.assertNotNull(savedUser.getLogin());
		Assert.assertNotNull(savedUser.getPassword());
		Assert.assertNotNull(savedUser.getFirstname());
		Assert.assertNotNull(savedUser.getLastname());
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void userSaveNullLoginTest() throws IllegalArgumentException {
		userDao.save(new User(null, "password", "firstname", "lastname", User.Privilege.USER));
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void userSaveNullPasswordTest() throws IllegalArgumentException {
		userDao.save(new User("login", null, "firstname", "lastname", User.Privilege.USER));
	}
	
	@Test
	public void userFindTest() {
		String login = "testuser2";
		User user = userDao.find(login);

		Assert.assertNotNull("User not found", user);
		
		Assert.assertEquals(user.getLogin(), "testuser2");
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
