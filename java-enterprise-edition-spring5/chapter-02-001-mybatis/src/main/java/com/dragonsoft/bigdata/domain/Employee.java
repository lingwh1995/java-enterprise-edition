package com.dragonsoft.bigdata.domain;

import java.sql.Date;

public class Employee {
	private int empno;
	private String ename;
	private String job;
	private Date hireDate;
	private double sal;
	private double comm;
	private int deptNo;

	public Employee(String ename, String job, Date hireDate,double sal, double comm, int deptNo) {
		this.ename = ename;
		this.job = job;
		this.hireDate = hireDate;
		this.sal = sal;
		this.comm = comm;
		this.deptNo = deptNo;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public double getComm() {
		return comm;
	}

	public void setComm(double comm) {
		this.comm = comm;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"empno=" + empno +
				", ename='" + ename + '\'' +
				", job='" + job + '\'' +
				", hireDate=" + hireDate +
				", sal=" + sal +
				", comm=" + comm +
				", deptNo=" + deptNo +
				'}';
	}
}
