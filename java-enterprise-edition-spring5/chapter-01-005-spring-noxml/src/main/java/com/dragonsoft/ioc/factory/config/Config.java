package com.dragonsoft.ioc.factory.config;

import com.dragonsoft.ioc.factory.domain.ColorFactoryBean;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Import(ColorFactoryBean.class)
public class Config {
}
