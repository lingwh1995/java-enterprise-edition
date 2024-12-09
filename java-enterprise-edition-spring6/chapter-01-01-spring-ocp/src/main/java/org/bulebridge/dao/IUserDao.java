package org.bulebridge.dao;

import org.bulebridge.domain.User;

public interface IUserDao {
    User findUserByUserId(String userId);
}
