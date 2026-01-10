package org.bluebridge.event_driven;

import org.bluebridge.aware.SpringUtils;
import org.springframework.stereotype.Service;

/**
 * @author lingwh
 * @desc
 * @date 2026/1/10 12:31
 */
@Service
public class UserService {

    public void register(String name) {
        // 打印注册成功日志
        System.out.println("用户注册成功：" + name);
        // 使用 SpringUtils 发布事件，或者注入 ApplicationEventPublisher
        SpringUtils.publishEvent(new UserRegisterEvent(this, name));
    }

}
