package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

/**
 * @author lingwh
 * @desc 聊天组，即聊天室
 * @date 2025/11/01 17:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    // 聊天组名称
    private String groupName;
    // 聊天组成员
    private Set<String> members;
    // 聊天组创建者
    private String owner;

    public static final Group EMPTY_GROUP = new Group("empty", Collections.emptySet(), "");

}
