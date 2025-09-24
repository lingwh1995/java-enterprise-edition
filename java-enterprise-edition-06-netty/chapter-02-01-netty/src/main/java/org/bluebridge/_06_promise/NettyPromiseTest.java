package org.bluebridge._06_promise;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc
 * @date 2025/9/24 15:24
 */
@Slf4j(topic = "·")
public class NettyPromiseTest {

    public static void main(String[] args) throws InterruptedException {
        // Promise 相当于一个结果容器
        DefaultPromise<Object> promise = new DefaultPromise<>(new NioEventLoopGroup().next());// 主动创建Promise对象

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 向Promise容器填充对象
            promise.setSuccess(80);
        }).start();

        log.info("获取结果： {}", promise.getNow());
        Thread.sleep(2000);
        log.info("获取结果： {}", promise.getNow());
    }

}
