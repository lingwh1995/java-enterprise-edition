package org.bluebridge.builtinparam.domain;

public class Emp {
	
	private String id;
	private String lastName;
	private String email;
	private String gender;

	public Emp(){

	}

	public Emp(String id, String lastName, String email, String gender) {
		this.id = id;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}

	public String getId(int i) {
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email="
				+ email + ", gender=" + gender + "]";
	}
	
	

}
