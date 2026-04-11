package org.bluebridge.annotation.autowired.byname.controller;

import org.bluebridge.annotation.autowired.byname.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    @Autowired
    private IOrderService orderService;

    public void deleteById(String id) {
        orderService.deleteById(id);
    }
}
