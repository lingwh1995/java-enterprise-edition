package org.bluebridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ronin
 */
@EnableScheduling
@SpringBootApplication
public class Application {
    //https://blog.csdn.net/qq_45087487/article/details/132063403
    //https://www.w3cschool.cn/quartz_doc/quartz_doc-h4ux2cq6.html
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
