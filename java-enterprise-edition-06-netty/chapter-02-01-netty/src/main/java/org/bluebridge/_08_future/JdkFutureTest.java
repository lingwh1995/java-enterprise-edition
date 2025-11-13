package org.bluebridge._08_future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author lingwh
 * @desc Jdk 自带的 Future 测试
 * @date 2025/9/23 18:13
 */

/**
 * jdk Future 只能同步等待任务结束（或成功、或失败）才能得到结果
 */
@Slf4j
public class JdkFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 2.提交任务，callable 有返回结果，runnable 没有返回结果
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("正在运行......");
                TimeUnit.MILLISECONDS.sleep(1000 * 5);
                return 100;
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