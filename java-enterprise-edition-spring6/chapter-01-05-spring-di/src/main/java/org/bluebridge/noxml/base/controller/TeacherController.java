package org.bluebridge.noxml.base.controller;

import org.bluebridge.noxml.base.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    public void deleteById(String id) {
        teacherService.deleteById(id);
    }

}
