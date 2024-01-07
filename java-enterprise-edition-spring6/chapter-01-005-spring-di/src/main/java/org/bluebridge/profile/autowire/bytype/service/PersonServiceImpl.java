package org.bluebridge.profile.autowire.bytype.service;

import org.bluebridge.profile.autowire.bytype.dao.PersonDao;

/**
 * 根据属性类型autowire   需要为属性创建setter方法
 */
public class PersonServiceImpl implements IPersonService {

    private PersonDao orderDao;

    public void setOrderDao(PersonDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void deleteById(String id) {
        orderDao.deleteById(id);
    }
}
