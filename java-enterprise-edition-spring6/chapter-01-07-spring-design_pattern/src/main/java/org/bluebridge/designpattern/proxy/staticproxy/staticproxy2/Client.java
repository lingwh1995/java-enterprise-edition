package org.bluebridge.designpattern.proxy.staticproxy.staticproxy2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        //1.创建目标对象
        UserServiceImpl target = new UserServiceImpl();
        //2.创建代理对象
        UserServiceImplProxy userServiceImplProxy = new UserServiceImplProxy(target);
        //3.调用代理对象的代理方法
        userServiceImplProxy.addUser(new User("001","张三",25));
        userServiceImplProxy.deleteUserById("001");
        userServiceImplProxy.updateUser(new User("001","张三修改后",25));
        User user = userServiceImplProxy.getUserById("001");
        logger.info(user.toString());
    }
}
