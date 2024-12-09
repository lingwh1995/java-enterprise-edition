package org.bluebridge.annotation.resource.controller;

import jakarta.annotation.Resource;
import org.bluebridge.annotation.resource.service.IStudentService;
import org.springframework.stereotype.Controller;

@Controller
public class StudentController {

    @Resource
    private IStudentService studentService;

    public void deleteById(String id) {
        studentService.deleteById(id);
    }
}
