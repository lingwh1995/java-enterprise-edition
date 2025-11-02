package org.bluebridge.domain;

/**
 * @author lingwh
 * @desc 抽象请求消息
 * @date 2025/10/25 17:33
 */
public abstract class AbstractRequestMessage implements Message {

    protected int sequenceId;
    protected int messageType;

    public AbstractRequestMessage() {
        this.messageType = getMessageType();
    }
}
