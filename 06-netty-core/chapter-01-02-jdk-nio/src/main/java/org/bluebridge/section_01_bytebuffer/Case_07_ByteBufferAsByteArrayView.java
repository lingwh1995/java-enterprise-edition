package org.bluebridge.section_01_bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * 使用 ByteBuffer 作为 byte 数组视图
 *
 * 经典应用场景
 * 1. 二进制协议解析
 *    网络数据包是一串 byte，一会读 int、一会读 short、一会读字符串，来回切换视图，代码极其清爽。
 * 2. Hadoop 环形缓冲区
 *    双向对向写入得以实现，排序只操作索引（int 数组视图），不需要挪动大量 key-value 字节，大幅提升排序性能。
 * 3. 字节拼接/报文拼接
 *    使用 ByteBuffer/IntBuffer 等视图类替代手动字节拼装（system.arraycopy() + 位运算）
 *
 * ByteBuffer 扩展延伸
 * 1. ByteBuffer、IntBuffer 首要定位：连续二进制数据的统一读写抽象
 *    支持多种内存来源
 *    - JVM 堆内 byte []
 *    - 堆外操作系统内存（DirectBuffer）
 *    - 提供一套统一 API：position /limit/capacity、get/put、批量读写，方便 IO 通道（Channel）读写。
 * 2. 视图功能 asIntBuffer () /asLongBuffer () 是什么角色?
 *    基于同一个底层内存，更换 "数据解析单位"
 *    - ByteBuffer：单位 = 1 byte
 *    - IntBuffer：单位 = 4 byte，自动完成 int ↔ 4 字节编解码
 *    视图不会分配新内存，只是增加一层解析逻辑，只维护自身 position/limit。
 * 3. 要理解 ByteBuffer 绝对不是天生被设计专门用来充当 byte [] 的包装视图，这只是其扩展功能。
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
        intBuffer.put(8);
        // 这里不查看 IntBuffer 中的数据，而是查看 bytes 中的数据
        System.out.println(Arrays.toString(bytes));
    }
}