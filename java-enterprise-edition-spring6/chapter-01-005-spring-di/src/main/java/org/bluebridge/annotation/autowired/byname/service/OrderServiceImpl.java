package org.bluebridge.annotation.autowired.byname.service;

import org.bluebridge.annotation.autowired.byname.dao.IOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired
    @Qualifier("orderDaoForMysql")
    private IOrderDao orderDao;

    @Override
    public void deleteById(String id) {
        orderDao.deleteById(id);
    }

}
