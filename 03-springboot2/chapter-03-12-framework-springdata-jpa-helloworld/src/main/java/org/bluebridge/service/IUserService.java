package org.bluebridge.service;

import org.bluebridge.domain.User;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/19 13:45
 */
public interface IUserService {
    User findById(String id);
}
