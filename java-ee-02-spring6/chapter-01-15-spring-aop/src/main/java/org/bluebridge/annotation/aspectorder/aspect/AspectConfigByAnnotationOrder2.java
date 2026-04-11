package org.bluebridge.annotation.aspectorder.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class AspectConfigByAnnotationOrder2 {

    private static final Logger logger = LogManager.getLogger(AspectConfigByAnnotationOrder2.class);

    @Before("execution(public * org.bluebridge.annotation.aspectorder.service.CatServiceImpl.deleteCatById(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置方法执行了...[使用注解开发AOP+多个切面配置执行顺序-AspectConfigByAnnotationOrder2]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }
}