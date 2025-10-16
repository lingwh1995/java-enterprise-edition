package org.bluebridge._14_unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * @author lingwh
 * @desc Unpooled 是 Netty 提供的一个工具类，用于创建和操作 ByteBuf 实例。
 * @date 2025/10/10 15:05
 */

/**
 * 主要功能：
 * - 创建各种类型的 ByteBuf 缓冲区（堆内存、直接内存等）
 * - 提供零拷贝的缓冲区组合和切片操作
 * - 包含常用的缓冲区工具方法
 *
 * 核心方法：
 * - buffer()：创建堆内存缓冲区
 * - directBuffer()：创建直接内存缓冲区
 * - wrappedBuffer()：包装现有字节数组或 ByteBuf，支持零拷贝组合
 * - copiedBuffer()：复制字节数组创建新的缓冲区
 *
 * 特点：
 * - 不依赖于内存池，直接分配和释放内存
 * - 适用于临时缓冲区或简单场景
 * - wrappedBuffer() 方法支持零拷贝方式组合多个 ByteBuf
 */
public class UnpooledTest {

    public static void main(String[] args) {
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.buffer(5);
        byteBuf1.writeBytes(new byte[]{ 1, 2, 3, 4, 5 });
        ByteBuf byteBuf2 = ByteBufAllocator.DEFAULT.buffer(5);
        byteBuf2.writeBytes(new byte[]{ 6, 7, 8, 9, 10 });

        // 当包装 ByteBuf 个数超过一个时，底层使用了 CompositeByteBuf，wrappedBuffer()是零拷贝方法
        ByteBuf byteBuf3 = Unpooled.wrappedBuffer(byteBuf1, byteBuf2);
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf3));
    }

}
