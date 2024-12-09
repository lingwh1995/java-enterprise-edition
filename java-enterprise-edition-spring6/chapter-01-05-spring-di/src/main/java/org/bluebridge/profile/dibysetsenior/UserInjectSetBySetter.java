package org.bluebridge.profile.dibysetsenior;

import org.bluebridge.profile.dibysetsenior.domain.Friend;

import java.util.Set;

/**
 * set方式注入专题之注入   Set集合
 */
public class UserInjectSetBySetter {

    //注入Set，且数组元素数据类型为非引用类型数组
    private Set<String> hobbies;

    //注入Set，且数组元素数据类型为引用类型数组
    private Set<Friend> friends;

    public void setHobbies(Set<String> hobbies) {
        this.hobbies = hobbies;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "UserInjectSetBySetter{" +
                "hobbies=" + hobbies +
                ", friends=" + friends +
                '}';
    }
}
