/**  
 * @Title: UserController.java  
 * @Package com.dragonsoft.spring.ioc.controller  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author ronin  
 * @date 2019年3月19日  
 * @version V1.0  
 */ 
package com.dragonsoft.ioc.profile.controller;

import com.dragonsoft.ioc.profile.service.IUserService;


/**  
 * @ClassName: UserController  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月19日  
 *    
 */
public class UserController {
	private IUserService userServiceImpl;
	
	public void setUserServiceImpl(IUserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public void say(){
		userServiceImpl.say();
	}
}
