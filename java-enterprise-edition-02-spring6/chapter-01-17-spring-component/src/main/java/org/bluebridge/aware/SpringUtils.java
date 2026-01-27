package org.bluebridge.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author lingwh
 * @desc Spring 工具类
 *       以 ApplicationContextAware 为例说明其工作流程
 *       Spring 容器在启动时，会自动扫描所有实现了 ApplicationContextAware 接口的类。
 *       动作： 一旦扫描到，Spring 就会自动调用 setApplicationContext 方法。
 *       结果： 它把整个 Spring 容器的引用（context）传给了你的静态变量 applicationContext。
 *       意义： 这样你就在这个类里拿到了一把打开 Spring 仓库的"万能钥匙"。
 * @date 2026/1/10 11:47
 */
public class SpringUtils implements ApplicationContextAware, EnvironmentAware, BeanNameAware {

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

    /**
     * 感知 BeanName：Spring 告诉你的 Bean 在容器里叫什么
     * @param beanName
     */
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * 感知 Environment：获取 application.yml 中的配置
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        SpringUtils.environment = environment;
    }

    /**
     * 感知 ApplicationContext：获取整个 Spring 容器
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 手动从容器中获取指定的 Bean
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取容器中所有 Bean 的名称
     * @return
     */
    public static String[] getBeanDefinitionNames() {
        return applicationContext.getBeanDefinitionNames();
    }

    /**
     * 获取容器中 Bean 的数量
     * @return
     */
    public static int getBeanDefinitionCount() {
        return applicationContext.getBeanDefinitionCount();
    }

    /**
     * 手动从容器中获取指定的 Bean
     * @param beanName
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
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
     * 获取配置属性
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    /**
     * 发布事件
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

}
