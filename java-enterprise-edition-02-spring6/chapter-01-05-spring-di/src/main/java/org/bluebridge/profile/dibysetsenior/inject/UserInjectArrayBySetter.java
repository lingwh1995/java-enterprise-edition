package org.bluebridge.profile.dibysetsenior.inject;

import org.bluebridge.profile.dibysetsenior.domain.Friend;

import java.util.Arrays;

/**
 * set方式注入专题之注入   数组
 */
public class UserInjectArrayBySetter {

    //注入数组，且数组元素数据类型为非引用类型数组
    private String[] hobbies;

    //注入数组，且数组元素数据类型为引用类型数组
    private Friend[] friends;

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public void setFriends(Friend[] friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "UserInjectArrayBySetter{" +
                "hobbies=" + Arrays.toString(hobbies) +
                ", friends=" + Arrays.toString(friends) +
                '}';
    }
}
