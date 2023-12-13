/**  
 * @Title: Dog.java  
 * @Package com.dragonsoft.spring.ioc.entity  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author ronin  
 * @date 2019年3月19日  
 * @version V1.0  
 */ 
package com.dragonsoft.ioc.profile.dao;

import com.dragonsoft.ioc.profile.domain.User;

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

