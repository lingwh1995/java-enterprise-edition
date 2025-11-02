package org.bluebridge.domain;


import java.io.Serializable;

/**
 * @author lingwh
 * @desc 消息标记接口
 * @date 2025/10/25 12:41
 */
public interface Message extends Serializable {

    /**
     * 获取消息类型
     * @return
     */
    int getMessageType();

    /**
     * 获取消息序列号
     * @return
     */
    default int getSequenceId() {
        return 0;
    };

}
