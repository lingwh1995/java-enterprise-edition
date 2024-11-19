package com.dragonsoft.ssm.domain;

public class Emp {
	
	private String id;
	private String lastName;
	private String email;
	private String gender;
	private String databaseId;
	public Emp() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	@Override
	public String toString() {
		return "Emp{" +
				"id='" + id + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", gender='" + gender + '\'' +
				", databaseId='" + databaseId + '\'' +
				'}';
	}
}
