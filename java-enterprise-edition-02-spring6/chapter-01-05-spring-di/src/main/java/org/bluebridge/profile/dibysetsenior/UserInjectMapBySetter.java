package org.bluebridge.profile.dibysetsenior;

import org.bluebridge.profile.dibysetsenior.domain.Friend;

import java.util.Map;

/**
 * set方式注入专题之注入   Map集合
 */
public class UserInjectMapBySetter {

    //注入Map，且数组元素数据类型为非引用类型数组
    private Map<String,String> phones;

    //注入Map，且数组元素数据类型为引用类型数组
    private Map<String, Friend> friends;

    public void setPhones(Map<String, String> phones) {
        this.phones = phones;
    }

    public void setFriends(Map<String, Friend> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "UserInjectMapBySetter{" +
                "phones=" + phones +
                ", friends=" + friends +
                '}';
    }
}
