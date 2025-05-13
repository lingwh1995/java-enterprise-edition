package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass.proxyclass_1;

/**
 * java8以上环境运行时需要添加VM参数，否则会报错   --add-opens java.base/java.lang=ALL-UNNAMED
 */
public class Client {

    public static void main(String[] args) {
        // proxy
        UserServiceImpl userService = (UserServiceImpl) new UserLogProxy().getUserLogProxy(new UserServiceImpl());

        // call methods
        userService.findUserList();
        userService.addUser();
    }

}
