package org.bluebridge;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author lingwh
 * @desc Netty自带的Future测试，提供了更强大的功能，可以实时取到运行状态
 * @date 2025/9/23 18:13
 */
@Slf4j(topic = "·")
public class NettyFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();// 会有多个loop(executor)

        // 拿到一个EventLoop
        EventLoop loop = group.next();

        Future<Integer> future = loop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("正在运行...");
                Thread.sleep(10000);
                return 70;
            }
        });
        log.debug("等待结果");
        log.debug("结果是" + future.getNow());

        group.shutdownGracefully();
    }

}

