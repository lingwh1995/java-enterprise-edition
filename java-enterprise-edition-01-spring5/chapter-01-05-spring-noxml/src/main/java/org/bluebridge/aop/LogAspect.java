package org.bluebridge.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @author ronin
 */
@Aspect
public class LogAspect {

    @Pointcut("execution(public int org.bluebridge.aop.Caculator.div(int,int))")
    public void pointCut(){}

    @Before("pointCut()")
    public void logStar(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName());
        System.out.println(joinPoint.getArgs());
        System.out.println("除法运行......");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName());
        System.out.println("除法结束......");
    }

    @AfterReturning(value="pointCut()",returning ="result")
    public void logRenturn(JoinPoint joinPoint,Object result){
        System.out.println("方法返回值:"+result);
        System.out.println("除法正常返回......运行结果");
    }

    @AfterThrowing(value="pointCut()",throwing ="exception")
    public void LogException(JoinPoint joinPoint,Exception exception){
        System.out.println("发生异常的方法的名称:"+joinPoint.getSignature().getName());
        System.out.println("捕获到的异常:"+exception);
        System.out.println("除法异常......");
    }
}
