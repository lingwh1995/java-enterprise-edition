package org.bluebridge.mapper.slave;

import org.bluebridge.domain.User;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
public interface IUserMapper {

    User getUserById(int id);
}
