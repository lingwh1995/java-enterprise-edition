/**  
 * @Title: Client.java  
 * @Package com.dragonsoft.spring.ioc.client  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author ronin  
 * @date 2019年3月20日  
 * @version V1.0  
 */ 
package com.dragonsoft.ioc.profile.client;

import com.dragonsoft.ioc.profile.controller.UserController;
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
public class IocProfileTest {
	@Test
	public void fun1(){
		ClassPathXmlApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext-ioc-profile.xml");
		UserController controller = applicationContext.getBean("userController", UserController.class);
		controller.say();
	}
}
