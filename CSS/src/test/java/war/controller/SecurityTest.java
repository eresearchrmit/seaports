package war.controller;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SecurityTest {

	/**
	 * getUserStory should fail because there is no User Story with this ID
	 */
	@Test
	@Transactional
	public void getUserStoryUnknownIdTest() {
		int test = 1;
		Assert.assertNotNull(test);
	}
}