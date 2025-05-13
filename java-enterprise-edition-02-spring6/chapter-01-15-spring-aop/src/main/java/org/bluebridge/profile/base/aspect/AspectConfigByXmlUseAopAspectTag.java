package org.bluebridge.profile.base.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 切面类  使用xml配置
 * 切面 = 切点 + 通知
 */
public class AspectConfigByXmlUseAopAspectTag {

    private static final Logger logger = LogManager.getLogger(AspectConfigByXmlUseAopAspectTag.class);

    /**
     * 前置通知
     */
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置通知执行了...[使用XML配置文件开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 后置通知
     */
    public void afterReturningAdvice(JoinPoint joinPoint,Object result) {
        logger.info("后置通知执行了...[使用XML配置文件开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName + ",returning: " + result);
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    public  void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("环绕通知的前通知执行了...[使用XML配置文件开发AOP]");
        proceedingJoinPoint.proceed();
        logger.info("环绕通知的后通知执行了...[使用XML配置文件开发AOP]");
    }

    /**
     * 最终通知
     */
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("最终通知执行了...[使用XML配置文件开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 异常通知
     */
    public void afterThrowingAdvice(JoinPoint joinPoint,Exception e) throws Exception{
        logger.info("异常通知执行了...[使用XML配置文件开发AOP]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("methodName: " + methodName);
        logger.info("Exception: " + e.getMessage());
    }
}
