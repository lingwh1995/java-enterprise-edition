package org.bluebridge.profile.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public class MyAspectXml {
    /**
     * 定义前置通知
     */
    public void before(){
        System.out.println("----------前置通知(before)开始----------");
        System.out.println("----------前置通知(before)结束----------");
    }

    /**
     * 定义后置通知
     */
    public void after(JoinPoint joinPoint){
        System.out.println("----------后置通知(after)开始----------");
        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        System.out.println("methodName = " + methodName);
        System.out.println("----------后置通知(after)结束----------");
        System.out.println("----------后置通知(after)...----------");
    }

    /**
     * 返回通知
     */
    public void afterReturning(JoinPoint joinPoint, Object result){
        System.out.println("----------返回通知(afterReturning)开始----------");
        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        System.out.println("methodName = " + methodName);
        System.out.println("result = " + result);
        System.out.println("----------返回通知(afterReturning)结束----------");
    }

    /**
     * 异常通知
     */
    public void afterThrowing(JoinPoint joinPoint,Exception e) {
        System.out.println("----------异常通知(afterThrowing)开始----------");
        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        System.out.println("methodName = " + methodName);
        System.out.println("e = " + e);
        System.out.println("----------异常通知(afterThrowing)结束----------");
    }

    /**
     * 环绕通知
     */
    public Object around(ProceedingJoinPoint  proceedingJoinPoint ){
        System.out.println("----------前环绕(前around)...----------");
        Object o = null;
        try {
            // 获取方法信息
            String methodName = proceedingJoinPoint.getSignature().getName();
            System.out.println("methodName = " + methodName);
            o = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("----------后环绕(后around)...----------");
        return o;
    }

}
