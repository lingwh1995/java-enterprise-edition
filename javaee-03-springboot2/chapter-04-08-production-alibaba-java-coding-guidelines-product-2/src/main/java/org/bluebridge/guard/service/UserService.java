package org.bluebridge.guard.service;


import org.bluebridge.guard.domain.dto.UserLoginDTO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:25
 */
public interface UserService {

    boolean login(UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException, InvalidKeySpecException;

}
