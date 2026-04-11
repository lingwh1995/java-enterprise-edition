package org.bluebridge.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ronin
 */
@Component
public class ScheduleExecuteTask {

    /**
     * cron表达式格式
     * @Scheduled(cron = "{秒数} {分钟} {小时} {日期} {月份} {星期}")
     * cron表达式示例
     * @Scheduled(cron = "0 00 07 * * *")
     */
    //每5秒执行一次：写法一
    @Scheduled(cron = "*/5 * * * * ?")
    public void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }

    /*
    //每5秒执行一次：写法二
    @Scheduled(fixedDelay = 1000 * 5)
    public void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }*/
}
