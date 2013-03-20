package war.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;

import war.dao.UserStoryDao;
import war.model.Region;
import war.model.UserStory;

/**
 * This test class holds tests against all the protected controllers methods performed by an anonymous user
 * The tests are expecting to get an "Access Denied" response.
 * @author Guillaume Prevost
 * @since 19th Mar. 2013
 */
@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AnonymousControllerTest {
	
	@Autowired
	private WorkboardController workboardController;

	@Autowired
	private UserStoryController userStoryController;
	
	@Autowired
	private AdminController adminController;
	
	@Autowired
	private  UserStoryDao userStoryDao;
	
	@Before
	public void prepareData() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/* --------------------------------------------------------------------- */
	/* ----------------------------- Workboard ----------------------------- */
	/* --------------------------------------------------------------------- */
	
	@Test
	public void getWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.getWorkboard(model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void getWorkboardUserTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.getWorkboard("testuser1", model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void uploadfileinWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		MockMultipartFile mockMultipartFileText = new MockMultipartFile("content", "test.css", "text/css", "Hello World".getBytes());
		workboardController.uploadfileinWorkboard(mockMultipartFileText, 1, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void addEngineeringDataToWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		MockMultipartFile mockMultipartFileText = new MockMultipartFile("content", "test.css", "text/css", "Hello World".getBytes());
		workboardController.addEngineeringDataToWorkboard(mockMultipartFileText, "upload", "Crack propagation time", "Chloride", 1, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void addCsiroDataToWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.addCsiroDataToWorkboard(1, "Temperature", "A1B", "Hotter and Drier", "2030", "", model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void addCmarDataToWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.addCmarDataToWorkboard(1, "2030", "on", model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void addWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory userStory = new UserStory();
		userStory.setName("addWorkBoardTest");
		userStory.setRegion(new Region("East Coast South"));
		workboardController.addWorkboard(userStory, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void deleteWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.deleteWorkboard(1, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void deleteDataElementFromUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.deleteDataElementFromUserStory(1, model);
	}
	
	
	
	/* --------------------------------------------------------------------- */
	/* ----------------------------- User Story ---------------------------- */
	/* --------------------------------------------------------------------- */
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void getUserStoriesListTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.getUserStoriesList("testuser1", model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void getUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.getUserStory(2, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void getUserStoryViewTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.getUserStoryView(2, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void changeUserStoryPrivacyTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.changeUserStoryPrivacy(2, true, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void createUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.createUserStory(2, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void saveUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory refUserstory = userStoryDao.find(2);
		String[] updatedTexts = new String[] {"Updated Text 1", "Updated Text 2"};
		
		userStoryController.saveUserStory(updatedTexts, refUserstory, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void deleteUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.deleteUserStory(2, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void addTextToUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.addTextToUserStory(2, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void removeTextFromUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.removeTextFromUserStory(2, model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void includeDataElementToUserStoryTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		userStoryController.includeDataElementToUserStory(4, model);
	}

	/* --------------------------------------------------------------------- */
	/* --------------------------- Administration -------------------------- */
	/* --------------------------------------------------------------------- */
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void userListTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		adminController.userList(model);
	}
	
	@Test
	@ExpectedException(AccessDeniedException.class)
	public void userEnableDisableTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		adminController.userEnableDisable("enable", "testuser1", model);
	}
}