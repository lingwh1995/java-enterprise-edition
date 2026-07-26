package org.bluebridge.service;

import org.bluebridge.domain.User;

/**
 * 用户服务接口
 *
 * @author lingwh
 * @date 2024/9/2 11:25
 */
public interface IUserService {

    User findById(String id);
}
