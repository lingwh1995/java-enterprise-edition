package org.bulebridge.profile.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public class MyAspectXml {
    /**
     * 定义前置通知
     */
    public void before(){
        System.out.println("----------前置通知(before)----------");
    }

    /**
     * 定义后置通知
     */
    public void after(JoinPoint joinPoint){
        /**
         * 获取方法信息
         */
//        String Name = joinPoint.getSignature().getName();
//        System.out.println("The afterlogging  "+Name+" end");
        System.out.println("----------后置通知(after)...----------");
    }

    /**
     * 返回通知
     */
    public void afterReturning(JoinPoint joinPoint, Object result){
        System.out.println("----------返回通知(afterReturning)...----------");
        String methodName = joinPoint.getSignature().getName();
        System.out.println("(返回通知)The method " + methodName + " return with " + result);
    }

    /**
     * 异常通知
     */
    public void afterThrowing(JoinPoint joinPoint,Exception e) {
        System.out.println("----------异常通知(afterThrowing)...----------");
        String methodName = joinPoint.getSignature().getName();
        System.out.println("(异常通知)The method " + methodName + " occurs exception: " +e);
    }
    /**
     * 环绕通知
     */
    public Object around(ProceedingJoinPoint  proceedingJoinPoint ){
        System.out.println("----------前环绕(前around)...----------");
        Object o = null;
        try {
            o = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("----------后环绕(后around)...----------");
        return o;
    }


    /**
     * 切入点
     */
    @Pointcut(value="execution(public String org.bulebridge.anno.service.UserServiceImpl.eat())")
    public void log(){
        System.out.println("记录日志...");
    }

}
