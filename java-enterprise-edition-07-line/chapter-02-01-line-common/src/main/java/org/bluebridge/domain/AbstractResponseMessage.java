package org.bluebridge.domain;

/**
 * @author lingwh
 * @desc 抽象响应消息
 * @date 2025/10/25 17:26
 */
public abstract class AbstractResponseMessage implements Message {

    protected int sequenceId;
    protected int messageType;
    protected boolean success;
    protected String reason;

    public AbstractResponseMessage() {
        this.messageType = getMessageType();
    }

}
