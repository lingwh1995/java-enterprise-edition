package org.bluebridge.mapper;

import com.github.pagehelper.PageInfo;
import org.bluebridge.domain.PageEntity;
import org.bluebridge.domain.User;

import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 13:08
 */
public interface IUserDao {

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
