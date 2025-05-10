package org.bluebridge.example.controller;

import org.bluebridge.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SpringMVC接受POJO类型的参数
 *      支持级联操作,注意事项:
 *          1.User中有一个属性是Address类型的，如果这个Address没有无参构造方法，则在级联操作时会发生异常
 *              异常名称:org.springframework.beans.NullValueInNestedPathException
 *          2.涉及到中文数据传输时，可能会乱码，配置Sping提供的过滤器即可，但是这个过滤器要配置在web.xml的最上面的位置
 *          3.如果表单中的属性在实体中不存在，不会报错，但是需要在后台单独接收该参数
 */
@Controller
public class PojoController {

    private static final String SUCCESS = "success";

    /**
     * RequestParam注解
     * @return
     */
    @RequestMapping(value="testPojo",method = RequestMethod.POST)
    public String testPojo(User user, @RequestParam("other") String other){
        System.out.println("user"+user);
        System.out.println("other"+other);
        return SUCCESS;
    }
}
