package org.bluebridge.security.service;

import org.bluebridge.security.domain.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:25
 */
public interface UserService {

    boolean login(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
