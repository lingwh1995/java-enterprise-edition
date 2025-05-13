package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass.proxyclass_1;


import java.util.Collections;
import java.util.List;


/**
 * UserService
 */
public class UserServiceImpl {
    /**
     * find user list.
     *
     * @return user list
     */
    public List<User> findUserList() {
        return Collections.singletonList(new User("流华追梦", 18));
    }

    /**
     * add user
     */
    public void addUser() {
        // do something
    }
}