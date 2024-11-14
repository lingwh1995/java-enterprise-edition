package org.bluebridge.service.slave;

import org.bluebridge.domain.User;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
public interface IUserService {

    User getUserById(int id);
}
