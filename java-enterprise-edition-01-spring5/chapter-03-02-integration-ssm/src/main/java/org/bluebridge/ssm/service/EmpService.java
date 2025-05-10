package org.bluebridge.ssm.service;

import org.bluebridge.ssm.dao.IEmpDao;
import org.bluebridge.ssm.domain.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpService implements IEmpServices {
    @Autowired
    private IEmpDao iEmpDao;

    @Override
    public Emp getEmployeeById(String id) {
        return iEmpDao.getEmployeeById(id);
    }
}
