package org.bluebridge.section_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * 使用 ByteBuffer 作为 byte 数组视图
 *
 * @author lingwh
 * @date 2026/7/29 15:39
 */
@Slf4j
public class Case_07_ByteBufferAsByteArrayView {

    /**
     * 字符串转换为 ByteBuffer
     */
    @Test
    public void testByteBufferAsByteArrayView() {
        // 创建一个 byte 数组
        byte[] bytes = new byte[1000];
        // 创建一个 ByteBuffer 作为 byte 数组视图，其持有 byte 数组的引用
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        // 给 ByteBuffer 写入数据
        byteBuffer.put("abcde".getBytes());
        // 这里不查看 ByteBuffer 中的数据，而是查看 bytes 中的数据
        System.out.println(Arrays.toString(bytes));

        // 创建一个 IntBuffer 作为 ByteBuffer 的视图
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        // 给 IntBuffer 写入数据
        intBuffer.put(1);
        // 这里不查看 IntBuffer 中的数据，而是查看 bytes 中的数据
        System.out.println(Arrays.toString(bytes));
    }
}
