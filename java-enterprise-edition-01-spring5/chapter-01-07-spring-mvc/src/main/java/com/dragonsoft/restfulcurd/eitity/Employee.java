package com.dragonsoft.restfulcurd.eitity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Past;
import java.util.Date;


public class Employee {

	private Integer id;

	@NotEmpty(message = "lastName不能为空")
	private String lastName;

	@Email(message = "不合法的邮箱格式")
	private String email;
	//1 male, 0 female
	private Integer gender;
	
	private Department department;

	/**
	 * DateTimeFormat:规定前端传递时间类型参数的格式
	 * 必须配置<mvc:annotation-driven />
	 */
	@Past(message = "日期必须是今天之前的一个日期")
	@DateTimeFormat(pattern="yyyy-MM-i18n.properties")
	private Date birth;

	/**
	 * NumberFormat:校验数据格式
	 * <mvc:annotation-driven />
	 */
	@NumberFormat(pattern = "###,###.#")
	private Float salary;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", gender=" + gender + ", department=" + department
				+ ", birth=" + birth + ", salary=" + salary + "]";
	}

	public Employee(Integer id, String lastName, String email, Integer gender,
			Department department) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.department = department;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}
}
