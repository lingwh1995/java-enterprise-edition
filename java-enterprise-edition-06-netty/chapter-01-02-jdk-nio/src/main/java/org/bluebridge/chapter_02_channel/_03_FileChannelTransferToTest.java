package org.bluebridge.chapter_02_channel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 *
 */
/**
 * @author lingwh
 * @desc   NIO中FileChannel的transferTo()方法测试
 * @date   2025/6/24 17:08
 */
@Slf4j
public class _03_FileChannelTransferToTest {

    /**
     * 传输小于2G的数据
     */
    @Test
    public void FileChannelTransferToLessThan2GTest() {
        try (FileChannel from = new FileInputStream("files/from.txt").getChannel();
            FileChannel to = new FileOutputStream("files/to.txt").getChannel()
        ) {
            // 获取文件大小
            long size = from.size();
            log.info("文件大小：{}", size);
            // 效率高，会使用系统底层零拷贝进行优化，一次最多可以传输2G文件
            long currentTransferDataLength = from.transferTo(0, size, to);
            log.info("本次传输的数据长度 = {} 字节", currentTransferDataLength);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 传输大于2G的数据
     */
    @Test
    public void FileChannelTransferToLessThan2GTest1() {
        // 返回当前JVM运行的纳秒级时间戳
        long start = System.nanoTime();
        try (FileChannel from = new FileInputStream("E:\\nio\\from.iso").getChannel();
             FileChannel to = new FileOutputStream("E:\\nio\\to.iso").getChannel()
        ) {
            // 获取文件大小
            long size = from.size();
            log.info("文件大小：{}", size);
            long copySize = 0;
            while(copySize < size){
                copySize += from.transferTo(copySize, size, to);
                log.info("已经拷贝传输的数据大小：{}", copySize);
                log.info("to.position(): {}", to.position());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 返回当前JVM运行的纳秒级时间戳
        long end = System.nanoTime();
        log.info("transferTo 用时: {} 秒", (end - start) / 1000_000_000.0);
    }

}
