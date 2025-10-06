package org.bluebridge.utils;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static sun.security.pkcs11.wrapper.Constants.NEWLINE;

@Slf4j
public class DebugUtil {

    /**
     * 一个简单的debug()
     */
    private static void debug(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:")
                .append(buffer.readerIndex())
                .append(" write index:")
                .append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        log.info(buf.toString());
    }

}
