package com.dragonsoft.condition_family.domain;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/14 10:53
 */
public class Dog {
    private String dogName;
    private String dogCode;

    public Dog(String dogName, String dogCode) {
        this.dogName = dogName;
        this.dogCode = dogCode;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogCode() {
        return dogCode;
    }

    public void setDogCode(String dogCode) {
        this.dogCode = dogCode;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "dogName='" + dogName + '\'' +
                ", dogCode='" + dogCode + '\'' +
                '}';
    }
}
