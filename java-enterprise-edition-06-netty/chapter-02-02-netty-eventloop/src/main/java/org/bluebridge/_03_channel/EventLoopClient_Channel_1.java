package org.bluebridge._03_channel;

/**
 * @author lingwh
 * @desc
 * @date 2025/9/23 17:25
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc Channel常用方法
 * @date 2025/9/23 17:29
 */

/**
 * Channel 的常用方法
 *     close() 可以用来关闭Channel
 *     closeFuture() 用来处理 Channel 的关闭
 *         sync 方法作用是同步等待 Channel 关闭
 *         而 addListener 方法是异步等待 Channel 关闭
 *     pipeline() 方法用于添加处理器
 *     write() 方法将数据写入
 *         因为缓冲机制，数据被写入到 Channel 中以后，不会立即被发送
 *         只有当缓冲满了或者调用了flush()方法后，才会将数据通过 Channel 发送出去
 *     writeAndFlush() 方法将数据写入并立即发送（刷出）
 *
 *  注意事项
 *      带有Future、Promise的类型都是和异步方法配套使用，用来处理结果
 */
@Slf4j(topic = "·")
public class EventLoopClient_Channel_1 {

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());// 内部使用CharBuffer.wrap(msg)
                    }
                })
                // 连接到服务器
                // connect是一个异步非阻塞方法(即发起调用的main线程不阻塞, 把任务交给connect线程)
                .connect("localhost", 8080);

        // 2.1 使用sync 同步处理结果
        channelFuture
                // 下面都是ChannelFuture的方法
                .sync()// 这是一个阻塞方法, 同来同步结果, 一旦建立连接后才往下执行
                .channel()// 获取当前Channel
                .writeAndFlush("我是nio");// 使用这个channel发送消息

        // 2.2 使用addListener(回调对象)方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            /**
             * 建立好后, 会调用这个方法
             * @param channelFuture 建立好的channel
             * @throws Exception
             */
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                channel.writeAndFlush("建立好channel了");
            }
        });
    }

}

