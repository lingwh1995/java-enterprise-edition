package org.bluebridge._20_group_chat.message;

public class PongMessage extends Message {

    public PongMessage(int messageType) {
        super(PongMessage);
    }

    @Override
    public int getMessageType() {
        return PongMessage;
    }

}
