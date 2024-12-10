package com.dragonsoft.ioc.beanregistrar.config;

import com.dragonsoft.ioc.beanregistrar.domain.Red;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Import({Red.class,MyImportBeanDefinitionRegistrar.class})
public class Config {
}
