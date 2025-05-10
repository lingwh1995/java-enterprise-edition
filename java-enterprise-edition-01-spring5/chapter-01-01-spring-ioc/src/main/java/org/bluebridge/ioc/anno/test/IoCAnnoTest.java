package org.bluebridge.ioc.anno.test;

import org.bluebridge.ioc.anno.controller.CatController;
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
public class IoCAnnoTest {
	@Test
	public void testIoCAnno(){
		ClassPathXmlApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext-ioc-anno.xml");
		CatController catController = applicationContext.getBean("catController", CatController.class);
		catController.eat();
	}
}
