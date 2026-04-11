package org.bluebridge.annotation.genericpointcut.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 使用时直接将原来的切点表达式替换为被@Pointcut注解标注的方法的方法名，如果该方法在其他包下，则需要在方法名前添加全路径名即可
 */
@Aspect
@Component
public class AspectConfigByAnnotation {

    private static final Logger logger = LogManager.getLogger(AspectConfigByAnnotation.class);

    /**
     * 前置通知
     */
    @Before("genericPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置通知执行了...[使用注解开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 后置通知
     */
    @AfterReturning(value = "genericPointCut()",returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint,Object result) {
        logger.info("后置通知执行了...[使用注解开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName + ",returning: " + result);
    }

    /**
     * 环绕通知
     */
    @Around("genericPointCut()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("环绕通知的前通知执行了...[使用注解开发AOP+通用连接点的配置]");
        proceedingJoinPoint.proceed();
        logger.info("环绕通知的后通知执行了...[使用注解开发AOP+通用连接点的配置]");
    }

    /**
     * 最终通知
     */
    @After("genericPointCut()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("最终通知执行了...[使用注解开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "genericPointCut()", throwing = "e")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        logger.info("异常通知执行了...[使用注解开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("methodName: " + methodName);
        logger.info("Exception: " + e.getMessage());
    }

    /**
     * 配置通用切点
     */
    @Pointcut("execution(public * org.bluebridge.annotation.genericpointcut.service.OrderServiceImpl.deleteOrderById(..))")
    public void genericPointCut() {

    }
}
