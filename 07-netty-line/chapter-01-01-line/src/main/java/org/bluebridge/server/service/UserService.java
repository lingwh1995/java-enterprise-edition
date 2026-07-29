package org.bluebridge.server.service;

/**
 * 用户管理接口
 *
 * @author lingwh
 * @date 2025/10/16 17:15
 */
public interface UserService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回 true ，否则返回 false
     */
    boolean login(String username, String password);
}
