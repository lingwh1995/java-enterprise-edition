package org.bluebridge.security.service.impl;

import org.bluebridge.common.util.PasswordUtils;
import org.bluebridge.security.converter.UserConverter;
import org.bluebridge.security.domain.UserDO;
import org.bluebridge.security.domain.UserLoginDTO;
import org.bluebridge.security.mapper.UserMapper;
import org.bluebridge.security.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:27
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserConverter userConverter;

    @Override
    public boolean login(UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 1. 判空拦截（防御式编程）
        if (userLoginDTO == null) {
            return false;
        }
        // 2.转换为数据库对象
        UserDO userDO = userConverter.toUserDO(userLoginDTO);

        // 3. 语义化命名：强调这是从数据库查出的“持久化”或“已存在”用户
        UserDO existingUser = userMapper.selectUserByUsername(userDO);

        // 4. 快速失败原则
        if (existingUser == null) {
            return false;
        }

        // 5. 清晰的参数对比：userLoginDTO 提供明文密码，existingUser 提供盐值和加密后的密文
        return PasswordUtils.verify(
                userLoginDTO.getPassword(),
                existingUser.getSalt(),
                existingUser.getPassword()
        );
    }

}
