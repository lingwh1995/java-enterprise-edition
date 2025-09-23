package org.bluebridge;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc 事件循环和事件循环组测试
 * @date 2025/9/23 11:06
 */
@Slf4j(topic = "·")
public class EventLoopTest {

    public static void main(String[] args) throws InterruptedException {
        // 1.创建事件循环组
        // 可以负责 io 事件，普通任务，定时任务(内部使用ScheduledThreadPool实现)
        // 默认使用MAX(1，电脑核心数 * 2)线程数
        NioEventLoopGroup group = new NioEventLoopGroup(4);
        // 普通任务， 定时任务
//        DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup();

        // 2.获取下一个事件循环对象(相当于一个循环链表做轮询)， 上面设置为4， 那么每4个为依次循环
        log.info("next: {}", group.next());
        log.info("next: {}", group.next());
        log.info("next: {}", group.next());
        log.info("next: {}", group.next());
        log.info("next: {}", group.next());
        log.info("next: {}", group.next());
        log.info("next: {}", group.next());

        // 3.执行普通任务
        group.next().submit(() -> log.info("这是个普通任务......"));

        // 4.执行定时循环任务
        group.next().scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("这是个定时任务......");
        },3,10, TimeUnit.SECONDS);

        log.debug("main");

        // 5.让主线程等待30秒，确保定时任务有时间执行
        TimeUnit.SECONDS.sleep(30);

        // 6.优雅的关闭(任务全部执行完后关闭)
        //group.shutdownGracefully();
    }

}
