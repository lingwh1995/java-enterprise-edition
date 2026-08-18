package org.bluebridge.section_20_rpc.protocol;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SequenceIdGenerator
 *
 * @author lingwh
 * @date 2025/11/2 0:28
 */
public abstract class SequenceIdGenerator {

    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId() {
        return id.incrementAndGet();
    }
}
