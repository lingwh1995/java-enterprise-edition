package com.xa8bit.security.mapper;

import com.xa8bit.security.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:23
 */
@Mapper
public interface UserMapper {

    User select(User user);

}
