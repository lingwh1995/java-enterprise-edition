package org.bluebridge._19_protocol_design._04_custom_protocol_codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lingwh
 * @desc 自定义登录请求协议编解码器测试
 * @date 2025/10/15 17:33
 */

public class CustomProtocolMessageCodecAndSharableTest {

    public static void main(String[] args) throws Exception {
        /**
         * 如何判断处理器是否支持多线程共享
         *  1.通过处理器原码 @Sharable 注解判断，支持多线程共享的处理器上面都有 @Sharable 注解
         *  2.自定义的处理器如果要支持多线程共享，需要在处理器类上添加 @Sharable 或 @ChannelHandler.Sharable 注解
         *  3.@Sharable 是 @ChannelHandler.Sharable的简化形式，需要通过适当的 import 语句来使用
         *  4.如果处理器会改变经过处理器的数据，那么该处理器就不应该被多个线程共享，如粘包半包处理器，如果处理器不会改变经过处理器的数据，那么
         *    该处理器就可以被多个线程共享，如日志处理器
         */
        // 日志处理器
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel channel = new EmbeddedChannel(
            // 帧解码器
            new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
            // 日志处理器
            LOGGING_HANDLER,
            // 自定义协议编解码器
            new MessageCodec());

        // encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);

        //入站
        //channel.writeInbound(buf);

        //测试 LengthFieldBasedFrameDecoder
        ByteBuf buf1 = buf.slice(0, 100);
        ByteBuf buf2 = buf.slice(100, buf.readableBytes() - 100);

        channel.writeInbound(buf1);     // release 0
        buf1.retain();                  //引用计数 + 1
        channel.writeInbound(buf2);
    }

}
