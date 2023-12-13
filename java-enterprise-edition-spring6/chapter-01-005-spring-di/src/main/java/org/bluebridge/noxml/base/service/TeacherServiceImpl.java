package org.bluebridge.noxml.base.service;

import org.bluebridge.noxml.base.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements ITeacherService{

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public void deleteById(String id) {
        teacherDao.deleteById(id);
    }
}
