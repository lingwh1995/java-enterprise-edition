package org.bluebridge.annotation.aspectorder.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class AspectConfigByAnnotationOrder1 {

    private static final Logger logger = LoggerFactory.getLogger(AspectConfigByAnnotationOrder1.class);

    @Before("execution(public * org.bluebridge.annotation.aspectorder.service.CatServiceImpl.deleteCatById(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置方法执行了...[使用注解开发AOP+多个切面配置执行顺序-AspectConfigByAnnotationOrder1]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }
}