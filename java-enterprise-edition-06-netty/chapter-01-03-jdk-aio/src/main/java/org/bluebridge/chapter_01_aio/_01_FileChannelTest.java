package org.bluebridge.chapter_01_aio;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.bluebridge.ByteBufferUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author lingwh
 * @desc
 * @date 2025/7/16 18:34
 */
@Slf4j
public class _01_FileChannelTest {

    public static void main(String[] args) throws IOException {
        try{
            AsynchronousFileChannel channel = AsynchronousFileChannel
                    .open(Paths.get("java-enterprise-edition-06-netty/chapter-01-03-jdk-aio/files/aio.txt"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(2);
            log.info("begin......");
            channel.read(buffer, 0, null, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("read completed......{}", result);
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.info("read failed......");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("do other things......");
        // 默认文件 AIO 使用的线程都是守护线程，所以最后要执行 System.in.read() 以避免守护线程意外结束
        System.in.read();
    }

}