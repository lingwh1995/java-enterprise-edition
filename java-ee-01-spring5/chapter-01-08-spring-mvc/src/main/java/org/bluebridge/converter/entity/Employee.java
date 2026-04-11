package org.bluebridge.converter.entity;

/**
 * @author lingwh
 * @desc   Employee实体类
 * @date   2019/6/17 14:02
 */
public class Employee {

	private String id;

	private String username;

	private String password;

	private String age;

	public Employee() {

	}

	public Employee(String id, String username, String password, String age) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", age=" + age +
				'}';
	}
}
