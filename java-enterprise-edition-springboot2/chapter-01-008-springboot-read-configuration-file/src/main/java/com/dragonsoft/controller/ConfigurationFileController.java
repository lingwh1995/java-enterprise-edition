package com.dragonsoft.controller;

import com.dragonsoft.configurationProperties_bind_with_bean.LibraryBindWithBean;
import com.dragonsoft.configurationProperties_bind_with_out_bean.LibraryBindWithoutBean;
import com.dragonsoft.propertySource.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 通过@value读取比较简单的配置信息
 *      使用@Value("${property}")读取比较简单的配置信息：Spring并不推荐@value这种方式
 */
@Controller
public class ConfigurationFileController {

    //-----------使用@Value读取配置文件开始-----------
    @Value("${version}")
    private String version;

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;

    @Value("${library.location}")
    private String location;

    @Value("${library.books[0].name}")
    private String book0name;

    @Value("${library.books[0].description}")
    private String book0description;

    @Value("${library.books[1].name}")
    private String book1name;

    @Value("${library.books[1].description}")
    private String book1description;

    @Value("${library.books[2].name}")
    private String book2name;

    @Value("${library.books[2].description}")
    private String book2description;
    //-----------使用@Value读取配置文件结束-----------

    //-----------使用@ConfigurationProperties读取配置文件(和bean绑定)开始-----------
    @Resource
    private LibraryBindWithBean libraryBindWithBean;
    //-----------使用@ConfigurationProperties读取配置文件(和bean绑定)结束-----------

    //-----------使用@ConfigurationProperties读取配置文件(不和bean绑定)开始-----------
    @Resource
    private LibraryBindWithoutBean libraryBindWithoutBean;
    //-----------使用@ConfigurationProperties读取配置文件(不和bean绑定)结束-----------

    //-----------使用@PropertySource读取properties文件内容开始-----------
    @Resource
    private User user;
    //-----------使用@PropertySource读取properties文件内容结束-----------


    /**
     * 访问   http://localhost:8080/read-configuration     查看效果
     * @return
     */
    @ResponseBody
    @RequestMapping("/read-configuration")
    public String getConfiguration() {
        System.out.println("-----------使用@Value读取配置文件开始-----------");
        System.out.println("version = " + version);
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("location = " + location);
        System.out.println("book0 = " + book0name + "-" + book0description);
        System.out.println("book1 = " + book1name + "-" + book1description);
        System.out.println("book2 = " + book2name + "-" + book2description);
        System.out.println("-----------使用@Value读取配置文件结束-----------");

        System.out.println("-----------使用@ConfigurationProperties读取配置文件(和bean绑定)开始-----------");
        System.out.println(libraryBindWithBean);
        System.out.println("-----------使用@ConfigurationProperties读取配置文件(和bean绑定)结束-----------");

        System.out.println("-----------使用@ConfigurationProperties读取配置文件(不和bean绑定)开始-----------");
        System.out.println(libraryBindWithoutBean);
        System.out.println("-----------使用@ConfigurationProperties读取配置文件(不和bean绑定)结束-----------");

        //-----------使用@PropertySource读取properties文件内容开始-----------
        System.out.println("user = " + user);
        //-----------使用@PropertySource读取properties文件内容结束-----------
        return "请在控制台查看打印的配置文件内容~";
    }
}
