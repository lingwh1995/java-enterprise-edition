package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyinterface;


public class UserServiceImpl implements IUserService {
    private UserDao userDao = new UserDao();

    @Override
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    @Override
    public User getById(String id) {
        return userDao.getById(id);
    }

    @Override
    public void showUsers() {
        userDao.showUsers();
    }
}