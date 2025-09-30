package org.bluebridge._08_bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lingwh
 * @desc Netty中的ByteBuf
 * @date 2025/9/24 15:50
 */
@Slf4j
public class ByteBufTest {

    @Test
    public void testByteBufHelloWorld() {
        // ByteBuf可以动态扩容(初始为256)
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        log.info("buffer： {}", buffer);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }

        // 写入字节数组
        buffer.writeBytes(sb.toString().getBytes());
        // 扩容至512
        log.info("buffer： {}", buffer);
    }

    /**
     *  1.创建ByteBuf
     *     直接内存创建和销毁的代价昂贵，但读写性能高（少一次内存复制），适合配合池化功能一起用
     *     直接内存对 GC 压力小，因为这部分内存不受 JVM 垃圾回收的管理，但也要注意及时主动释放
     *  2.池化与非池化
     *     池化的最大意义在于可以重用 ByteBuf
     *     没有池化，则每次都得创建新的 ByteBuf 实例，这个操作对直接内存代价昂贵，就算是堆内存，也会增加 GC 压力
     *     有了池化，则可以重用池中 ByteBuf 实例，并且采用了与 jemalloc 类似的内存分配算法提升分配效率
     *     高并发时，池化功能更节约内存，减少内存溢出的可能
     *  3.池化功能是否开启，可以通过下面的系统环境变量来设置
     *      -Dio.netty.allocator.type={unpooled|pooled}
     *  4.ByteBuf主要有以下几个组成部分
     *     最大容量与当前容量
     *         在构造ByteBuf时，可传入两个参数，分别代表初始容量和最大容量，若未传入第二个参数（最大容量），最大容量默认为Integer.MAX_VALUE
     *         当ByteBuf容量无法容纳所有数据时，会进行扩容操作，若超出最大容量，会抛出java.lang.IndexOutOfBoundsException异常
     *  5.ByteBuf的读写
     *     读写操作不同于ByteBuffer只用position进行控制，ByteBuf分别由读指针和写指针两个指针控制
     *     进行读写操作时，无需进行模式的切换
     *        读指针前的部分被称为废弃部分，是已经读过的内容
     *        读指针与写指针之间的空间称为可读部分
     *        写指针与当前容量之间的空间称为可写部分
     */
    @Test
    public void testByteBufAllocator() {
        // 直接内存
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(16);
        // 堆内存
        buffer = ByteBufAllocator.DEFAULT.heapBuffer(16);
        // 创建池化基于直接内存的 ByteBuf
        buffer = ByteBufAllocator.DEFAULT.directBuffer(16);
    }

}
