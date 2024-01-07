package com.dragonsoft.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * SpringMVC中的模型:
 *      1.Model
 *      2.Map
 *      3.ModelMap
 *      4.ModelAndView
 */
@Controller
public class MutiplyMapContoller {

    private static final String VIEWNAME = "modelView";

    /**
     * 使用Map<String,Object>作为模型:
     *      每个放入Map中的值会被放入到Request域中
     */
    @RequestMapping(value="testMap")
    public String testMap(Map<String,Object> map){
        map.put("time",new Date()+",testMap...");
        System.out.println(map);
        return VIEWNAME;
    }


    /**
     * 使用ModelMap作为模型
     */
    @RequestMapping(value="testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.put("time",new Date()+",testModelMap...");
        System.out.println(modelMap);
        return VIEWNAME;
    }


    /**
     * 使用Model作为模型
     */
    @RequestMapping(value="testModel")
    public String testModel(Model model){
        model.addAttribute("time",new Date()+",testModel...");
        return VIEWNAME;
    }


    /**
     * ModelAndView和Model高级特性:
     * ModelAndView.addObject()和Model.addAttribute()放置同名参数问题
     * @param model
     * @return
     */
    @RequestMapping("/mergemodel")
    public ModelAndView mergeModel(Model model) {
        //①添加模型数据
        model.addAttribute("a", "a");
        ModelAndView mv = new ModelAndView("mvcsuperior");
        //②在视图渲染之前更新③处同名模型数据
        mv.addObject("a", "update");
        //③修改①处同名模型数据
        model.addAttribute("a", "new");
        //视图页面的a将显示为"update" 而不是"new"
        return mv;
    }
}
