package org.bluebridge.chapter_01;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class _08_FileChannelTransferToTest {

    @Test
    public void FileChannelTransferToTest() {
        try(FileChannel from = new FileInputStream("from.txt").getChannel();
            FileChannel to = new FileOutputStream("to.txt").getChannel()){
            // 效率高，系统地层会使用零拷贝进行优化
            from.transferTo(0, from.size(), to);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
