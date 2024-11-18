package org.bluebridge.service;

import org.bluebridge.domain.User;

/**
 * @author ronin
 */
public interface IUserService {

    /**
     * 根据id获取User
     * @param id
     * @return
     */
    User getUserById(String id);
}
