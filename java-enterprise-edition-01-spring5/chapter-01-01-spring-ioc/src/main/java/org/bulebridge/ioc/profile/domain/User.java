package org.bulebridge.ioc.profile.domain;

/**  
 * @ClassName: User  
 * @Description: TODO(这里用一句话描述这个类的作用)  
 * @author ronin  
 * @date 2019年3月16日  
 *    
 */
public class User {
	private String name;
	private Integer age;
	/**  
	 * 创建一个新的实例 User.  
	 *    
	 */ 
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**  
	 * 创建一个新的实例 User.  
	 *  
	 * @param name
	 * @param age  
	 */ 
	public User(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * <p>Title: toString</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see Object#toString()
	 */  
	
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
}
