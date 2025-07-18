package org.bluebridge.domain;

/**
 * @author lingwh
 * @desc   Address实体类
 * @date   2019/6/18 14:09
 */
public class Address {

    private String province;
    private String city;

    public Address(){

    }
    public Address(String province, String city) {
        this.province = province;
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

}

