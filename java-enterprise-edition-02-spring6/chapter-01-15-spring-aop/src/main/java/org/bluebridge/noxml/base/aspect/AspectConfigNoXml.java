package org.bluebridge.noxml.base.aspect;

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
public class AspectConfigNoXml {

    private static final Logger logger = LogManager.getLogger(AspectConfigNoXml.class);

    /**
     * 前置通知
     */
    @Before("execution(public * org.bluebridge.noxml.base.service.StudentServiceImpl.deleteStudentById(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 后置通知
     */
    @AfterReturning("execution(public * org.bluebridge.noxml.base.service.StudentServiceImpl.deleteStudentById(..))")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        logger.info("后置通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 环绕通知
     */
    @Around("execution(public * org.bluebridge.noxml.base.service.StudentServiceImpl.deleteStudentById(..))")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("环绕通知的前通知执行了...[使用注解开发AOP]");
        proceedingJoinPoint.proceed();
        logger.info("环绕通知的后通知执行了...[使用注解开发AOP]");
    }

    /**
     * 最终通知
     */
    @After("execution(public * org.bluebridge.noxml.base.service.StudentServiceImpl.deleteStudentById(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("最终通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 异常通知
     */
    @AfterThrowing("execution(public * org.bluebridge.noxml.base.service.StudentServiceImpl.deleteStudentById(..))")
    public void afterThrowingAdvice(JoinPoint joinPoint) {
        logger.info("异常通知执行了...[使用注解开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("methodName: " + methodName);
    }
}
