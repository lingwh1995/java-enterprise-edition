package org.bluebridge.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.bluebridge.config.Config;
import org.bluebridge.message.LoginRequestMessage;
import org.bluebridge.message.Message;
import org.bluebridge.protocol.MessageCodecSharable;
import org.bluebridge.protocol.Serializer;

/**
 * @author lingwh
 * @desc 序列化测试类
 * @date 2025/11/11 16:40
 */
public class SerializerTest {

    public static void main(String[] args) {
        MessageCodecSharable MESSAGE_CODEC_SHARABLE = new MessageCodecSharable();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel channel = new EmbeddedChannel(LOGGING_HANDLER, MESSAGE_CODEC_SHARABLE, LOGGING_HANDLER);

        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("zhangsan", "123456");
        // channel.writeOutbound(loginRequestMessage);

        ByteBuf byteBuf = messageToBytes(loginRequestMessage);
        channel.writeInbound(byteBuf);
    }

    public static ByteBuf messageToBytes(Message msg) {
        int algorithm = Config.getSerializerAlgorithm().ordinal();
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        out.writeBytes(new byte[]{1,2,3,4});out.writeByte(1);
        out.writeByte(algorithm);
        out.writeByte(msg.getMessageType());
        out.writeInt(msg.getSequenceId());
        out.writeByte(0xff);
        byte[] bytes = Serializer.Algorithm.values()[algorithm].serialize(msg);out.writeInt(bytes.length);
        out.writeBytes(bytes);
        return out;
    }

}
