package org.bluebridge.domain;


/**
 * @author ronin
 * @version V1.0
 * @since 2019/12/9 11:37
 */
public class User {

    private Integer id;
    private String username;
    private String address;

    public User() {
    }

    public User(Integer id, String username, String address) {
        this.id = id;
        this.username = username;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
