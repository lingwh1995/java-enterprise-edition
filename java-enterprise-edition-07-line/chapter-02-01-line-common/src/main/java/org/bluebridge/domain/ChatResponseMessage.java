package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ChatResponseMessage extends AbstractResponseMessage {

    private String from;
    private String content;

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    /**
     * 这里不使用lombok的@AllArgsConstructor注解了,因为要给 success 赋值
     * @param from
     * @param content
     */
    public ChatResponseMessage(String from, String content) {
        super.setSuccess(true);
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return Message.CHAT_RESPONSE_MESSAGE;
    }

}
