package org.bluebridge.ioc.profile.service;

import org.bluebridge.ioc.profile.dao.UserDao;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/19 14:32
 */
public class UserServiceImpl implements IUserService{

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void say() {
		userDao.say();
	}

}
