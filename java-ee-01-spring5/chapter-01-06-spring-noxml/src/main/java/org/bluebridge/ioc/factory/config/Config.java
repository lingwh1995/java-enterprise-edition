package org.bluebridge.ioc.factory.config;

import org.bluebridge.ioc.factory.domain.ColorFactoryBean;
import org.springframework.context.annotation.Import;

/**
 * @author lingwh
 * @desc   Spring循环依赖
 * @date   2019/4/5 14:44
 */
@Import(ColorFactoryBean.class)
public class Config {
}
