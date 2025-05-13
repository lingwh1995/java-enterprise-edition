package org.bluebridge.beanlifecycle.fivestep;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Spring中Bean的生命周期五步：
 *  第一步：实例化Bean
 *  第二步：Bean属性赋值
 *  第三步：初始化Bean
 *  第四步：使用Bean
 *  第五步：销毁Bean
 */
public class SpringBeanLifeCycleFiveStep {

    private static final Logger logger = LogManager.getLogger(SpringBeanLifeCycleFiveStep.class);

    private String description;

    public SpringBeanLifeCycleFiveStep() {
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
        logger.info("第三步：初始化Bean...");
    }

    /**
     * 销毁Bean的方法
     *      这个方法的方法名随意，但是需要在spring配置文件中配置，这个方法需要手动去关闭Spring容器，才会触发这个容器
     */
    public void destoryBean() {
        logger.info("第五步：销毁Bean...");
    }

    @Override
    public String toString() {
        return "SpringBeanLifeCycleFiveStep{" +
                "description='" + description + '\'' +
                '}';
    }
}