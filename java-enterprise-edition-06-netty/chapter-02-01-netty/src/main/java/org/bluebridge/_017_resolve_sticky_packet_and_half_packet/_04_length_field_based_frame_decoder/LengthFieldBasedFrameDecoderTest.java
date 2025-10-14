package org.bluebridge._017_resolve_sticky_packet_and_half_packet._04_length_field_based_frame_decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.utils.DebugUtil;

/**
 * @author lingwh
 * @desc 预设长度解码器
 * @date 2025/10/14 14:05
 */
@Slf4j
public class LengthFieldBasedFrameDecoderTest {

    public static void main(String[] args) {
        /**
         * 预设长度解码器参数说明
         *
         * @param maxFrameLength       帧的最大长度。如果帧的长度大于此值，将抛出 {@link TooLongFrameException} 异常。
         * @param lengthFieldOffset    长度字段的偏移量
         * @param lengthFieldLength    长度字段的长度
         * @param lengthAdjustment     要添加到长度字段值的补偿值
         * @param initialBytesToStrip  从解码后的帧中剥离的起始字节数
         *
         * public LengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip)
         */
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0),
                new LoggingHandler(LogLevel.DEBUG)
        );

        // 4个字节的内容长度， 实际内容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "Hello, world");
        send(buffer, "Hi!");
        DebugUtil.debug(buffer);
        channel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(); // 实际内容
        int length = bytes.length; // 实际内容长度
        buffer.writeInt(length);
        buffer.writeBytes(bytes);
    }

}
