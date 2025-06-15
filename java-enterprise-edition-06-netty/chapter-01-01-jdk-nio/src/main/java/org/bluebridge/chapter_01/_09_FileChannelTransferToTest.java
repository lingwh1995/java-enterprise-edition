package org.bluebridge.chapter_01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * FileChannel的 transferTo() 方法测试
 */
@Slf4j
public class _09_FileChannelTransferToTest {

    @Test
    public void FileChannelTransferToTest() {
        try(FileChannel from = new FileInputStream("files/from.txt").getChannel();
            FileChannel to = new FileOutputStream("files/to.txt").getChannel()){
            // 获取文件大小
            long size = from.size();
            log.debug("文件大小：{}", size);
            // 效率高，会使用系统底层零拷贝进行优化
            long currentTransferDataLength = from.transferTo(0, from.size(), to);
            System.out.println("本次传输的数据长度 = " + currentTransferDataLength + "byte");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
