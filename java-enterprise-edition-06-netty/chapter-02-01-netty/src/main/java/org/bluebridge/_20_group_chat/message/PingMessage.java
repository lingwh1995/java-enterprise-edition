package org.bluebridge._20_group_chat.message;

public class PingMessage extends Message {

    public PingMessage(int messageType) {
        super(PingMessage);
    }

    @Override
    public int getMessageType() {
        return PingMessage;
    }

}
