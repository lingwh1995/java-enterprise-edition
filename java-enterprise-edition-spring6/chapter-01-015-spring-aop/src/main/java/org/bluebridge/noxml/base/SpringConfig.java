package org.bluebridge.noxml.base;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "org.bluebridge.noxml.base")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig {
}
