package org.bluebridge.chapter_02_channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lingwh
 * @desc NIO 使用 FileChannel 读取文件中的数据
 * @date 2025/6/24 15:08
 */
@Slf4j
public class _01_FileChannelTest {

    @Test
    public void testFileChannel() throws Exception {
        // 1.从输入流中获取 channel
        try(FileChannel channel = new FileInputStream("files/nio.txt").getChannel()) {
            // 2.创建一个大小为10字节的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // 3.从 channel 中读取数据到，并写入到缓冲区
            while (true) {
                // 返回从 channel 中读取到的字节数，如果返回-1，则表示没有数据了
                int len = channel.read(buffer);
                log.info("读取到数据的字节长度：{}", len);
                if (len == -1) {
                    break;
                }
                // 4.切换为读模式 [position指针指向开头，limit指针指向写入的最后位置（内存长度）]
                buffer.flip();
                // 5.从 buffer 中读取数据
                while (buffer.hasRemaining()) { // 检查缓冲区中是否还有剩余的数据
                    byte b = buffer.get(); // 每次读取一个字节
                    log.info("position :{}", buffer.position());
                    log.info("读取到的数据：{}", (char)b);
                }
                // 6.一次循环后切换为写模式 [ 复位position limit指针（动不了实际数据） ]
                //buffer.clear();
                buffer.compact(); //从上次未读完的地方向前移动，并沿着未读完数据的最后一位往后写
            }
        }
    }

}
