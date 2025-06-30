package org.bluebridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StopWatch;

@Slf4j
public class StopWatchTest {

    /**
     * 测试StopWatch
     */
    @Test
    public void testStopWatch() {
        // 创建一个StopWatch对象
        StopWatch stopWatch = new StopWatch();
        // 开始计时
        stopWatch.start();

        task();

        // 停止计时
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskName() + "执行时间：" + stopWatch.getLastTaskTimeMillis());
    }

    private void task() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
