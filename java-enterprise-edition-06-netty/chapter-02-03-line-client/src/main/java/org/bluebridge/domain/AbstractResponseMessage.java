package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author lingwh
 * @desc 抽象响应消息
 * @date 2025/10/25 17:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class AbstractResponseMessage extends Message {

    private boolean success;
    private String reason;

}
