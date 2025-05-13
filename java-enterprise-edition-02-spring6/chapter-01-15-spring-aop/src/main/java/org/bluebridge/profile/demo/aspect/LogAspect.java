package org.bluebridge.profile.demo.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 切面类  使用注解配置
 * 切面 = 切点 + 通知
 */
public class LogAspect {

    private static final Logger logger = LogManager.getLogger(LogAspect.class);

    /**
     * 前置通知
     */
    public void beforeAdvice(JoinPoint joinPoint) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        String date = simpleDateFormat.format(new Date());
        logger.info("操作执行时间: " + date);
        logger.info("前置通知执行了...[使用XML配置开发AOP]");
        //String methodName = joinPoint.getSignature().getName();
        //logger.info("MethodName: " + methodName);
    }

}
