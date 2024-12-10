package com.dragonsoft.ioc._import.config;

import com.dragonsoft.ioc._import.domain.Color;
import com.dragonsoft.ioc._import.domain.Red;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author ronin
 */
@Configuration
//导入一个类
//@Import(Color.class)
@Import({Color.class,Red.class})
public class ImportConfig {

}
