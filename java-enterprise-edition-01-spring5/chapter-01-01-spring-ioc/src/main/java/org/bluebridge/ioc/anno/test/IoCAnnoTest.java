package org.bluebridge.ioc.anno.test;

import org.bluebridge.ioc.anno.controller.CatController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/20 9:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-ioc-anno.xml")
public class IoCAnnoTest {

	@Test
	public void testIoCAnno(){
		ApplicationContext applicationContext = new
				ClassPathXmlApplicationContext("applicationContext-ioc-anno.xml");
		CatController catController = applicationContext.getBean("catController", CatController.class);
		catController.eat();
	}

}
