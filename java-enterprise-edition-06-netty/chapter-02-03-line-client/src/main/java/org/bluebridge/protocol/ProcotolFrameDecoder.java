package org.bluebridge.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author lingwh
 * @desc 协议帧格式解码器(解决粘包半包问题)
 * @date 2025/10/25 12:26
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProcotolFrameDecoder() {
        this(1024, 12, 4, 0, 0);
    }

    public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                                int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
