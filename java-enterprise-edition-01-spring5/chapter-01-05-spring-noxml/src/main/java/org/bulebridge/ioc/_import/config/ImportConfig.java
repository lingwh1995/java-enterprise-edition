package org.bulebridge.ioc._import.config;

import org.bulebridge.ioc._import.domain.Color;
import org.bulebridge.ioc._import.domain.Red;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Configuration
//导入一个类
//@Import(Color.class)
@Import({Color.class, Red.class})
public class ImportConfig {

}
