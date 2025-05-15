package org.bluebridge.anno.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspectAnno {

    /**
     * 为类生成代理
     */
    /**
     * 前置通知
     */
    @Before(value="execution(public String org.bluebridge.anno.dao.PersonDao.save())")
    public void before(){
        System.out.println("----------前置通知(before)开始----------");
        System.out.println("----------前置通知(before)结束----------");
    }


    /**
     * 为接口生成代理
     */
    /**
     * 前置通知
     */
    @Before(value="execution(public String org.bluebridge.anno.service.UserServiceImpl.eat())")
    public void before1(){
        System.out.println("----------前置通知(before)开始----------");
        System.out.println("----------前置通知(before)结束----------");
    }

    /**
     * 后置通知
     */
    @After(value="execution(public String org.bluebridge.anno.service.UserServiceImpl.eat())")
    public void after(JoinPoint joinPoint){
        System.out.println("----------后置通知(after)开始----------");
        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        System.out.println("methodName = " + methodName);
        System.out.println("----------后置通知(after)结束----------");
        System.out.println("----------后置通知(after)...----------");
    }

    /**
     *返回值通知
     */
    @AfterReturning(value="execution(public String org.bluebridge.anno.service.UserServiceImpl.eat())",returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        System.out.println("----------返回通知(afterReturning)开始----------");
        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        System.out.println("methodName = " + methodName);
        System.out.println("result = " + result);
        System.out.println("----------返回通知(afterReturning)结束----------");
    }

    /**
     *环绕通知
     */
    @Around(value="execution(public String org.bluebridge.anno.service.UserServiceImpl.eat())")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("----------前环绕通知(around)----------");
        Object o = null;
        try {
            // 获取方法信息
            String methodName = proceedingJoinPoint.getSignature().getName();
            System.out.println("methodName = " + methodName);
            o = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("----------后环绕通知(around)----------");
        return o;
    }

    /**
     *异常通知
     */
    @AfterThrowing(value="execution(public String org.bluebridge.anno.service.UserServiceImpl.eat())",throwing="e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        System.out.println("----------异常通知(afterThrowing)开始----------");
        // 获取方法信息
        String methodName = joinPoint.getSignature().getName();
        System.out.println("methodName = " + methodName);
        System.out.println("e = " + e);
        System.out.println("----------异常通知(afterThrowing)结束----------");
    }

    /**
     * 切入点
     */
    @Pointcut(value="execution(public String org.bluebridge.anno.service.UserServiceImpl.eat())")
    public void log(){}

    /**
     * 使用切入点的前置通知
     */
    @Before(value="org.bluebridge.anno.aspect.MyAspectAnno.log()")
    public void beforeUsePointCut(){
        System.out.println("----------使用切入点的前置通知(beforeUsePointCut)----------");
    }

}
