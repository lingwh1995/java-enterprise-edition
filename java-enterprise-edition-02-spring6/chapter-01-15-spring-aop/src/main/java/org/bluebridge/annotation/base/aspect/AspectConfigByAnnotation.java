package org.bluebridge.annotation.base.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 切面类  使用注解配置
 * 切面 = 切点 + 通知
 */
@Aspect
@Component
public class AspectConfigByAnnotation {

    private static final Logger logger = LogManager.getLogger(AspectConfigByAnnotation.class);

    /**
     * 前置通知
     */
    @Before("execution(public * org.bluebridge.annotation.base.service.UserServiceImpl.addUser(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 后置通知
     */
    @AfterReturning(value = "execution(public * org.bluebridge.annotation.base.service.UserServiceImpl.deleteUserById(..))",
                    returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        logger.info("后置通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName + ",returning: " + result);
    }

    /**
     * 环绕通知
     */
    @Around("execution(public * org.bluebridge.annotation.base.service.UserServiceImpl.updateUser(..))")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("环绕通知的前通知执行了...[使用注解开发AOP]");
        proceedingJoinPoint.proceed();
        logger.info("环绕通知的后通知执行了...[使用注解开发AOP]");
    }

    /**
     * 最终通知
     */
    @After("execution(public * org.bluebridge.annotation.base.service.UserServiceImpl.getUserById(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("最终通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "execution(public * org.bluebridge.annotation.base.service.UserServiceImpl.login(..))",
                    throwing = "e")
    public void afterThrowingAdvice(JoinPoint joinPoint,Exception e) {
        logger.info("异常通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("methodName: " + methodName);
        logger.info("Exception: " + e.getMessage());
    }
}
