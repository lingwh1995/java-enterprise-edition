package com.dragonsoft.cache.domain;

import com.dragonsoft.resultmap.domain.Employee;

import java.util.List;

public class Department {
    private String id;
    private String dname;
    private List<Employee> emps;

    public Department(){

    }

    public Department(String id, String dname) {
        this.id = id;
        this.dname = dname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", dname='" + dname + '\'' +
                ", emps=" + emps +
                '}';
    }
}
