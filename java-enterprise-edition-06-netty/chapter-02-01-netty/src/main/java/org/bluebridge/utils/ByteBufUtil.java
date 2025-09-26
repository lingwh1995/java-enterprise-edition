package org.bluebridge.utils;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;

import static io.netty.util.internal.MathUtil.isOutOfBounds;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * 模拟 ByteBufferUtil，用于调试 Netty ByteBuf 的工具类
 * 提供打印完整内容和可读内容的十六进制+ASCII 格式输出
 */
public class ByteBufUtil {

    private static final char[] BYTE2CHAR = new char[256];
    private static final char[] HEXDUMP_TABLE = new char[256 * 4];
    private static final String[] HEXPADDING = new String[16];
    private static final String[] HEXDUMP_ROWPREFIXES = new String[65536 >>> 4];
    private static final String[] BYTE2HEX = new String[256];
    private static final String[] BYTEPADDING = new String[16];

    static {
        // 初始化十六进制和ASCII转换表（与 ByteBufferUtil 逻辑一致）
        final char[] DIGITS = "0123456789abcdef".toCharArray();
        for (int i = 0; i < 256; i++) {
            HEXDUMP_TABLE[i << 1] = DIGITS[i >>> 4 & 0x0F];
            HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i & 0x0F];
        }

        int i;
        // 初始化十六进制填充字符串
        for (i = 0; i < HEXPADDING.length; i++) {
            int padding = HEXPADDING.length - i;
            StringBuilder buf = new StringBuilder(padding * 3);
            for (int j = 0; j < padding; j++) buf.append("   ");
            HEXPADDING[i] = buf.toString();
        }

        // 初始化行前缀（偏移量标识）
        for (i = 0; i < HEXDUMP_ROWPREFIXES.length; i++) {
            StringBuilder buf = new StringBuilder(12);
            buf.append(NEWLINE);
            buf.append(Long.toHexString(i << 4 & 0xFFFFFFFFL | 0x100000000L));
            buf.setCharAt(buf.length() - 9, '|');
            buf.append('|');
            HEXDUMP_ROWPREFIXES[i] = buf.toString();
        }

        // 初始化字节转十六进制字符串
        for (i = 0; i < BYTE2HEX.length; i++) {
            BYTE2HEX[i] = ' ' + StringUtil.byteToHexStringPadded(i);
        }

        // 初始化ASCII填充字符串
        for (i = 0; i < BYTEPADDING.length; i++) {
            int padding = BYTEPADDING.length - i;
            StringBuilder buf = new StringBuilder(padding);
            for (int j = 0; j < padding; j++) buf.append(' ');
            BYTEPADDING[i] = buf.toString();
        }

        // 初始化字节转ASCII字符（不可见字符用 '.' 代替）
        for (i = 0; i < BYTE2CHAR.length; i++) {
            BYTE2CHAR[i] = (i <= 0x1f || i >= 0x7f) ? '.' : (char) i;
        }
    }

    /**
     * 打印 ByteBuf 所有内容（从 0 到 capacity）
     */
    public static void debugAll(ByteBuf buf) {
        ByteBuf duplicate = buf.duplicate(); // 复制缓冲区，避免修改原始索引
        int originalReaderIndex = duplicate.readerIndex();
        int originalWriterIndex = duplicate.writerIndex();

        // 临时设置为可读所有内容（0 到 capacity）
        duplicate.readerIndex(0);
        duplicate.writerIndex(buf.capacity());

        StringBuilder dump = new StringBuilder(256);
        appendPrettyHexDump(dump, duplicate, 0, duplicate.readableBytes());
        System.out.println("+--------+-------------------- all ------------------------+----------------+");
        System.out.printf("readerIndex: [%d], writerIndex: [%d], capacity: [%d]\n", 
                         buf.readerIndex(), buf.writerIndex(), buf.capacity());
        System.out.println(dump);

        // 恢复复制缓冲区的原始索引（不影响原始缓冲区）
        duplicate.readerIndex(originalReaderIndex);
        duplicate.writerIndex(originalWriterIndex);
    }

    /**
     * 打印 ByteBuf 可读内容（从 readerIndex 到 writerIndex）
     */
    public static void debugRead(ByteBuf buf) {
        StringBuilder dump = new StringBuilder(256);
        int offset = buf.readerIndex();
        int length = buf.readableBytes();
        appendPrettyHexDump(dump, buf, offset, length);
        System.out.println("+--------+-------------------- read -----------------------+----------------+");
        System.out.printf("readerIndex: [%d], writerIndex: [%d]\n", 
                         buf.readerIndex(), buf.writerIndex());
        System.out.println(dump);
    }

    /**
     * 生成十六进制+ASCII格式的 ByteBuf 内容（核心逻辑）
     */
    private static void appendPrettyHexDump(StringBuilder dump, ByteBuf buf, int offset, int length) {
        if (isOutOfBounds(offset, length, buf.capacity())) {
            throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + ", capacity: " + buf.capacity());
        }
        if (length == 0) {
            return;
        }

        // 头部标题
        dump.append("         +-------------------------------------------------+" +
                    NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" +
                    NEWLINE + "+--------+-------------------------------------------------+----------------+");

        final int startIndex = offset;
        final int fullRows = length >>> 4; // 完整行（16字节/行）
        final int remainder = length & 0xF; // 当前行剩余字节

        // 打印完整行
        for (int row = 0; row < fullRows; row++) {
            int rowStartIndex = (row << 4) + startIndex;
            appendHexDumpRowPrefix(dump, row, rowStartIndex); // 行前缀（偏移量）

            // 十六进制部分
            for (int j = rowStartIndex; j < rowStartIndex + 16; j++) {
                dump.append(BYTE2HEX[getUnsignedByte(buf, j)]);
            }
            dump.append(" |");

            // ASCII部分
            for (int j = rowStartIndex; j < rowStartIndex + 16; j++) {
                dump.append(BYTE2CHAR[getUnsignedByte(buf, j)]);
            }
            dump.append('|');
        }

        // 打印剩余字节行
        if (remainder != 0) {
            int rowStartIndex = (fullRows << 4) + startIndex;
            appendHexDumpRowPrefix(dump, fullRows, rowStartIndex);

            // 十六进制部分（带填充）
            for (int j = rowStartIndex; j < rowStartIndex + remainder; j++) {
                dump.append(BYTE2HEX[getUnsignedByte(buf, j)]);
            }
            dump.append(HEXPADDING[remainder]); // 不足16字节补空格
            dump.append(" |");

            // ASCII部分（带填充）
            for (int j = rowStartIndex; j < rowStartIndex + remainder; j++) {
                dump.append(BYTE2CHAR[getUnsignedByte(buf, j)]);
            }
            dump.append(BYTEPADDING[remainder]); // 不足16字节补空格
            dump.append('|');
        }

        // 尾部分隔线
        dump.append(NEWLINE + "+--------+-------------------------------------------------+----------------+");
    }

    // 辅助方法：获取无符号字节值
    private static short getUnsignedByte(ByteBuf buf, int index) {
        return (short) (buf.getByte(index) & 0xFF);
    }

    // 辅助方法：添加行前缀（偏移量标识）
    private static void appendHexDumpRowPrefix(StringBuilder dump, int row, int rowStartIndex) {
        if (row < HEXDUMP_ROWPREFIXES.length) {
            dump.append(HEXDUMP_ROWPREFIXES[row]);
        } else {
            dump.append(NEWLINE);
            dump.append(Long.toHexString(rowStartIndex & 0xFFFFFFFFL | 0x100000000L));
            dump.setCharAt(dump.length() - 9, '|');
            dump.append('|');
        }
    }
}
