package com.dragonsoft.controller;

import com.dragonsoft.domian.Student;
import org.ho.yaml.Yaml;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 方式五获取YML中的值:
 *      使用Jyaml类库解析YML
 * 注意:
 *      1.实体要有无参构造
 *      2.要额外引入相关类库
 */
@RestController
public class YmlControllerJyamlLib {

    /**
     * 通过Spring容器来管理Yyaml实例对象
     *      特别注意:使用@Resource注解来获取注入的对象
     * @return
     */
    @Bean(name="jyaml")
    public Yaml yaml(){
        return new Yaml();
    }

    @Resource(name="jyaml")
    private Yaml jyaml;

    /**
     * 使用Jyaml将yml文件解析成实体
     */
    @GetMapping("/yml/jyaml/student")
    public Student getStudent(){
        Student student = null;
        try {
            student = jyaml.loadType(YmlControllerJyamlLib.class.getResourceAsStream("/student.yml"), Student.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return student;
    }

    /**
     * 使用Jyaml将yml文件解析成Map
     *  注意:如果想要使用Map接收解析结果,则Yaml.loadType()的第二个参数一定要为Map的实现类
     */
    @GetMapping("/yml/jyaml/studentmap")
    public Map getStudentMap(){
        Map studentMap = null;
        try {
            studentMap = jyaml.loadType(YmlControllerJyamlLib.class.getResourceAsStream("/student.yml"), HashMap.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return studentMap;
    }
}

