package org.bluebridge.server.service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceMemoryImpl implements UserService {

    private static Map<String, String> ALL_USER_MAP = new ConcurrentHashMap<>();

    static {
        ALL_USER_MAP.put("001", "123");
        ALL_USER_MAP.put("002", "123");
        ALL_USER_MAP.put("003", "123");
        ALL_USER_MAP.put("004", "123");
        ALL_USER_MAP.put("005", "123");
    }

    @Override
    public boolean login(String username, String password) {
        String pass = ALL_USER_MAP.get(username);
        if (pass == null) {
            return false;
        }
        return pass.equals(password);
    }

}
