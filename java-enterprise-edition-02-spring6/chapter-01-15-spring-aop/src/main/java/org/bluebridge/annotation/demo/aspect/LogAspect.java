package org.bluebridge.annotation.demo.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 切面类  使用注解配置
 * 切面 = 切点 + 通知
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LogManager.getLogger(LogAspect.class);

    @Pointcut("execution(public * org.bluebridge.annotation.demo.service.OrderServiceImpl.addOrder(..))")
    public void addOrderPointCut() {}

    @Pointcut("execution(public * org.bluebridge.annotation.demo.service.OrderServiceImpl.deleteOrderById(..))")
    public void deleteOrderByIdPointCut() {}

    @Pointcut("execution(public * org.bluebridge.annotation.demo.service.OrderServiceImpl.updateOrder(..))")
    public void updateOrderPointCut() {}

    @Pointcut("execution(public * org.bluebridge.annotation.demo.service.OrderServiceImpl.getOrderById(..))")
    public void getOrderByIdPointCut() {}

    /**
     * 前置通知
     */
    @Before("addOrderPointCut() || deleteOrderByIdPointCut() || updateOrderPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        String date = simpleDateFormat.format(new Date());
        logger.info("操作执行时间: " + date);
        logger.info("前置通知执行了...[使用注解开发AOP]");
        //String methodName = joinPoint.getSignature().getName();
        //logger.info("MethodName: " + methodName);
    }

}
