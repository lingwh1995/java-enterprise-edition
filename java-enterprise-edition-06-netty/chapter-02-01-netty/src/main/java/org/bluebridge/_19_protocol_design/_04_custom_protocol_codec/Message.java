package org.bluebridge._19_protocol_design._04_custom_protocol_codec;

/**
 * @author lingwh
 * @desc 消息基类
 * @date 2025/10/15 17:28
 */
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message implements Serializable {

    private int sequenceId;
    private int messageType;

}
