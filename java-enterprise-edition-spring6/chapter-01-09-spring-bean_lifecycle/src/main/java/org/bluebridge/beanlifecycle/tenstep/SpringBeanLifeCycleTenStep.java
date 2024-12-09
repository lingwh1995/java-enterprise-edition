package org.bluebridge.beanlifecycle.tenstep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * Spring中Bean的生命周期七步：
 *  第一步：实例化Bean
 *  第二步：Bean属性赋值
 *  第三步：执行Bean后处理器的before方法
 *  第四步：初始化Bean
 *  第五步：执行Bean后处理器的after方法
 *  第六步：使用Bean
 *  第七步：销毁Bean
 */
public class SpringBeanLifeCycleTenStep implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(SpringBeanLifeCycleTenStep.class);

    private String description;

    public SpringBeanLifeCycleTenStep() {
        logger.info("第一步：实例化Bean...");
    }

    public void setDescription(String description) {
        logger.info("第二步：Bean属性赋值...");
        this.description = description;
    }


    /**
     * 初始化Bean的方法
     *      这个方法的方法名随意，但是需要在spring配置文件中配置
     */
    public void initBean() {
        logger.info("第四步：初始化Bean...");
    }

    /**
     * 销毁Bean的方法
     *      这个方法的方法名随意，但是需要在spring配置文件中配置，这个方法需要手动去关闭Spring容器，才会触发这个容器
     */
    public void destoryBean() {
        logger.info("第七步：销毁Bean...");
    }

    @Override
    public String toString() {
        return "SpringBeanLifeCycleSevenStep{" +
                "description='" + description + '\'' +
                '}';
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        logger.info("点位1：BeanClassLoaderAware接口的setBeanClassLoader()方法执行了...");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        logger.info("点位1：BeanFactoryAware的setBeanFactory()方法执行了...");
    }

    @Override
    public void setBeanName(String name) {
        logger.info("点位1：BeanNameAware接口的setBeanName()执行了...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("点位2：InitializingBean接口的afterPropertiesSet()执行了...");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("点位3：DisposableBean接口的destroy()执行了...");
    }
}