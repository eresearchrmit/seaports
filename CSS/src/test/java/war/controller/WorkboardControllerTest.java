package war.controller;

import junit.framework.Assert;

import security.UserLoginService;
import war.dao.AbsVariableDao;
import war.dao.BitreVariableCategoryDao;
import war.dao.ClimateEmissionScenarioDao;
import war.dao.ClimateParamsDao;
import war.dao.CmarDataDao;
import war.dao.CsiroVariableDao;
import war.dao.DataElementDao;
import war.dao.PastDataDao;
import war.dao.SeaportDao;
import war.dao.UserStoryDao;
import war.model.AbsData;
import war.model.AcornSatData;
import war.model.BitreData;
import war.model.DataElement;
import war.model.DataElement.DisplayType;
import war.model.DataElementAbs;
import war.model.DataElementAcornSat;
import war.model.DataElementBitre;
import war.model.DataElementPast;
import war.model.DataElementVulnerability;
import war.model.PastData;
import war.model.Region;
import war.model.Seaport;
import war.model.User;
import war.model.UserStory;
import war.model.WeatherEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

/**
 * This test class holds the unit tests regarding the workboards (user stories) retrieval, privacy, and actions
 * @author Guillaume Prevost
 */
@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WorkboardControllerTest {
	
	@Autowired
	private WorkboardController workboardController;
	
	@Autowired
	private UserStoryDao userStoryDao;
	
	User loggedInUser;
	User loggedInUserNoWB;
	User loggedInAdmin;
	SecurityContext securityContextUserLoggedIn;
	SecurityContext securityContextUserLoggedInNoWB;
	SecurityContext securityContextAdminLoggedIn;
	
	/**
	 * Method executed before starting the unit tests to prepared the data
	 */
	@Before
	public void prepareData() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL); // Optional
		
		loggedInUser = new User("testuser1", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser1", "testuser1");
		Authentication userAuth = new ConcreteAuthentication(loggedInUser);
		securityContextUserLoggedIn = new SecurityContextImpl();
		securityContextUserLoggedIn.setAuthentication(userAuth);
		
		loggedInUserNoWB = new User("testuser3", "password", true, true, UserLoginService.ROLE_USER, "email@company.com", "testuser3", "testuser3");
		Authentication userAuthNoWB = new ConcreteAuthentication(loggedInUserNoWB);
		securityContextUserLoggedInNoWB = new SecurityContextImpl();
		securityContextUserLoggedInNoWB.setAuthentication(userAuthNoWB);
		
		loggedInAdmin = new User("testadmin1", "password", true, true, UserLoginService.ROLE_ADMINISTRATOR, "email@company.com", "testadmin1", "testadmin1");
		Authentication adminAuth = new ConcreteAuthentication(loggedInAdmin);
		securityContextAdminLoggedIn = new SecurityContextImpl();
		securityContextAdminLoggedIn.setAuthentication(adminAuth);
	}
	
	/* --------------------------------------------------------------------- */
	/* ---------------------------- getWorkboard --------------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * getWorkboard should fail because user request the workboard of another user
	 */
	@Test(expected = AccessDeniedException.class)
	public void getWorkboardOfOtherUserTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		workboardController.getWorkboard("testuser2", model);
	}
	
	/**
	 * getWorkboard should fail because user request the workboard of a non-existing user
	 */
	@Test(expected = AccessDeniedException.class)
	public void getWorkboardOfUnknownUserTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		workboardController.getWorkboard("UNKNOWN USER", model);
	}
	
	/**
	 * getWorkboard should succeed & return an new workboard for testuser3
	 */
	@Test
	public void getWorkboardNoActiveWorkboardTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedInNoWB);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getWorkboard("testuser3", model);
		
		// Make sure no error happened during
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
				
		UserStory refWorkboard = new UserStory();
 		refWorkboard.setOwner(this.loggedInUserNoWB);
 		
 		// Check the view name
 		Assert.assertEquals("workboardCreation", result.getViewName());
 		
 		// Check the user set in the model
 		Assert.assertTrue(model.get("user").getClass().equals(User.class));
		Assert.assertEquals(this.loggedInUserNoWB, (User)(model.get("user")));
		
		// Check the UserStory set in the ModelAndView's ModelMap
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory resWorkboard = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(refWorkboard.getOwner(), resWorkboard.getOwner());
	}
	
	/**
	 * getWorkboard should succeed & return the active workboard of testuser1
	 */
	@Test
	public void getWorkboardTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getWorkboard("testuser1", model);
				
		// Makes sure no error occured
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
		
		// Check the return UserStory
		Assert.assertTrue(result.getModelMap().get("userstory").getClass().equals(UserStory.class));
		UserStory resWorkboard = (UserStory)(result.getModelMap().get("userstory"));
		Assert.assertEquals(1, resWorkboard.getId());
		Assert.assertEquals("User 1 Workboard", resWorkboard.getName());
		Assert.assertEquals(this.loggedInUser, resWorkboard.getOwner());
	}
	
	/* --------------------------------------------------------------------- */
	/* ----------------------- addAbsDataToWorkboard ----------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * addAbsDataToWorkboardTest should succeed & return a confirmation message
	 */
	@Test
	public void addAbsDataToWorkboardSuccessTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		int refUserStoryId = 1;
		int refVariableId = 1;
		String refSeaportCode = "AUSYD";
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addAbsDataToWorkboard(refUserStoryId, refVariableId, refSeaportCode, "graph", redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_ABS_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		
		// Check that the last Data Element in the user story matches the parameters passed
		UserStory refUserStory = userStoryDao.find(refUserStoryId);
		DataElementAbs refDataElement = (DataElementAbs)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));
		Assert.assertEquals(DisplayType.GRAPH, refDataElement.getDisplayType());
		Assert.assertEquals(true, refDataElement.getIncluded());
		Assert.assertEquals(0, refDataElement.getPosition());
		Assert.assertTrue(refDataElement.getAbsDataList().size() > 0);
		for (AbsData data : refDataElement.getAbsDataList()) {
			Assert.assertEquals(refSeaportCode, data.getSeaport().getCode());
			Assert.assertEquals(refVariableId, data.getVariable().getId());
		}
	}
	
	/**
	 * addAbsDataToWorkboardBadParametersTest should fail since the parameters passed are incorrect
	 */
	@Test
	public void addAbsDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);

		// UNKNOWN USER STORY
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addAbsDataToWorkboard(9999, 1, "AUSYD", "graph", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN VARIABLE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addAbsDataToWorkboard(1, 9999, "AUSYD", "graph", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(AbsVariableDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN SEAPORT
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addAbsDataToWorkboard(1, 1, "UNKNOW SEAPORT CODE", "graph", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(SeaportDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN DISPLAY TYPE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addAbsDataToWorkboard(1, 1, "AUSYD", "UNKNOW DISPLAY TYPE", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		UserStory refUserStory = userStoryDao.find(1);
		DataElement refDataElement = refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1);
		Assert.assertEquals(DisplayType.UNDEFINED, refDataElement.getDisplayType());
	}

	
	/* --------------------------------------------------------------------- */
	/* ---------------------- addBitreDataToWorkboard ---------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * addBitreDataToWorkboardTest should succeed & return a confirmation message
	 */
	@Test
	public void addBitreDataToWorkboardSuccessTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		int refUserStoryId = 1;
		int refVariableCategoryId = 1;
		String refSeaportCode = "AUSYD";
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addBitreDataToWorkboard(refUserStoryId, refVariableCategoryId, refSeaportCode, "table", redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_BITRE_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		
		// Check that the last Data Element in the user story matches the parameters passed
		UserStory refUserStory = userStoryDao.find(refUserStoryId);
		DataElementBitre refDataElement = (DataElementBitre)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));
		Assert.assertEquals(DisplayType.TABLE, refDataElement.getDisplayType());
		Assert.assertEquals(true, refDataElement.getIncluded());
		Assert.assertEquals(0, refDataElement.getPosition());
		Assert.assertTrue(refDataElement.getBitreDataList().size() > 0);
		for (BitreData data : refDataElement.getBitreDataList()) {
			Assert.assertEquals(refSeaportCode, data.getSeaport().getCode());
			Assert.assertEquals(refVariableCategoryId, data.getVariable().getCategory().getId());
		}
	}
	
	/**
	 * addBitreDataToWorkboardBadParametersTest should fail since the parameters passed are incorrect
	 */
	@Test
	public void addBitreDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);

		// UNKNOWN USER STORY
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addBitreDataToWorkboard(9999, 1, "AUSYD", "graph", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN VARIABLE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addBitreDataToWorkboard(1, 9999, "AUSYD", "graph", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(BitreVariableCategoryDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN SEAPORT
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addBitreDataToWorkboard(1, 1, "UNKNOW SEAPORT CODE", "graph", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(SeaportDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN DISPLAY TYPE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addBitreDataToWorkboard(1, 1, "AUSYD", "UNKNOW DISPLAY TYPE", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		UserStory refUserStory = userStoryDao.find(1);
		DataElement refDataElement = refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1);
		Assert.assertEquals(DisplayType.UNDEFINED, refDataElement.getDisplayType());
	}


	/* --------------------------------------------------------------------- */
	/* ----------------------- uploadfileinWorkboard ----------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * uploadfileinWorkboard should succeed
	 */
	@Test
	public void uploadfileinWorkBoardSuccessTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		MockMultipartFile mockMultipartFileText = new MockMultipartFile("content", "test.txt", "text/plain", "Hello World".getBytes());
		workboardController.uploadfileinWorkboard(mockMultipartFileText, 1, redirectAttributes);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		
		MockMultipartFile mockMultipartFileCsv = new MockMultipartFile("content", "test.csv", "text/csv", "Hello World".getBytes());
		workboardController.uploadfileinWorkboard(mockMultipartFileCsv, 1, redirectAttributes);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		MockMultipartFile mockMultipartFileJpeg = new MockMultipartFile("content", "test.jpeg", "image/jpeg", "Hello World".getBytes());
		workboardController.uploadfileinWorkboard(mockMultipartFileJpeg, 1, redirectAttributes);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
	}
	
	/**
	 * uploadfileinWorkboard should fail since the sent file types are incorrect
	 */
	@Test
	public void uploadfileinWorkBoardInvalidTypeTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		MockMultipartFile mockMultipartFileText = new MockMultipartFile("content", "test.css", "text/css", "Hello World".getBytes());
		workboardController.uploadfileinWorkboard(mockMultipartFileText, 1, redirectAttributes);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertEquals(WorkboardController.ERR_INVALID_FILETYPE, redirectAttributes.getFlashAttributes().get("errorMessage"));
	}
	
	/* --------------------------------------------------------------------- */
	/* ---------------------- addPastDataToWorkboard ----------------------- */
	/* --------------------------------------------------------------------- */
	
	
	/**
	 * addPastDataToWorkboardTest should succeed & return a confirmation message
	 */
	@Test
	public void addPastDataToWorkboardSuccessTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		int refUserStoryId = 1;
		int refPastDataId = 1;
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addPastDataToWorkboard(refUserStoryId, refPastDataId, redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_PAST_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		
		// Check that the last Data Element in the user story matches the parameters passed
		UserStory refUserStory = userStoryDao.find(refUserStoryId);
		DataElementPast refDataElement = (DataElementPast)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));
		Assert.assertEquals(DisplayType.PICTURE, refDataElement.getDisplayType());
		Assert.assertEquals(true, refDataElement.getIncluded());
		Assert.assertEquals(0, refDataElement.getPosition());
		Assert.assertTrue(refDataElement.getPastDataList().size() == 1);
		for (PastData data : refDataElement.getPastDataList()) {
			Assert.assertEquals(refPastDataId, data.getId());
		}
	}
	
	/**
	 * addPastDataToWorkboardBadParametersTest should fail since the parameters passed are incorrect
	 */
	@Test
	public void addPastDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);

		// UNKNOWN USER STORY
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addPastDataToWorkboard(9999, 1, redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN VARIABLE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addPastDataToWorkboard(1, 9999, redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(PastDataDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
	}
	
	/* --------------------------------------------------------------------- */
	/* -------------------- addAcornSatDataToWorkboard --------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * addAcornSatDataToWorkboardTest should succeed & return a confirmation message
	 */
	@Test
	public void addAcornSatDataToWorkboardSuccessTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		int refUserStoryId = 1;
		String refExtreme = "extreme";
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addAcornSatDataToWorkboard(refUserStoryId, refExtreme, redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_ACORNSAT_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		
		// Check that the last Data Element in the user story matches the parameters passed
		UserStory refUserStory = userStoryDao.find(refUserStoryId);
		DataElementAcornSat refDataElement = (DataElementAcornSat)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));
		Assert.assertEquals(DisplayType.TABLE, refDataElement.getDisplayType());
		Assert.assertEquals(true, refDataElement.getIncluded());
		Assert.assertEquals(0, refDataElement.getPosition());
		for (AcornSatData data : refDataElement.getAcornSatDataList()) {
			Assert.assertTrue(data.getExtreme());
			Assert.assertEquals(refUserStory.getSeaport().getRegion().getId(), data.getAcornSatStation().getRegion().getId());
		}
	}
	
	/**
	 * addAcornSatDataToWorkboardBadParametersTest should fail since the parameters passed are incorrect
	 */
	@Test
	public void addAcornSatDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);

		// UNKNOWN USER STORY
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addAcornSatDataToWorkboard(9999, "extreme", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN EXTREME VALUE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addAcornSatDataToWorkboard(1, "UNKNOWN EXTREME VALUE", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		//Assert.assertEquals(PastDataDao.ERR_NO_RESULT, model.get("errorMessage"));
	}
		
	
	/* --------------------------------------------------------------------- */
	/* ---------------------- addCsiroDataToWorkboard ---------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * addCsiroDataToWorkBoardTest should succeed & return a confirmation message
	 */
	@Test
	public void addCsiroDataToWorkboardTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addCsiroDataToWorkboard(1, 
				"Temperature", "A1B", "Hotter & Drier", "2030", "on", redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_CSIRO_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
	}
	
	/**
	 * addCsiroDataToWorkBoardTest should succeed & return a confirmation message
	 */
	@Test
	public void addCsiroDataToWorkboardWithoutPictureTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addCsiroDataToWorkboard(1, 
				"Temperature", "A1B", "Hotter & Drier", "2030", null, redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertEquals(WorkboardController.MSG_CSIRO_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
	}
	
	/**
	 * addCsiroDataToWorkBoardTest should succeed & return a confirmation message
	 */
	@Test
	public void addCsiroDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		// UNKNOWN VARIABLE
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addCsiroDataToWorkboard(1, "UNKNOWN VARIABLE", 
				"A1B", "Hotter & Drier", "2030", "", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(CsiroVariableDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN SCENARIO
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addCsiroDataToWorkboard(1, "Temperature", 
				"UNKNOWN SCENARIO", "Hotter and Drier", "2030", "", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(ClimateEmissionScenarioDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN MODEL
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addCsiroDataToWorkboard(1, "Temperature", 
				"A1B", "UNKNOWN MODEL", "2030", "", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(ClimateParamsDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
	}

	
	
	/* --------------------------------------------------------------------- */
	/* ---------------------- addCmarDataToWorkboard ----------------------- */
	/* --------------------------------------------------------------------- */
	
	
	/**
	 * addCmarDataToWorkboard should succeed & return a confirmation message
	 */
	@Test
	public void addCmarDataToWorkboardTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addCmarDataToWorkboard(1, "2030", "on", redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_CMAR_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
	}
	
	/**
	 * addCmarDataToWorkboard should succeed & return a confirmation message
	 */
	@Test
	public void addCmarDataToWorkboardWithoutPicturesTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addCmarDataToWorkboard(1, "2030", null, redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_CMAR_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
	}
	
	
	/**
	 * addCmarDataToWorkboard fail since the parameters passed are incorrect
	 */
	@Test
	public void addCmarDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		// UNKNOWN USER STORY
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addCmarDataToWorkboard(99999, "2030", "on", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, redirectAttributes.getFlashAttributes().get("warningMessage"));
		
		// UNKNOWN YEAR
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addCmarDataToWorkboard(1, "9999", "on", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("warningMessage"));
		Assert.assertEquals(CmarDataDao.ERR_NO_RESULT, redirectAttributes.getFlashAttributes().get("warningMessage"));
	}

	/* --------------------------------------------------------------------- */
	/* ---------------- addEngineeringModelDataToWorkboard ----------------- */
	/* --------------------------------------------------------------------- */
	
	// TODO: Test addEngineeringModelDataToWorkboard
	
	
	/* --------------------------------------------------------------------- */
	/* ------------ addVulnerabilityAssessmentDataToWorkboard -------------- */
	/* --------------------------------------------------------------------- */
	
	
	/**
	 * addVulnerabilityAssessmentDataToWorkboard should succeed & return a confirmation message
	 */
	@Test
	public void addVulnerabilityAssessmentDataToWorkboardSuccessTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		int refUserStoryId = 1;
		String refType = "Heavy rain";
		String refImpact = "Impact of the event";
		String refConsequences = "Other consequences";
		String refChanges = "Changes Implemented";
		
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addVulnerabilityAssessmentToWorkboard(refUserStoryId, refType, "2008", "direct", refImpact, "2", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", refConsequences, "adequate", refChanges, redirectAttributes);
		
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));

		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_VULNERABILITY_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		
		UserStory refUserStory = userStoryDao.find(refUserStoryId);
		DataElementVulnerability refDataElement = (DataElementVulnerability)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));

		
		Assert.assertEquals(refType, refDataElement.getWeatherEvent().getType());
		Assert.assertEquals(2008, (int)(refDataElement.getWeatherEvent().getYear()));
		Assert.assertTrue(refDataElement.getWeatherEvent().getDirect());
		Assert.assertEquals(refImpact, refDataElement.getWeatherEvent().getImpact());
		Assert.assertEquals(refChanges, refDataElement.getWeatherEvent().getChanges());
		Assert.assertEquals("2,4,1,1,0,5,2,3,4,2,1,5", refDataElement.getWeatherEvent().getConsequencesRating());
	}
	
	/**
	 * addVulnerabilityAssessmentDataToWorkboard should fail since the parameters passed are incorrect
	 */
	@Test
	public void addVulnerabilityAssessmentDataToWorkboardBadParametersTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		// UNKNOWN USER STORY
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.addVulnerabilityAssessmentToWorkboard(9999, "Heavy rain", "2006", "direct", "Impact of the event", "2", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "adequate", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertEquals(UserStoryDao.ERR_NO_SUCH_USERSTORY, redirectAttributes.getFlashAttributes().get("errorMessage"));
		
		// UNKNOWN WEATHER EVENT TYPE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addVulnerabilityAssessmentToWorkboard(1, "UNKNOW WEATHER EVENT TYPE", "2008", "direct", "Impact of the event", "2", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "adequate", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertEquals(WeatherEvent.ERR_INVALID_WEATHER_EVENT_TYPE, redirectAttributes.getFlashAttributes().get("errorMessage"));
		
		// UNKNOWN YEAR
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addVulnerabilityAssessmentToWorkboard(1, "Heavy rain", "UNKNOWN YEAR", "direct", "Impact of the event", "2", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "adequate", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertEquals("For input string: \"UNKNOWN YEAR\"", redirectAttributes.getFlashAttributes().get("errorMessage"));
		
		// UNKNOWN DIRECT PARAM
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addVulnerabilityAssessmentToWorkboard(1, "Heavy rain", "2005", "UNKNOWN DIRECT PARAM", "Impact of the event", "2", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "adequate", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_VULNERABILITY_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		UserStory refUserStory = userStoryDao.find(1);
		DataElementVulnerability refDataElement = (DataElementVulnerability)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));
		Assert.assertFalse(refDataElement.getWeatherEvent().getDirect());
		
		// UNKNOWN ADEQUATE PARAM
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addVulnerabilityAssessmentToWorkboard(1, "Heavy rain", "2005", "direct", "Impact of the event", "2", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "UNKNOWN ADEQUATE PARAM", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_VULNERABILITY_DATA_ADDED, redirectAttributes.getFlashAttributes().get("successMessage"));
		refUserStory = userStoryDao.find(1);
		refDataElement = (DataElementVulnerability)(refUserStory.getDataElements().get(refUserStory.getDataElements().size() - 1));
		Assert.assertFalse(refDataElement.getWeatherEvent().getResponseAdequate());
		
		// UNKNOWN CONSEQUENCE RATING
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addVulnerabilityAssessmentToWorkboard(1, "Heavy rain", "2005", "direct", "Impact of the event", "UNKNOWN RATING", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "UNKNOWN ADEQUATE PARAM", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertEquals("For input string: \"UNKNOWN RATING\"", redirectAttributes.getFlashAttributes().get("errorMessage"));
		
		// CONSEQUENCE RATING OUT OF RANGE
		redirectAttributes = new RedirectAttributesModelMap();
		result = workboardController.addVulnerabilityAssessmentToWorkboard(1, "Heavy rain", "2005", "direct", "Impact of the event", "9999", "4", "1", "1", "0", "5", "2", "3", "4", "2", "1", "5", "Other consequences", "UNKNOWN ADEQUATE PARAM", "Changes implemented", redirectAttributes);
		Assert.assertNotNull(result);
		Assert.assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
		Assert.assertEquals(WeatherEvent.ERR_CONSEQUENCE_RATING_OUT_OF_RANGE, redirectAttributes.getFlashAttributes().get("errorMessage"));
	}

	
	/* --------------------------------------------------------------------- */
	/* --------------------------- addWorkboard ---------------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * addWorkBoardAlreadyCurrentWorkboardTest : Creation should fail because the region is not defined
	 */
	@Test
	public void addWorkboardRegionUndefinedTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedInNoWB);
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory userStory = new UserStory();
		userStory.setName("addWorkBoardTestWithoutRegion");
		userStory.setPurpose("Activity description");
		ModelAndView result = workboardController.createWorkboard(userStory, model);

		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(WorkboardController.ERR_REGION_NOT_DEFINED, model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboardCreation", result.getViewName());
	}
	
	/**
	 * addWorkBoardAlreadyCurrentWorkboardTest : Creation should fail because the region is not defined
	 */
	@Test
	public void addWorkboardPurposeUndefinedTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedInNoWB);
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory userStory = new UserStory();
		userStory.setName("addWorkBoardTestWithoutPurpose");
		userStory.setSeaport(new Seaport("AUSYD", "Sydney Harbour", new Region("East Coast South", "")));
		ModelAndView result = workboardController.createWorkboard(userStory, model);

		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("errorMessage"));
		Assert.assertEquals(WorkboardController.ERR_PURPOSE_NOT_DEFINED, model.get("errorMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboardCreation", result.getViewName());
	}
	
	/**
	 * addWorkBoardAlreadyCurrentWorkboardTest : Creation should fail because the user already have a current workboard
	 */
	@Test
	public void addWorkboardAlreadyCurrentWorkboardTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory userStory = new UserStory();
		userStory.setName("addWorkBoardTest");
		userStory.setSeaport(new Seaport("AUSYD", "Sydney Harbour", new Region("East Coast South", "")));
		userStory.setPurpose("Activity description");
		ModelAndView result = workboardController.createWorkboard(userStory, model);

		Assert.assertNotNull(result);
		Assert.assertNotNull(model.get("warningMessage"));
		Assert.assertEquals(WorkboardController.ERR_ALREADY_CURRENT_WORKBOARD, model.get("warningMessage"));
		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
	}
		
	/**
	 * addWorkBoardUnknownUserTest : Creation should succeed and fill the :odelAndView with the new Workboard
	 */
	@Test
	public void addWorkboardTest() {
		// This user has no Workboard (= active user story) so a new workboard can be created
		SecurityContextHolder.setContext(securityContextUserLoggedInNoWB);
		
		ExtendedModelMap model = new ExtendedModelMap();
		UserStory refUserStory = new UserStory();
		refUserStory.setSeaport(new Seaport("AUSYD", "Sydney Harbour", new Region("East Coast South", "")));
		refUserStory.setPurpose("Activity description");
		refUserStory.setName("addWorkBoardTest");
		ModelAndView result = workboardController.createWorkboard(refUserStory, model);

		// Check there was no error
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
 		
 		// Check the view name
 		Assert.assertEquals("workboardCreated", result.getViewName());
	}
	
	
	
	/* --------------------------------------------------------------------- */
	/* -------------------------- deleteWorkboard -------------------------- */
	/* --------------------------------------------------------------------- */
	
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
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
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
		Assert.assertEquals(this.loggedInUser, userStory.getOwner());
	}


	
	/* --------------------------------------------------------------------- */
	/* ------------------------- deleteDataElement ------------------------- */
	/* --------------------------------------------------------------------- */
	
	/**
	 * deleteDataElementUnknownIdTest : Delete should fail because the ID provided doesn't belong to the user
	 */
	@Test(expected = AccessDeniedException.class)
	public void deleteDataElementNotAuthTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedInNoWB);
		
		// Try to delete a data element which the user does not own
		ExtendedModelMap model = new ExtendedModelMap();
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		workboardController.deleteDataElement(1, redirectAttributes, model);
	}
	
	/**
	 * deleteDataElementUnknownIdTest : Delete should fail because the ID provided doesn't correspond to an existing data element
	 */
	@Test
	public void deleteDataElementUnknownIdTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		String result = workboardController.deleteDataElement(99999, redirectAttributes, model);
		
		// Check the error message
		Assert.assertNotNull(result);
		Assert.assertTrue(redirectAttributes.getFlashAttributes().containsKey("errorMessage"));
		Assert.assertEquals(DataElementDao.ERR_NO_SUCH_DATA_ELEMENT, redirectAttributes.getFlashAttributes().get("errorMessage"));
	}
	
	
	/**
	 * deleteDataElementTest : Delete should fail since because the data element belongs to a user story (i.e. not workboard)
	 */
	@Test
	public void deleteDataElementFromUserStoryTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		
		ExtendedModelMap model = new ExtendedModelMap();
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		
		int id = 4; // ID of the data element to delete
		String result = workboardController.deleteDataElement(id, redirectAttributes, model);
		
		// Check the success message
		Assert.assertNotNull(result);
		Assert.assertFalse(redirectAttributes.getFlashAttributes().containsKey("successMessage"));
		Assert.assertTrue(redirectAttributes.getFlashAttributes().containsKey("errorMessage"));
		Assert.assertEquals(WorkboardController.ERR_DELETE_DATA_ELEMENT, redirectAttributes.getFlashAttributes().get("errorMessage"));
	}
	
	/**
	 * deleteDataElementTest : Delete should succeed
	 */
	@Test
	public void deleteDataElementTest() {
		SecurityContextHolder.setContext(securityContextUserLoggedIn);
		ExtendedModelMap model = new ExtendedModelMap();
		RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
		
		int id = 3; // ID of the data element to delete
		String result = workboardController.deleteDataElement(id, redirectAttributes, model);
				
		// Check the success message
		Assert.assertNotNull(result);
		Assert.assertFalse(redirectAttributes.getFlashAttributes().containsKey("errorMessage"));
		Assert.assertTrue(redirectAttributes.getFlashAttributes().containsKey("successMessage"));
		Assert.assertEquals(WorkboardController.MSG_DATA_ELEMENT_DELETED, redirectAttributes.getFlashAttributes().get("successMessage"));
	}
}