package org.bluebridge.ioc.profile.dao;

import org.bluebridge.ioc.profile.domain.User;

/**
 * @ClassName: Dog  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月19日  
 *    
 */
public class UserDao {

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public void say(){
		System.out.println("Hello!My Name Is "+user.getName());
	}
}

