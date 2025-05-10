package org.bluebridge.ioc.factory.config;

import org.bluebridge.ioc.factory.domain.ColorFactoryBean;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Import(ColorFactoryBean.class)
public class Config {
}
