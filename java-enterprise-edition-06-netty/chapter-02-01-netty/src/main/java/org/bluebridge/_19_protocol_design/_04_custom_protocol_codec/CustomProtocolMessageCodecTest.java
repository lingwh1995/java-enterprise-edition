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

public class CustomProtocolMessageCodecTest {

    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
            // 帧解码器
            new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
            // 日志处理器
            new LoggingHandler(LogLevel.DEBUG),
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
