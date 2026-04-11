package org.bluebridge.ioc.profile.dao;

import org.bluebridge.ioc.profile.domain.User;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/19 14:22
 */
public class UserDao {

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public void say(){
		System.out.println("Hello! My Name Is " + user.getName() + "!");
	}
}

