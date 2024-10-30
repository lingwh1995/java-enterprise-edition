package com.dragonsoft.domian;

import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private Integer age;
    private Map<String,Object> hobbies;
    private List<Object> favoriteBooks;

    public Student(){

    }

    public Student(String name, Integer age, Map<String, Object> hobbies, List<Object> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
        this.favoriteBooks = favoriteBooks;
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

    public Map<String, Object> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Map<String, Object> hobbies) {
        this.hobbies = hobbies;
    }

    public List<Object> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<Object> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", hobbies=" + hobbies +
                ", favoriteBooks=" + favoriteBooks +
                '}';
    }
}
