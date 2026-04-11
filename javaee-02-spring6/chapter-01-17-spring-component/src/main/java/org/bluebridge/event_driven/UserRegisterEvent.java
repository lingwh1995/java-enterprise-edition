package org.bluebridge.event_driven;

import org.springframework.context.ApplicationEvent;

/**
 * @author lingwh
 * @desc
 * @date 2026/1/10 12:30
 */
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 传递的数据
     */
    private String username;

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() { return username; }

}
