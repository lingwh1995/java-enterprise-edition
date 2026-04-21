package org.bluebridge._09_promise;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc Netty 提供的 Promise 测试
 * @date 2025/9/24 15:24
 */

/**
 * netty Promise 不仅有 netty Future 的功能，而且脱离了任务独立存在，只作为两个线程间传递结果的容器
 */
@Slf4j
public class NettyPromiseTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //syncGetResult();
        //asyncGetResult();
        awaitDeadLock();
    }

    /**
     * 同步方式获取 promise 执行结果
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void syncGetResult() throws InterruptedException, ExecutionException {
        // 主动创建 Promise 对象， Promise 相当于一个结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(new NioEventLoopGroup().next());
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
                //int i = 1 / 0;
                int j = 100;
                promise.setSuccess(j);
                log.info("set success, value = {}", j);
            }catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        log.info("等待结果......");
        log.info("获取结果： {}", promise.getNow());
        log.info("获取结果： {}", promise.get());
    }

    /**
     * 异步方式获取 promise 执行结果
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void asyncGetResult() {
        // 主动创建 Promise 对象， Promise 相当于一个结果容器
        DefaultPromise<Integer> promise = new DefaultPromise<>(new NioEventLoopGroup().next());
        // 设置回调，异步接收结果
        promise.addListener(future -> {
            // 这里的 future 就是上面的 promise
            log.info("获取结果： {}", promise.getNow());
            log.info("获取结果： {}", promise.get());
        });
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
                //int i = 1 / 0;
                int j = 100;
                promise.setSuccess(j);
                log.info("set success, value = {}", j);
            }catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        log.info("等待结果......");
    }

    /**
     * await死锁检测
     */
    private static void awaitDeadLock() {
        DefaultEventLoop eventExecutors = new DefaultEventLoop();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventExecutors);

        eventExecutors.submit(()->{
            log.info("1......");
            try {
                promise.await();
                // 注意不能仅捕获 InterruptedException 异常
                // 否则 死锁检查抛出的 BlockingOperationException 会继续向上传播
                // 而提交的任务会被包装为 PromiseTask，它的 run 方法中会 catch 所有异常然后设置为 Promise 的失败结果而不会抛出
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("2......");
        });
        eventExecutors.submit(()->{
            log.info("3......");
            try {
                promise.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("4......");
        });
    }

}
