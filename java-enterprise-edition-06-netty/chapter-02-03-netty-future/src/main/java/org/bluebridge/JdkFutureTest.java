package org.bluebridge;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author lingwh
 * @desc Jdk自带的Future测试
 * @date 2025/9/23 18:13
 */
@Slf4j(topic = "·")
public class JdkFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 2. 提交任务, callable有返回结果, runnable没有返回结果
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 50;
            }
        });

        // 阻塞方法 get(), 等到有结果
        log.info("Future结果: {}", future.get());

        log.info("取消了吗?: {}", future.isCancelled());
        future.cancel(true);// true代表强制cancel
        log.info("取消了吗?: {}", future.isCancelled());

        log.info("完成了嘛?: {}", future.isDone());
        log.info("线程池结束了吗?: {}", executorService.isShutdown());
        executorService.shutdown();
        log.info("取消了吗?: {}", future.isCancelled());
        log.info("线程池结束了吗?: {}", executorService.isShutdown());
    }

}