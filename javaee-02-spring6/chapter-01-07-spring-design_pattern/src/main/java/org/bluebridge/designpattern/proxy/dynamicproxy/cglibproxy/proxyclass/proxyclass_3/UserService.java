package org.bluebridge.designpattern.proxy.dynamicproxy.cglibproxy.proxyclass.proxyclass_3;


public class UserService {

	private UserDao userDao = new UserDao();

	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	public User getById(String id) {
		return userDao.getById(id);
	}

	public void showUsers() {
		userDao.showUsers();
	}
}
