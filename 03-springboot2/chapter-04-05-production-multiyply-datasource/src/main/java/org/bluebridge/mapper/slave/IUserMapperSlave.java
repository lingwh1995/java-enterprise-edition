package org.bluebridge.mapper.slave;

import org.bluebridge.domain.User;

/**
 * 从库用户 Mapper 接口
 *
 * @author lingwh
 * @date 2024/11/14 16:05
 */
public interface IUserMapperSlave {

    User getUserById(int id);
}
