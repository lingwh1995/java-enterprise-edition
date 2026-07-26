package org.bluebridge.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步任务无返回结果服务类
 *
 * @author lingwh
 * @date 2019/11/18 15:10
 */
@Service
public class AsyncTaskSuccessWithoutReturnResultService {

    /**
     * 任务 1
     * @throws InterruptedException
     */
    @Async
    public void task1() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task1任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    /**
     * 任务 2
     * @throws InterruptedException
     */
    @Async
    public void task2() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(2000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task2任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    /**
     * 任务 3
     * @throws InterruptedException
     */
    @Async
    public void task3() throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(3000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task3任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }
}
