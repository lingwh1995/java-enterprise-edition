package org.bluebridge.domain;

/**
 * @author lingwh
 * @desc   Account实体类
 * @date   2019/3/25 11:18
 */
public class Account {
	
	private String id;
	private String name;
	private Double money;

	public Account() {
	}

	public Account(String id, String name, Double money) {
		this.id = id;
		this.name = name;
		this.money = money;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", money=" + money +
				'}';
	}

}
