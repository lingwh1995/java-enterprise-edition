package org.bluebridge.profile.genericpointcut.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AspectConfigByXmlUseAopAspectTag {

    private static final Logger logger = LoggerFactory.getLogger(AspectConfigByXmlUseAopAspectTag.class);

    /**
     * 前置通知
     */
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("前置通知执行了...[使用XML配置文件开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 后置通知
     */
    public void afterReturningAdvice(JoinPoint joinPoint,Object result) {
        logger.info("后置通知执行了...[使用XML配置文件开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName + ",returning: " + result);
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint
     * @throws Throwable
     */
    public  void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("环绕通知的前通知执行了...[使用XML配置文件开发AOP+通用连接点的配置]");
        proceedingJoinPoint.proceed();
        logger.info("环绕通知的后通知执行了...[使用XML配置文件开发AOP+通用连接点的配置]");
    }

    /**
     * 最终通知
     */
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("最终通知执行了...[使用XML配置文件开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("MethodName: " + methodName);
    }

    /**
     * 异常通知
     */
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception e) {
        logger.info("异常通知执行了...[使用XML配置文件开发AOP+通用连接点的配置]");
        String methodName = joinPoint.getSignature().getName();
        logger.info("methodName: " + methodName);
        logger.info("Exception: " + e.getMessage());
    }
}
