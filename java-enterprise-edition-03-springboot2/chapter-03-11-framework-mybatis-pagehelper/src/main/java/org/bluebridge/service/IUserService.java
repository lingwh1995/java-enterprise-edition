package org.bluebridge.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.bluebridge.domain.PageEntity;
import org.bluebridge.domain.User;

import java.util.List;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/11/18 14:56
 */
public interface IUserService {

    /**
     * 查询所有的User对象
     * @return
     */
    List<User> list();

    /**
     * 根据id查询User
     * @param id
     * @return
     */
    User getById(int id);

    /**
     * 分页查询所有的User对象
     * @param pageEntity
     * @return
     */
    PageInfo<User> listPage(PageEntity<User> pageEntity);

}
