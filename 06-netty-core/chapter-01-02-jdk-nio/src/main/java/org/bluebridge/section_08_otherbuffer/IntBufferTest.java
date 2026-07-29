package org.bluebridge.section_08_otherbuffer;

import org.bluebridge.ByteBufferUtil;
import org.junit.Test;

import java.nio.IntBuffer;

/**
 * ByteBuffer 基础使用（示例演示IntBuffer，属于Buffer子类）
 * 三大核心属性：capacity容量、position当前位置、limit读写上限
 *
 * @author lingwh
 * @date 2026/7/29 15:08
 */
public class IntBufferTest {

    /**
     * ByteBuffer 基础使用
     */
    @Test
    public void testIntBuffer() {
        // 1. 创建缓冲区，最多存放 5 个int数字
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 2. 写入数据，position向后移动
        intBuffer.put(11);
        intBuffer.put(22);
        intBuffer.put(33);
        System.out.println("写入之后 position=" + intBuffer.position() + ", limit=" + intBuffer.limit());

        // 3. flip()：写模式切换读模式
        // limit=position，position重置为0，准备读取已写入的数据
        intBuffer.flip();
        System.out.println("flip之后 position=" + intBuffer.position() + ", limit=" + intBuffer.limit());

        // 4. 循环读取缓冲区数据
        while (intBuffer.hasRemaining()) {
            int value = intBuffer.get();
            System.out.print(value + " ");
        }
        System.out.println();

        // 5. rewind()：position回到起点，可以再次重读
        intBuffer.rewind();
        System.out.print("rewind重读：");
        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + " ");
        }
        System.out.println();

        // 6. mark标记位置、reset回到标记点
        intBuffer.rewind();
        intBuffer.get();
        intBuffer.mark();  // 标记当前位置
        intBuffer.get();
        intBuffer.reset(); // position恢复到mark位置
        System.out.println("mark位置数据：" + intBuffer.get());

        // 7. clear()：切换回写模式，position=0，limit=capacity；原有数据不会清除，可被覆盖
        intBuffer.clear();
        System.out.println("clear之后 position=" + intBuffer.position() + ", limit=" + intBuffer.limit());

        // 8. 批量写入数组
        int[] arr = {100, 200};
        intBuffer.put(arr);
        intBuffer.flip();
        System.out.print("批量写入读取：");
        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + " ");
        }
    }
}
