package org.bluebridge.ioc.beanregistrar.test;

import org.bluebridge.ioc.beanregistrar.config.Config;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/9 14:32
 */
public class ImportBeanDefinitionRegistrarTest {

    @Test
    public void testImportBeanDefinitionRegistrar() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }
}
