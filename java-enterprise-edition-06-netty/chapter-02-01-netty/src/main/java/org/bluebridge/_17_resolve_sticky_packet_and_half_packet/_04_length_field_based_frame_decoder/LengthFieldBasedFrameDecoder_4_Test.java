package org.bluebridge._17_resolve_sticky_packet_and_half_packet._04_length_field_based_frame_decoder;

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
 * @desc 预设长度解码器 消息中有版本号和偏移量，并且从解码后的帧中剥离前4字节偏移量
 * @date 2025/10/14 14:05
 */
@Slf4j
public class LengthFieldBasedFrameDecoder_4_Test {

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
                new LengthFieldBasedFrameDecoder(1024, 4, 4, 1, 4),
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
        // 偏移量
        byte[] offset = { 0x01, 0x02, 0x03, 0x04};
        // 实际内容
        byte[] bytes = content.getBytes();
        // 实际内容长度
        int length = bytes.length;
        // 版本号
        byte version = 1;
        // 写入偏移量
        buffer.writeBytes(offset);
        buffer.writeInt(length);
        buffer.writeByte(version);
        buffer.writeBytes(bytes);
    }

}
