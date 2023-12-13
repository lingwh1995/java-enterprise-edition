package com.dragonsoft.example.controller;

import com.dragonsoft.domain.Person;
import com.dragonsoft.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ModelAttribuate注解:
 *      1.何时运行:被@ModelAttribuate标注的方法，SpringMVC执行每一个方法的时候都会被调用一次
 *      2.复杂情况如何处理:从数据库中查询出来的同一个数据被放入到到多个不同域中，Map<String,Object>/Model/ModelMap,以哪个模型中
 *        存放的数据为准，是不确定的、随机的。
 *      3.执行流程:
 *              1.>执行被@ModelAttribuate注解修饰的方法,从数据库中查询出Person对象，放在模型中
 *              2.>取出模型中存放的Person对象，把表单中的数据封装到Person对象中(只封装发生了变化的属性)
 *              3.>把封装好的数据作为参数传入到目标方法中
 *  @ModelAttribute的作用:
 *      1.绑定请求参数到命令对象
 *      2.暴露表单引用对象为模型数据,即把一个数据放入到模型中，模型指的是Model、Map、ModelMap
 *          注意:
 *              1>.此操作相当于在控制路由跳转的方法中，先查库，获取数据后，再把数据放入到模型中
 *              2>.此操作已经将数据放入域中了，可以在页面直接使用了
 *              3>.模型(Model/Map/ModelMap)默认都是Request域范围
 *      3.通过@ModelAttribute自动将数据暴露在模型中，不需要再方法入参中写Model/Model/ModelMap(注意:不包含ModelAndView)
 *      4.数据封装
 *          编辑页面只能部分修改部分字段的的时候，如密码字段不能被修改
 *              常规做法:把密码和其他字段都放入到域中，密码的值存放在隐藏域中，设置一个name，传递到后台，不改变这个字段的值就可以了
 *              使用@ModelAttribute：不把密码往前台传递，再执行更新操作的时候使用该注解封将表单数据和数据库中的数据合并后封装到实体中
 *      @ModelAttritube使用总结:
 *      1.被@ModelAttribute修饰对象会直接放入到request域中
 *      2.@ModelAttribute默认使用的key是对象名首字母小写，要想改这个值，在方法入参中传入Model/ModelMap/Map/ModelAndView,把值添加到这几个模型中即可
 *      3.运用在参数上，会将客户端传递过来的参数按名称注入到指定对象中，并且会将这个对象自动加入模型中，便于View层使用
 *        在被@RequestMapping标注的方法中，如果方法的入参中传入的参数被@ModelAttribute修饰了，那个这个参数直接被暴露在模型中
 *      4.运用在方法上，会在每一个@RequestMapping标注的方法前执行，如果有返回值，则自动将该返回值加入到ModelMap中；
 *
 */
@SessionAttributes(types = {Person.class})
@Controller
public class ModelAttribuateController {

    private static final String VIEW = "modeAttribuate";
    private static final String SUCCESS = "success";

//    /**
//     * 模拟更新操作，根据id是否为空判断是否为更新操作
//     *      并把从数据库中查询出来的数据放在map中
//     * @param id
//     * @param map
//     */
//    @ModelAttribute
//    public void getUser_Map(@RequestParam(value="id",required=false) Integer id,Map<String,Object> map){
//        System.out.println("被@ModelAttribute标注的方法getUser()被调用了......");
//        if(id != null) {
//            /**
//             * 模拟从数据库中查询数据
//             */
//            Person person = new
//                    Person("1","zhangsan","dragon123456_map","1458687169@qq.com",1);
//            map.put("person",person);
//            System.out.println("从数据库中获取一个对象:"+person);
//        }
//    }

//    /**
//     * 模拟更新操作，根据id是否为空判断是否为更新操作
//     *      并把从数据库中查询出来的数据放在Model中
//     * @param id
//     * @param model
//     */
//    @ModelAttribute
//    public void getUser_Model(@RequestParam(value="id",required=false) Integer id,Model model){
//        System.out.println("被@ModelAttribute标注的方法getUser()被调用了......");
//        if(id != null) {
//            /**
//             * 模拟从数据库中查询数据
//             */
//            Person person = new
//                    Person("1","zhangsan","dragon123456_model","1458687169@qq.com",1);
//            model.addAttribute("person",person);
//            System.out.println("从数据库中获取一个对象:"+person);
//        }
//    }

    /**
     * 模拟更新操作，根据id是否为空判断是否为更新操作
     *      并把从数据库中查询出来的数据放在ModelMap中
     * 注意:ModelAttribuate注解(不用在方法参数中)
     * @param id
     * @param modelMap
     */
    @ModelAttribute
    public void getUser_ModeMapNoUseInFunctionParam(@RequestParam(value="id",required=false) Integer id,ModelMap modelMap){
        System.out.println("被@ModelAttribute标注的方法getUser()被调用了...+NoUseInFunctionParam...");
        if(id != null) {
            /**
             * 模拟从数据库中查询数据
             */
            Person person = new
                    Person("1","zhangsan","dragon123456_modelMap","1458687169@qq.com",1);
            modelMap.put("person",person);
            System.out.println("从数据库中获取一个对象:"+person);
        }
    }

    /**
     * 模拟更新操作，根据id是否为空判断是否为更新操作
     *      并把从数据库中查询出来的数据放在ModelMap中
     * 注意:ModelAttribuate注解(使用在方法参数中)
     * @param id
     * @param modelMap
     */
    @ModelAttribute
    public void getUser_ModeMapUseInFunctionParam(@RequestParam(value="id",required=false) Integer id,ModelMap modelMap){
        System.out.println("被@ModelAttribute标注的方法getUser()被调用了......+UseInFunctionParam...");
        if(id != null) {
            /**
             * 模拟从数据库中查询数据
             */
            Person person = new
                    Person("1","zhangsan","dragon123456_modelMap","1458687169@qq.com",1);
            modelMap.put("abc",person);
            System.out.println("从数据库中获取一个对象:"+person);
        }
    }

    /**
     * 逻辑视图跳转页面
     * @param map
     * @return
     */
    @RequestMapping(value="toTestModelAttribuate")
    public String page(Map<String,Object> map){
        /**
         * 模拟从数据库中取值，并把值回填到表单中
         */
        Person person = new
                Person("1","zhangsan","dragon123456","1458687169@qq.com",1);
        System.out.println("逻辑视图跳转时查库操作:"+person);
        map.put("person",person);
        return "/modelAttribuate";
    }

    /**
     *测试@ModelAttribuate注解(不用在方法参数中)
     * @return
     */
    @RequestMapping(value="testModelAttribuateNoUserInFunctionParam")
    public String testModelAttribuateNoUserInFunctionParam(Person person){
        System.out.println("person:"+person);
        return SUCCESS;
    }

    /**
     *测试@ModelAttribuate注解(用在方法参数中)
     * @return
     */
    @RequestMapping(value="testModelAttribuateUserInFunctionParam")
    public String testModelAttribuateUserInFunctionParam(@ModelAttribute("abc") Person person){
        System.out.println("person:"+person);
        return SUCCESS;
    }

    /**
     * @ModelAttritube
     * 绑定请求参数到命令对象高级用法:
     *      将URI模板变量的值自动自动绑定到命令对象中，当你请求的URL中包含“&username=zhang”会自动绑定到命令对象User上,
     *      因为User对象中有一个属性是username
     * 注意事项:
     *      当URI模板变量和请求参数同名时， 请求参数 具有高优先权。
     * @param user
     * @return
     */
    @RequestMapping(value="/testmodelattributesuperior/getparamvaluefromurl/{username}")
    public String getparamvaluefromurl(@ModelAttribute("model") User user){
        System.out.println(user);
        return SUCCESS;
    }

    /**
     * 使用ModelAttribute把数据放入到模型中，模型指的是Model、Map、ModelMap
     * @return
     */
    @ModelAttribute("cityList")
    public List cityList() {
        return Arrays. asList("北京", "山东");
    }

    /**
     * 暴露表单引用对象为模型数据,即把一个数据放入到模型中,并从模型中获取该数据
     *      注意:SpringMVC提供三种模型，指的是Model、Map、ModelMap
     * @param model
     * @return
     */
    @RequestMapping(value="/testmodelattributesuperior/exposedatatomodel")
    public String exposedatatomodel(Model model,
                                    ModelMap modelMap,
                                    Map map,
                                    ModelAndView mv){
        model.addAttribute("modelattributeexposedatatomodel",true);
        /**
         * 从Model模型中获取使用@ModelAttribute放入到模型中的数据
         */
        List<String> listFromModel = (List)model.asMap().get("cityList");
        for(String s : listFromModel){
            System.out.println("s==="+s);
        }
        /**
         * 从ModelMap模型中获取使用@ModelAttribute放入到模型中的数据
         */
        List<String> listFromModelMap = (List)modelMap.get("cityList");
        for(String s : listFromModelMap){
            System.out.println("s==="+s);
        }
        /**
         * 从Map模型中获取使用@ModelAttribute放入到模型中的数据
         */
        List<String> listFromMap = (List)map.get("cityList");
        for(String s : listFromMap){
            System.out.println("s==="+s);
        }
        /**
         * 从ModelMap的Model模型中获取使用@ModelAttribute放入到模型中的数据
         *      注意:下面的代码放开注释会报错
         */
//        Map<String, Object> modelFromModelAndView = mv.getModel();
//        List<String> listFromModelFromModelAndView = (List<String>)modelFromModelAndView.get("cityList");
//        for(String s : listFromModelFromModelAndView){
//            System.out.println("s==="+s);
//        }
        /**
         * 从ModelMap的ModelAndView模型中获取使用@ModelAttribute放入到模型中的数据
         *      注意:下面的代码放开注释会报错
         */
//        ModelMap modelMapFromModelAndView = mv.getModelMap();
//        List<String> modeAndViewlMapFromModelAndView = (List<String>)modelMapFromModelAndView.get("cityList");
//        for(String s : modeAndViewlMapFromModelAndView){
//            System.out.println("s==="+s);
//        }
        return SUCCESS;
    }

    /**
     * 常规格式:
     *      @ModelAttribute()
     *      public User getUser(Model model){
     *          User user = new User();
     *          user.setUsername("王麻子");
     *          user.setAge(18);
     *          user.setEmail("1458689999@qq.com");
     *          model.addAttribute("wangmazi",user);
     *      return user;
    }
     * @return
     */
    @ModelAttribute("wangmz")
    public User getUser(){
        User user = new User();
        user.setUsername("王麻子");
        user.setAge(18);
        user.setEmail("1458689999@qq.com");
        return user;
    }
    /**
     * 使用@ModelAttribute自动将数据暴露到模型中(注意:不包含ModelAndView)
     * @param user @ModelAttribute("user")
     */
    @RequestMapping(value="/testmodelattributesuperior/expose_data_tomodel_automic")
    public String exposereRuestMappingResultToModel(@ModelAttribute("wangmz") User user){
        System.out.println(user);
        return SUCCESS;
    }
}
