package org.bluebridge._06_promise;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc
 * @date 2025/9/24 15:24
 */

/**
 * netty Promise 不仅有 netty Future 的功能，而且脱离了任务独立存在，只作为两个线程间传递结果的容器
 */
@Slf4j(topic = "·")
public class NettyPromiseTest {

    public static void main(String[] args) throws InterruptedException {
        // Promise 相当于一个结果容器
        DefaultPromise<Object> promise = new DefaultPromise<>(new NioEventLoopGroup().next());// 主动创建Promise对象

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 向Promise容器填充对象
            promise.setSuccess(100);
        }).start();

        log.info("获取结果： {}", promise.getNow());
        Thread.sleep(2000);
        log.info("获取结果： {}", promise.getNow());
    }

}
