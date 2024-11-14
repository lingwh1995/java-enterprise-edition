package org.bluebridge.mapper;

import org.bluebridge.domain.User;

import java.util.List;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
public interface IUserDao {

    User getUserById(int id);

    List<User> getAllUsers();
}
