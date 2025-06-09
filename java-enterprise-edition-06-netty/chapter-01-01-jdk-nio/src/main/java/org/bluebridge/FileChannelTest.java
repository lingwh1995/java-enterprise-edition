package org.bluebridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO的FileChannel
 */
@Slf4j
public class FileChannelTest {

    @Test
    public void testFileChannel() throws Exception {
        // 1.从输入流中获取Channel
        try(FileChannel channel = new FileInputStream("nio.txt").getChannel()) {
            // 2.准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // 3.从Channel中读取数据到，并写入到缓冲区
            while (true) {
                int len = channel.read(buffer);
                log.debug("读取到的字节数：{}", len);
                if (len == -1) {
                    break;
                }
                // 4.切换为读模式
                buffer.flip();
                // 5.从buffer中读取数据
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("读取到的数据：{}", (char)b);
                }
                // 6.切换为写模式
                buffer.clear();
            }
        }
    }

}
