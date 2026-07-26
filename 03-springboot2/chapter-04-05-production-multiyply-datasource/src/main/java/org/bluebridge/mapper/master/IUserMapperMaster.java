package org.bluebridge.mapper.master;

import org.bluebridge.domain.User;

/**
 * 主库用户 Mapper 接口
 *
 * @author lingwh
 * @date 2024/11/14 16:01
 */
public interface IUserMapperMaster {

    User getUserById(int id);
}
