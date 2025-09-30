package org.bluebridge._05_future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc Netty自带的Future测试，提供了更强大的功能，可以实时取到运行状态
 * @date 2025/9/23 18:13
 */

/**
 * netty Future 可以同步等待任务结束得到结果，也可以异步方式得到结果，但都是要等任务结束
 */
@Slf4j
public class NettyFutureTest {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();// 会有多个loop(executor)

        // 拿到一个EventLoop
        EventLoop eventLoop = group.next();

        Future<Integer> future = eventLoop.submit(() -> {
            log.debug("正在运行......");
            TimeUnit.MILLISECONDS.sleep(3000);
            return 100;
        });

        log.debug("等待结果......");
        TimeUnit.MILLISECONDS.sleep(5000);

        log.debug("结果是： {}", future.getNow());

        group.shutdownGracefully();
    }

}

