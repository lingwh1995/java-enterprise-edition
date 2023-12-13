package org.bluebridge.profile.dibysetsenior;

import org.bluebridge.profile.dibysetsenior.domain.Friend;

import java.util.List;

/**
 * set方式注入专题之注入   List集合
 */
public class UserInjectListBySetter {

    //注入List，且数组元素数据类型为非引用类型数组
    private List<String> hobbies;

    //注入List，且数组元素数据类型为引用类型数组
    private List<Friend> friends;

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "UserInjectListBySetter{" +
                "hobbies=" + hobbies +
                ", friends=" + friends +
                '}';
    }
}
