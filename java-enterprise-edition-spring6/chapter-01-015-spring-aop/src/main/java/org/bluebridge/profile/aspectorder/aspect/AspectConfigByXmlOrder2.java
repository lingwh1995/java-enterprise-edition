package org.bluebridge.profile.aspectorder.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AspectConfigByXmlOrder2 {

    private static final Logger logger = LoggerFactory.getLogger(AspectConfigByXmlOrder2.class);

    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置方法执行了...[使用XML配置文件开发AOP+多个切面配置执行顺序-AspectConfigByXmlOrder2]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }
}