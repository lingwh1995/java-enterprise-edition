package org.bluebridge._07_future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc Netty 自带的 Future 测试，提供了更强大的功能，可以实时取到运行状态
 * @date 2025/9/23 18:13
 */

/**
 * netty Future 可以同步等待任务结束得到结果，也可以异步方式得到结果，但都是要等任务结束
 */
@Slf4j
public class NettyFutureTest {

    public static void main(String[] args) throws InterruptedException {
        // 会有多个 NioEventLoop (executor)
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 拿到一个 EventLoop
        EventLoop eventLoop = group.next();

        Future<Integer> future = eventLoop.submit(() -> {
            log.info("正在运行......");
            TimeUnit.MILLISECONDS.sleep(3000);
            return 100;
        });

        log.info("等待结果......");
        TimeUnit.MILLISECONDS.sleep(5000);

        // 同步方式接收结果
//        log.info("结果是： {}", future.getNow());

        // 异步方式接收结果
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) {
                log.info("结果是： {}", future.getNow());
            }
        });
    }

}

