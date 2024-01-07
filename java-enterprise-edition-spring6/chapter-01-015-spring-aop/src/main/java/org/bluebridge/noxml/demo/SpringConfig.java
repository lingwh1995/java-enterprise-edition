package org.bluebridge.noxml.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "org.bluebridge.noxml.demo")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig {
}
