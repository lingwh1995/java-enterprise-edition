package org.bluebridge.service.master.impl;

import org.bluebridge.domain.User;
import org.bluebridge.mapper.master.IUserMapperMaster;
import org.bluebridge.service.master.IUserServiceMaster;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
@Service
public class UserServiceImplMaster implements IUserServiceMaster {

    @Resource
    private IUserMapperMaster userMapperMaster;

    @Override
    public User getUserById(int id) {
        return userMapperMaster.getUserById(id);
    }
}
