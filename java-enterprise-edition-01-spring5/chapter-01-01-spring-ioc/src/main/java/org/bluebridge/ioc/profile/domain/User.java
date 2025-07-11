package org.bluebridge.ioc.profile.domain;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/16 14:30
 */
public class User {
	private String name;
	private Integer age;

	public User() {
	}

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

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
