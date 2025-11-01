package org.bluebridge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Set;

/**
 * @author lingwh
 * @desc 聊天组，即聊天室
 * @date 2025/11/01 17:05
 */
@Data
@AllArgsConstructor
public class Group {

    // 聊天室名称
    private String name;
    // 聊天室成员
    private Set<String> members;

    public static final Group EMPTY_GROUP = new Group("empty", Collections.emptySet());

}
