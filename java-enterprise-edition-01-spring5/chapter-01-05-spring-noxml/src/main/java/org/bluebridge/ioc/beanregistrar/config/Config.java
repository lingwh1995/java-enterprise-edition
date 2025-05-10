package org.bluebridge.ioc.beanregistrar.config;

import org.bluebridge.ioc.beanregistrar.domain.Red;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Import({Red.class,MyImportBeanDefinitionRegistrar.class})
public class Config {
}
