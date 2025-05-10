package org.bluebridge.ioc.profile.service;

import org.bluebridge.ioc.profile.dao.UserDao;

/**
 * @ClassName: UserServiceImpl  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月19日  
 *    
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
