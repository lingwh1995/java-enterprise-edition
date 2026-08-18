package org.bluebridge.section_20_rpc.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 协议帧格式解码器
 *
 * @author lingwh
 * @date 2025/10/24 10:58
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProcotolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
