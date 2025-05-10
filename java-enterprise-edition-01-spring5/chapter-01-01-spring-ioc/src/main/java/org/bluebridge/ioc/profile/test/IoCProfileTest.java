package org.bluebridge.ioc.profile.test;

import org.bluebridge.ioc.profile.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**  
 * @ClassName: Client  
 * @Description: TODO(IOC Client)  
 * @author ronin  
 * @date 2019年3月20日  
 *    
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-base.xml")
public class IoCProfileTest {
	@Test
	public void testIoCProfile(){
		ClassPathXmlApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext-ioc-profile.xml");
		UserController controller = applicationContext.getBean("userController", UserController.class);
		controller.say();
	}
}
