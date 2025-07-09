package org.bluebridge.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author lingwh
 * @desc   SpringMVC使用ModelAndView作为模型:addObject(String attributeName,Object object):默认把值放在request域对象中
 * @date   2019/6/20 9:15
 */
@Controller
@SessionAttributes(value="time1")
public class ModelAndViewController {
    private static final String VIEWNAME = "modelView";

    /**
     * ModelAndView使用案例1:
     * @return
     */
    @RequestMapping(value="testModelAndView1")
    public ModelAndView testModelAndView1(){
        ModelAndView modelAndView = new ModelAndView(VIEWNAME);
        modelAndView.addObject("time",new Date()+"testModelAndView1...(把视图名称通过构造方法传入)");
        return modelAndView;
    }

    /**
     * ModelAndView使用案例2:
     * @return
     */
    @RequestMapping(value="testModelAndView2")
    public ModelAndView testModelAndView2(ModelAndView modelAndView){
        modelAndView.setViewName(VIEWNAME);
        modelAndView.addObject("time",new Date()+"testModelAndView2...(把视图名称通过构造方法传入，把key/value通过addObject()方法放入到模型中)");
        View view = modelAndView.getView();
        return modelAndView;
    }

    /**
     * ModelAndView使用案例3:
     * @return
     */
    @RequestMapping(value="testModelAndView3")
    public ModelAndView testModelAndView3(){
        ModelAndView modelAndView = new ModelAndView(VIEWNAME,"time",new Date()+"testModelAndView2...(把视图名称和key/value通过构造方法放入到模型中)");
        return modelAndView;
    }


    /**
     * 重定向到另一个jsp页面,并传递值过去：方式1:HttpServletRequest+ModelAndView
     * @param request
     * @return
     */
    @RequestMapping(value="testModelAndViewRedirectJsp1")
    public ModelAndView testModelAndViewRedirectJsp1(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("redirect:/views/success.jsp");
        request.getSession().setAttribute("time",new Date()+"重定向到另一个jsp页面,并传递值过去：方式1:HttpServletRequest+ModelAndView");
        return modelAndView;
    }

    /**
     * 重定向到另一个jsp页面,并传递值过去：方式2:@SessiuonAttribuates+直接返回String
     * @param model
     * @return
     */
    @RequestMapping(value="testModelAndViewRedirectJsp2")
    public String testModelAndViewRedirectJsp2(Model model){
        model.addAttribute("time1",new Date()+"重定向到另一个jsp页面,并传递值过去：方式2:@SessiuonAttribuates+直接返回String...");
        return "redirect:/views/success.jsp";
    }

    /**
     * 重定向到另一个Controller,并传值过去,方式1:@SessiuonAttribuates+ModelAndView
     * @return
     */
    @RequestMapping(value="testModelAndViewRedirectAnotherController1")
    public ModelAndView testModelAndViewRedirectAnotherController1(){
        ModelAndView modelAndView = new ModelAndView("redirect:/forwardOrRedirectTargetController");
        modelAndView.addObject("time1",new Date()+"重定向到另一个Controller,并传值过去,方式1:@SessiuonAttribuates+ModelAndView....");
        return modelAndView;
    }

    /**
     * 重定向到另一个Controller,并传值过去,方式2:直接返回String
     * @return
     */
    @RequestMapping(value="testModelAndViewRedirectAnotherController2")
    public String testModelAndViewRedirectAnotherController2(Model model){
        model.addAttribute("time1",new Date()+"重定向到另一个Controller,并传值过去,方式2:直接返回String....");
        return "redirect:/forwardOrRedirectTargetController";
    }

    /**
     * 转发向到另一个Controller,并传值过去,方式1:ModelAndView
     * @return
     */
    @RequestMapping(value="testModelAndViewForwardAnotherController1")
    public ModelAndView testModelAndViewForwardAnotherController1(){
        ModelAndView modelAndView = new ModelAndView("forward:/forwardOrRedirectTargetController");
        modelAndView.addObject("time1",new Date()+"转发向到另一个Controller,并传值过去,方式1:ModelAndView....");
        return modelAndView;
    }

    /**
     * 转发向到另一个Controller,并传值过去，方式2:直接返回String
     * @param model
     * @return
     */
    @RequestMapping(value="testModelAndViewForwardAnotherController2")
    public String testModelAndViewForwardAnotherController2(Model model){
        model.addAttribute("time1",new Date()+"转发向到另一个Controller,并传值过去，方式2:直接返回String...");
        return "forward:/forwardOrRedirectTargetController";
    }


    /**
     * 转发/重定向目标Controlelr
     * @return
     */
    @RequestMapping(value="forwardOrRedirectTargetController")
    public String redirectTargetController(HttpServletRequest request){
        System.out.println("session中的time1:"+request.getSession().getAttribute("time1"));
        System.out.println("目标Controlelr......");
        return null;
    }

}
