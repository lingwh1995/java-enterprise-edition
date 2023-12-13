package org.bluebridge.beanlifecycle.sevenstep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanPostProcessor：可以在Bean实例化之前和之后添加一些附加操作
 *      最佳应用场景之一是 日志的处理
 */
public class LogBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(LogBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("第三步：执行Bean后处理器的before方法...");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("执行Bean后处理器的after方法...");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
