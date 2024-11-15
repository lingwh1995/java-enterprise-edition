package org.bluebridge.service.slave.impl;

import org.bluebridge.domain.User;
import org.bluebridge.mapper.slave.IUserMapperSlave;
import org.bluebridge.service.slave.IUserServiceSlave;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
@Service
public class UserServiceImplSlave implements IUserServiceSlave {

    @Resource
    private IUserMapperSlave userMapperSlave;

    @Override
    public User getUserById(int id) {
        return userMapperSlave.getUserById(id);
    }
}
