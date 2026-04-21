package org.bluebridge.ioc._import.config;

import org.bluebridge.ioc._import.domain.Color;
import org.bluebridge.ioc._import.domain.Red;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/13 14:25
 */
@Configuration
//导入一个类
//@Import(Color.class)
@Import({Color.class, Red.class})
public class ImportConfig {

}
