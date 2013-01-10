package war.controller;

import junit.framework.Assert;

import war.dao.PersonDao;
import war.model.Person;
import war.model.WorkBoard;

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
		Assert.assertEquals(PersonDao.ERR_NO_SUCH_USER, model.get("errorMessage"));
	}
	
	/**
	 * getUserWorkBoard should succeed & return an new workboard for testuser2
	 */
	@Test
	public void getUserWorkBoardNoActiveWorkboardTest() {
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("testuser2", model);
		
		// Make sure no error happened during
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
				
 		Person refPerson = new Person("testuser2", "password", "testuser2", "testuser2", Person.Privilege.USER);
 		WorkBoard refWorkboard = new WorkBoard();
 		refWorkboard.setPerson(refPerson);
 		
 		// Check the view name
 		Assert.assertEquals("workboard", result.getViewName());
 		
 		// Check the user set in the model
 		Assert.assertTrue(model.get("user").getClass().equals(Person.class));
		Assert.assertEquals(refPerson, (Person)(model.get("user")));
		
		// Check the Workboard set in the ModelAndView's ModelMap
		Assert.assertTrue(result.getModelMap().get("workboard").getClass().equals(WorkBoard.class));
		WorkBoard resWorkboard = (WorkBoard)(result.getModelMap().get("workboard"));
		Assert.assertEquals(refWorkboard.getPerson(), resWorkboard.getPerson());
	}
	
	
	/**
	 * getUserWorkBoard should succeed & return the active workboard of testuser1
	 */
	@Test
	public void getUserWorkBoardTest() {
		Person refPerson = new Person("testuser1", "password", "testuser1", "testuser1", Person.Privilege.USER);
		
		ExtendedModelMap model = new ExtendedModelMap();
		ModelAndView result = workboardController.getUserWorkBoard("testuser1", model);
				
		// Makes sure no error occured
		Assert.assertNotNull(result);
		Assert.assertNull(model.get("errorMessage"));
		
		// Check the return Workboard
		Assert.assertTrue(result.getModelMap().get("workboard").getClass().equals(WorkBoard.class));
		WorkBoard resWorkboard = (WorkBoard)(result.getModelMap().get("workboard"));
		Assert.assertEquals(1, resWorkboard.getWorkBoardID());
		Assert.assertEquals("Workboard 1", resWorkboard.getWorkBoardName());
		Assert.assertEquals(refPerson, resWorkboard.getPerson());
	}
}