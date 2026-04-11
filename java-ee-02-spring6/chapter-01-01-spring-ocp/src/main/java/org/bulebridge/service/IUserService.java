package org.bulebridge.service;

import org.bulebridge.domain.User;

public interface IUserService {
    User findUserByUserId(String userId);
}
