package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyinterface;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * cglib代理，可以直接为一个类或者一个接口生成一个代理对象
 * 		本包下代码仅展示了cglib为接口创建代理，不包含cglib为类创建代理
 */
public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		//1.创建被目标对象
		UserServiceImpl userService = new UserServiceImpl();
		//2.创建代理对象
		ProxyFactory proxyFactory = new ProxyFactory(userService);
		//3.调用代理对象的方法
		IUserService proxyInstance = (IUserService)proxyFactory.getProxyInstance();
		//删除001用户
		proxyInstance.deleteById("001");
		//查询，注意001已经删除了，要查就查002这一条数据
		logger.info("id=004的用户信息:" + userService.getById("002"));
	}
}
