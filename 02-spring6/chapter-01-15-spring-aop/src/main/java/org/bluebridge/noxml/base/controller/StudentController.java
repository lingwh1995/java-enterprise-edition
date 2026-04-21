package org.bluebridge.noxml.base.controller;

import org.bluebridge.noxml.base.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StudentController {

    @Autowired
    private IStudentService studentService;

    public void deleteStudentById(String id) {
        studentService.deleteStudentById(id);
    }
}
