package org.bluebridge.section_19_protocol_design.unit_04_custom_protocol_codec;

/**
 * 消息基类
 *
 * @author lingwh
 * @date 2025/10/15 17:28
 */
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message implements Serializable {

    private int sequenceId;
    private int messageType;
}
