package org.bluebridge.server.service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceMemoryImpl implements UserService {

    private static Map<String, String> ALL_USER_MAP = new ConcurrentHashMap<>();

    static {
        ALL_USER_MAP.put("zhangsan", "123456");
        ALL_USER_MAP.put("lisi", "123456");
        ALL_USER_MAP.put("wangwu", "123456");
        ALL_USER_MAP.put("zhaoliu", "123456");
        ALL_USER_MAP.put("sunqi", "123456");
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
