package org.bulebridge.ioc.factory.config;

import org.bulebridge.ioc.factory.domain.ColorFactoryBean;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Import(ColorFactoryBean.class)
public class Config {
}
