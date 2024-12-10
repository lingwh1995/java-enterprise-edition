package com.dragonsoft.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SpringMVC重定向传
 *  1.把参数拼接到URL中
 *  2.把参数放在Session中,重定向到一个页面或者Controlelr
 *  RedirectAttributes的使用:
 *      1.使用RedirectAttributes需要在配置文件中配置<mvc:annotation-driven />
 *      2.常用方法
 *          RedirectAttributes.addFlashAttribute()：把参数拼接存放到Seesion中
 *              -->如果跳转到页面，则Session中存放的数据瞬间一处
 *              -->如果跳转到Controller,使用@ModelAttribute注解可获取该数据，因为该数据是
 *                  存储在Session中的，可以直接使用@ModelAttribute获取
 *          RedirectAttributes.addAttribute():把参数拼接到URL中
 *              -->可以直接跳转到页面
 *              -->如果跳转到Controller，使用@RequestParam获取传递过去的参数值
 *              如:http://localhost:8080/test/views/redirectAttribuate.jsp?name=zhangsan&age=18&school=ufe
 */
@Controller
public class RedirectAttributesController {

    /**
     * 重定向到一个目标页面
     * 把参数拼接到URL中,重定向到页面,请注意查看浏览器地址栏中地址已经拼接了参数进去
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="testRedirectAttributesToPageParamInURL")
    public String testRedirectAttributesToPageParamInURL(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("name","zhangsan");
        redirectAttributes.addAttribute("age",18);
        redirectAttributes.addAttribute("school","ufe");
        return "redirect:/views/redirectAttribuate.jsp";
    }
    
    /**
     * 重定向到一个目标Controller
     * 把参数拼接到URL中,重定向到Controller中,请注意查看浏览器地址栏中地址已经拼接了参数进去
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="testRedirectAttributesToControllerParamInURL")
    public String testRedirectAttributesToControllerParamInURL(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("name","zhangsan");
        redirectAttributes.addAttribute("age",18);
        redirectAttributes.addAttribute("school","ufe");
        return "redirect:/targetControllerParamInURL";
    }

    /**
     * 目标Controller
     *      通过@RequestParam注解获取重定向后传递过来的值
     * @param name
     * @return
     */
    @RequestMapping(value="targetControllerParamInURL")
    public String targetControllerForRequestParam(@RequestParam("name") String name,
            @RequestParam("age") String age,@RequestParam("school")  String school){
        System.out.println("name:"+name);
        System.out.println("age:"+age);
        System.out.println("school:"+school);
        return "redirectAttribuate";
    }
    
    /**
     * 重定向到一个目标页面
     * 把参数放在Session中,重定向到页面，并在页面获取该值
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="testRedirectAttributesToPageParamInSession")
    public String testRedirectAttributesToPageParamInSession(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("name","zhangsan");
        return "redirect:/views/redirectAttribuate.jsp";
    }

    /**
     * 重定向到一个目标Controller
     * 把参数放在Session中,重定向到另一个方法，并在该方法中接收(存放在session中的)参数
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="testRedirectAttributesToControllerParamInSession")
    public String testRedirectAttributesToControllerParamInSession(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("name","zhangsan");
        return "redirect:/targetControllerParamInSession";
    }

    /**
     * 目标Controller通过@ModelAttribute注解获取重定向后传递过来的值
     * @param name
     * @return
     */
    @RequestMapping(value="targetControllerParamInSession")
    public String targetController(@ModelAttribute("name") String name){
        System.out.println(name);
         return "redirectAttribuate";
    }
}
