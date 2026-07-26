package org.bluebridge.dao;

import org.bluebridge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 符合 jpa 规范的的接口
 *
 * 1. JpaRepository<操作的实体类型,实体中主键的类型>
 *    封装了基本的 CRUD 操作
 * 2. JpaSpecificationExecutor<操作的实体类型>
 *    封装了复杂查询操作，如分页
 *
 * @author lingwh
 * @date 2024/9/2 11:25
 */
public interface IUserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

}
