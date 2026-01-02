package org.bluebridge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.bluebridge.domain.entity.UserDO;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:23
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param userDO
     * @return
     */
    UserDO selectUserByUsername(UserDO userDO);

}
