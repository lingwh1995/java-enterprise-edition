package org.bluebridge.profile.advisor.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 切面类  使用xml配置
 * 切面 = 切点 + 通知
 */
public class AspectConfigByXmlUseAdvisorTag implements
        MethodBeforeAdvice, AfterReturningAdvice, MethodInterceptor, ThrowsAdvice {

    private static final Logger logger = LogManager.getLogger(AspectConfigByXmlUseAdvisorTag.class);

    /**
     * 前置通知
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info("前置通知执行了...[使用XML配置文件开发AOP]");
    }

    /**
     * 后置通知
     * @param returnValue the value returned by the method, if any
     * @param method the method being invoked
     * @param args the arguments to the method
     * @param target the target of the method invocation. May be {@code null}.
     * @throws Throwable
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        logger.info("后置通知执行了...[使用XML配置文件开发AOP]");
    }

    /**
     * 环绕通知
     * @param invocation the method invocation joinpoint
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        logger.info("环绕通知的前通知执行了...[使用XML配置文件开发AOP]");
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        logger.info("methodName: " + methodName + ",arguments: " + arguments.toString());
        //执行方法
        invocation.getMethod().invoke(invocation.getThis(),invocation.getArguments());
        Object result = invocation.proceed();
        logger.info("环绕通知的后通知执行了...[使用XML配置文件开发AOP]");
        return result;
    }

    /**
     * 异常通知
     * @param method
     * @param args
     * @param target
     * @param ex
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        logger.info("异常通知执行了...[使用XML配置文件开发AOP]");
    }

}
