package org.bluebridge.designpattern.proxy.staticproxy.staticproxy2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImplProxy implements IUserService{

    private static final Logger logger = LogManager.getLogger(UserServiceImplProxy.class);

    private IUserService target;

    public UserServiceImplProxy(IUserService target) {
        this.target = target;
    }

    boolean isAdmain = false;

    @Override
    public void addUser(User user) {
        target.addUser(user);
    }

    @Override
    public void deleteUserById(String id) {
        if(isAdmain) {
            target.deleteUserById(id);
        }else {
            logger.info("当前用户不是管理员用户,无法执行删除操作.....");
        }
    }

    @Override
    public void updateUser(User user) {
        target.updateUser(user);
    }

    @Override
    public User getUserById(String id) {
        User user = null;
        if(isAdmain) {
            user = target.getUserById(id);
        }else {
            user = target.getUserById(id);
            user.setAge(null);
        }
        return user;
    }
}
