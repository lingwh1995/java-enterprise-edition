package org.bluebridge.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bluebridge.domain.PageEntity;
import org.bluebridge.mapper.IUserDao;
import org.bluebridge.domain.User;
import org.bluebridge.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public PageInfo<User> listPage(PageEntity<User> pageEntity) {
        int currentPage = pageEntity.getPageNum();
        int pageSize = pageEntity.getPageSize();
        PageHelper.startPage(currentPage, pageSize);
        List<User> userList = userDao.list();
        return PageInfo.of(userList);
    }

}
