package com.dragonsoft.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * 测试异步调度失败
 * @author ronin
 */
@RequestMapping("/async")
@Controller
public class AsyncTaskFailController {

    /**
     * 测试:
     *      http://localhost:8080/async/test-async-fail
     * 此种情况下异步调度失败
     *      异步的代码要放在Service层中(将异步部分代码放入另一个类中，如果放在同一个类中，异步调度会失败)
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping("/test-async-fail")
    public Map<String,String> doTaskFail() throws InterruptedException{
        System.out.println("方法开始执行了......");
        long currentTimeMillis = System.currentTimeMillis();
        this.task1();
        this.task2();
        this.task3();
        long currentTimeMillis1 = System.currentTimeMillis();
        HashMap<String, String> map = new HashMap<>();
        map.put("message","task任务总耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
        System.out.println("task任务总耗时:" + (currentTimeMillis1 - currentTimeMillis) + "ms");
        System.out.println("方法执行结束了......");
        return map;
    }


    @Async
    public void task1() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(1000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task1任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async
    public void task2() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(2000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task2任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }

    @Async
    public void task3() throws InterruptedException{
        long currentTimeMillis = System.currentTimeMillis();
        Thread.sleep(3000);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("task3任务耗时:"+(currentTimeMillis1-currentTimeMillis)+"ms");
    }
}
