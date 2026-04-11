package org.bluebridge.ioc.profile.test;

import org.bluebridge.ioc.profile.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/19 14:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-ioc-profile.xml")
public class IoCProfileTest {

	@Test
	public void testIoCProfile(){
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext-ioc-profile.xml");
		UserController controller = applicationContext.getBean("userController", UserController.class);
		controller.say();
	}

}
