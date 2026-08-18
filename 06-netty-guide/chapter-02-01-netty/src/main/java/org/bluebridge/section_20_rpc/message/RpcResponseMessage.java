package org.bluebridge.section_20_rpc.message;

import lombok.Data;
import lombok.ToString;

/**
 * RpcResponseMessage
 *
 * @author lingwh
 * @date 2025/12/13 11:40
 */
@Data
@ToString(callSuper = true)
public class RpcResponseMessage extends Message {

    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }
}
