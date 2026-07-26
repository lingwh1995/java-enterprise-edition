package org.bluebridge.service;

import org.bluebridge.domain.User;

/**
 * 用户服务接口
 *
 * @author lingwh
 * @date 2019/11/19 13:40
 */
public interface IUserService {

    /**
     * 根据 id 获取 User
     * @param id
     * @return
     */
    User getUserById(String id);
}
