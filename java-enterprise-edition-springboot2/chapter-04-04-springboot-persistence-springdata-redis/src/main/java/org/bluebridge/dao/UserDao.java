package org.bluebridge.dao;


import org.apache.ibatis.annotations.Select;
import org.bluebridge.domain.User;

/**
 * @author ronin
 */
public interface UserDao {

    @Select("select id,username,password from t_user where id =#{id}")
    User getUserById(String id);
}
