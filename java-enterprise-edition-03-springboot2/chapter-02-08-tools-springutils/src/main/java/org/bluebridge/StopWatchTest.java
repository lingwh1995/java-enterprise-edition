package org.bluebridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Slf4j
public class StopWatchTest {

    /**
     * 测试StopWatch HelloWorld 案例
     */
    @Test
    public void testStopWatchHelloWorld() throws InterruptedException {
        // 创建一个StopWatch对象
        StopWatch stopWatch = new StopWatch("秒表");
        // 开始计时
        stopWatch.start("task-1");
        task1();
        // 停止计时
        stopWatch.stop();
        log.info("任务名称：{}，执行时间：{}，花费总时间：{}，任务总数：{}", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis(), stopWatch.getTotalTimeMillis(), stopWatch.getTaskCount());
    }

    /**
     * 测试StopWatch
     *  1.多个任务执行时间统计
     *  2.优雅的打印执行结果
     */
    @Test
    public void testStopWatch() throws InterruptedException {
        // 创建一个StopWatch对象
        StopWatch stopWatch = new StopWatch("秒表");
        // task-1开始计时
        stopWatch.start("task-1");
        task1();
        // task-1停止计时
        stopWatch.stop();

        // task-2开始计时
        stopWatch.start("task-2");
        task2();
        // task-2停止计时
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    /**
     * 任务一：睡眠1000毫秒
     */
    private void task1() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1000);
    }

    /**
     * 任务二：睡眠2000毫秒
     */
    private void task2() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(2000);
    }

}
