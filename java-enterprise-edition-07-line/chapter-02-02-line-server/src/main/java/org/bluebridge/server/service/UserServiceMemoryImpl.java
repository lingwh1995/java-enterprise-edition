package org.bluebridge.server.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingwh
 * @desc
 * @date 2025/10/25 11:20
 */
@Service
public class UserServiceMemoryImpl implements IUserService{

    private static final Map<String, String> ALL_USER_MAP = new ConcurrentHashMap<>();

    static {
        ALL_USER_MAP.put("001", "123");
        ALL_USER_MAP.put("002", "123");
        ALL_USER_MAP.put("003", "123");
        ALL_USER_MAP.put("004", "123");
        ALL_USER_MAP.put("005", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String passwd = ALL_USER_MAP.get(username);
        if(null == passwd) {
            return false;
        }
        return password.equals(passwd);
    }

}
