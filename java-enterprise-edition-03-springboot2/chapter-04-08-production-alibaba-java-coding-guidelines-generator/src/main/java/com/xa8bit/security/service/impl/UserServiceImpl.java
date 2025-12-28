package com.xa8bit.security.service.impl;

import com.xa8bit.security.domain.User;
import com.xa8bit.security.mapper.UserMapper;
import com.xa8bit.security.service.UserService;
import com.xa8bit.common.util.PasswordUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:27
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User userFromDb = userMapper.select(user);
        if(Objects.nonNull(userFromDb)) {
            String salt = userFromDb.getSalt();
            return PasswordUtils.verify(user.getPassword(), salt, userFromDb.getPassword());
        }
        return false;
    }

}
