package org.bluebridge.common.util;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author lingwh
 * @desc Spring 工具类
 *       核心机制：ApplicationContextAware
 *       Spring 容器在启动时，会自动扫描所有实现了 ApplicationContextAware 接口的类。
 *       动作： 一旦扫描到，Spring 就会自动调用 setApplicationContext 方法。
 *       结果： 它把整个 Spring 容器的引用（context）传给了你的静态变量 applicationContext。
 *       意义： 这样你就在这个类里拿到了一把打开 Spring 仓库的"万能钥匙"。
 * @date 2026/1/10 10:06
 */
@Component
public class SpringUtils implements  ApplicationContextAware, EnvironmentAware, BeanNameAware {

    /**
     * Spring 容器
     */
    private static ApplicationContext applicationContext;

    /**
     * 当前环境
     */
    private static Environment environment;

    /**
     * 当前 Bean 名称
     */
    private String beanName;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 获取当前类的代理对象
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取当前配置文件的属性值
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取当前激活的 Profile (dev/test/prod)
     * @return
     */
    public static String getActiveProfile() {
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 检查当前容器中是否存在指定名称的 Bean
     * @param beanName
     * @return
     */
    public static boolean containsBean(String beanName) {
        return applicationContext.containsBean(beanName);
    }

    /**
     * 判断当前是否为开发环境 (dev)
     * @return
     */
    public static boolean isDevMode() {
        String[] activeProfiles = environment.getActiveProfiles();
        return Arrays.asList(activeProfiles).contains("dev");
    }

    /**
     * 发布自定义事件
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

}