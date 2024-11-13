package org.bluebridge.dao;

import org.bluebridge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 符合jpa规范的的接口:
 *      JpaRepository<操作的实体类型,实体中主键的类型>
 *          封装了基本的CRUD操作
 *      JpaSpecificationExecutor<操作的实体类型>
 *          封装了复杂查询操作，如分页
 */
public interface IUserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

}
