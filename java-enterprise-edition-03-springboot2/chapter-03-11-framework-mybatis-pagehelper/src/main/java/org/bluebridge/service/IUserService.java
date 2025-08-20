package org.bluebridge.service;

import org.apache.ibatis.annotations.Select;
import org.bluebridge.domain.User;

import java.util.List;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
public interface IUserService {

    /**
     * 查询所有的User对象
     * @return
     */
    List<User> getAllUsers();

    /**
     * 根据id查询User
     * @param id
     * @return
     */
    User getUserById(int id);
}
