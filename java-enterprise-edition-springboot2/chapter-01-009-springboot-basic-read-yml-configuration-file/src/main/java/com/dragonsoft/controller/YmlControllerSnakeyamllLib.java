package com.dragonsoft.controller;

import com.dragonsoft.domian.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 方式六获取YML中的值:
 *      使用Snakeyaml自带类库解析YML
 * 注意:
 *      1.实体要有无参构造
 *      2.要额外引入相关类库
 */
@RestController
public class YmlControllerSnakeyamllLib {


    /**
     * 通过Spring容器来管理Snakeyaml实例对象
     *      特别注意:使用@Resource注解来获取注入的对象
     * @return
     */
    @Bean(name="snakeyaml")
    public Yaml yaml(){
        return new Yaml();
    }

    @Resource(name = "snakeyaml")
    private Yaml snakeyaml;

    /**
     * 使用snakeyaml解析yml文件,并将解析结果封装到实体中
     */
    @GetMapping("/yml/snake/student")
    public Student getStudent() {
        Student student = snakeyaml.loadAs(YmlControllerSnakeyamllLib.class.getResourceAsStream("/student.yml"), Student.class);
        return student;
    }

    /**
     * 使用snakeyaml解析yml文件,并将解析结果封装到Map中
     */
    @GetMapping("/yml/snake/studentmap")
    public Map getStudentMap() {
        Map studentMap = snakeyaml.loadAs(YmlControllerSnakeyamllLib.class.getResourceAsStream("/student.yml"), Map.class);
        return studentMap;
    }
}

