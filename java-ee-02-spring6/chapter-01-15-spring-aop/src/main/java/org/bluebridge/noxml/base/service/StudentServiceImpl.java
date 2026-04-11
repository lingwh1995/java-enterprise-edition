package org.bluebridge.noxml.base.service;

import org.bluebridge.noxml.base.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements IStudentService{

    @Autowired
    private StudentDao studentDao;

    @Override
    public void deleteStudentById(String id) {
        studentDao.deleteStudentById(id);
    }
}
