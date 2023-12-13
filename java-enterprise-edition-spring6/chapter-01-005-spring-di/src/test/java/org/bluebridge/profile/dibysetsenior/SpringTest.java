package org.bluebridge.profile.dibysetsenior;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringTest.class);

    /**
     * set方式注入专题之注入     数组
     */
    @Test
    public void testInjectArrayBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        UserInjectArrayBySetter user = applicationContext.getBean("userInjectArrayBySetter", UserInjectArrayBySetter.class);
        logger.info(user.toString());
    }

    /**
     * set方式注入专题之注入     List集合
     */
    @Test
    public void testInjectListBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        UserInjectListBySetter user = applicationContext.getBean("userInjectListBySetter", UserInjectListBySetter.class);
        logger.info(user.toString());
    }

    /**
     * set方式注入专题之注入     Set集合
     */
    @Test
    public void testInjectSetBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        UserInjectSetBySetter user = applicationContext.getBean("userInjectSetBySetter", UserInjectSetBySetter.class);
        logger.info(user.toString());
    }

    /**
     * set方式注入专题之注入     Map集合
     */
    @Test
    public void testInjectMapBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        UserInjectMapBySetter user = applicationContext.getBean("userInjectMapBySetter", UserInjectMapBySetter.class);
        logger.info(user.toString());
    }

    /**
     * set方式注入专题之注入     Properties
     */
    @Test
    public void testInjectPropertiesBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        UserInjectPropertiesBySetter user = applicationContext.getBean("userInjectPropertiesBySetter", UserInjectPropertiesBySetter.class);
        logger.info(user.toString());
    }

    /**
     * set方式注入专题之注入     NULL和空字符串
     */
    @Test
    public void testInjectNULLAndEmptyStringBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        UserInjectNULLAndEmptyStringBySetter user = applicationContext.getBean("userInjectNULLAndEmptyStringBySetter", UserInjectNULLAndEmptyStringBySetter.class);
        logger.info(user.toString());
        //测试注入的是NULL还是null字符串，如果是注入的是NULL会报空指针异常(放开代码执行会报异常)
        //user.getId().toString();
        //测试注入的是空字符串，如果是注入的是空字符串不会报错
        logger.info("-----------------------");
        logger.info(user.getAge().toString());
        logger.info("-----------------------");
    }

    /**
     * set方式注入专题之注入     特殊符号 < > ' " &
     */
    @Test
    public void testInjectSpecialsymbolsBySetter(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("profile/dibysetsenior/applicationContext-di-profile-set-senior.xml");
        MathInjectSpecialsymbolsBySetter math = applicationContext.getBean("mathInjectSpecialsymbolsBySetter", MathInjectSpecialsymbolsBySetter.class);
        logger.info(math.toString());
    }

}
