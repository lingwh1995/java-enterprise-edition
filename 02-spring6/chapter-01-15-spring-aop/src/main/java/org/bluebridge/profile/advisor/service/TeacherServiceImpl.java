package org.bluebridge.profile.advisor.service;

import org.bluebridge.profile.advisor.dao.TeacherDao;

public class TeacherServiceImpl implements ITeacherService{

    private TeacherDao teacherDao;

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    @Override
    public void deleteById(String id) {
        //放开后模拟发生了异常，可以触发异常通知
        //int i = 1 / 0;
        teacherDao.deleteById(id);
    }
}
