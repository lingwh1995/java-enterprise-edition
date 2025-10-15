package org.bluebridge._18_protocol_design.custom_protocol_codec;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/15 17:28
 */
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message implements Serializable {

    private int sequenceId;
    private int messageType;

}
