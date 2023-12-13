package org.bluebridge.controller.requestscope;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequestMapping(value = {"/domainObject"})
@Controller
public class SpringMVCRequestScopeController {

    /**
     * ModelAndView同时具有Model和View的功能
     *  Model   主要用于向请求域共享数据
     *  View    主要用于设置视图，实现页面跳转
     */
    @RequestMapping(value = "/springmvc/requestScope/modelAndView")
    public ModelAndView requestScopeBySpringMVCByModelAndView(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //向请求域共享数据
        modelAndView.addObject("requestScope","存放在request域对象中的值[基于SpringMVC提供的ModelAndView]");
        //设置视图，实现页面跳转
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /**
     *  Model   主要用于向请求域共享数据
     */
    @RequestMapping(value = "/springmvc/requestScope/model")
    public String requestScopeBySpringMVCByModel(Model model) {
        model.addAttribute("requestScope","存放在request域对象中的值[基于SpringMVC提供的Model]");
        return "success";
    }

    /**
     *  Map   主要用于向请求域共享数据
     */
    @RequestMapping(value = "/springmvc/requestScope/map")
    public String requestScopeBySpringMVCByMap(Map<String,Object> map) {
        map.put("requestScope","存放在request域对象中的值[基于SpringMVC提供的Map]");
        return "success";
    }

    /**
     *  ModelMap   主要用于向请求域共享数据
     */
    @RequestMapping(value = "/springmvc/requestScope/modelMap")
    public String requestScopeBySpringMVCByModelMap(ModelMap modelMap) {
        modelMap.addAttribute("requestScope","存放在request域对象中的值[基于SpringMVC提供的ModelMap]");
        return "success";
    }

}
