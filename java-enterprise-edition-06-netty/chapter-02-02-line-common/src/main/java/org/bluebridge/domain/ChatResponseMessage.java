package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
public class ChatResponseMessage extends AbstractResponseMessage {

    private String from;
    private String content;

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return ChatResponseMessage;
    }

}
