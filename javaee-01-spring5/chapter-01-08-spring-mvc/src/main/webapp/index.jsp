<%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/5/12
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${_contextPath}/resources/js/jquery2.2.js"></script>
    <script>
       $(function () {
           /**
            * data:单层json对象，可以使用$.post();/$.ajax();把该data传递到后台
            */
           $("#dealSignJsonUseJsonObject").click(function () {
               var url = '${_contextPath}'+'/dealSignJsonUseJsonObject.action';
               var data = {name:'zhangsan',age:'28',win:'[2006,2012,2013]',lose:[2003,2009,2019],person:{name:'ufe',addr:'xian'}};
               //注意:发送ajax时向后台传递的是一个Json对象，不是一个Json格式的字符串
               $.post(url,data,function (result) {
                    console.log(result);
                    //{name: "zhangsan", age: "28", win: "[2006,2012,2013]", lose[]: "2003", person[name]: "ufe", …}
                    //win正常传递,lose传过去变成:lose[]: "2003"
               });
           });

           /**
            * data:单层json格式字符串,只能用$.ajax();把该data传递到后台
            */
           $("#dealSignJsonUseJsonString").click(function () {
               var url = '${_contextPath}'+'/dealSignJsonUseJsonString.action';
               var data = {name:'zhangsan',age:'28',win:'[2006,2012,2013]',lose:[2003,2009,2019]};
               var dataStr = JSON.stringify(data);
               //注意:发送ajax时向后台传递的是一个Json对象，不是一个Json格式的字符串
               $.ajax({
                   type:'post',
                   url:url,
                   contentType:"application/json;charset=utf-8",
                   data:dataStr,
                   success:function (result) {
                       console.log(result);
                   }
               });
           });
       });
    </script>
</head>
<body>
    <h1>测试SpringMVC的@RequestMapping注解</h1>
    <a href="${_contextPath}/hello">hello</a>
    <a href="${_contextPath}/testRequestMappping?username=zhangsan&age=9">testRequestMappping</a>

    <hr/>
    <h1>测试SpringRest风格的URL</h1>
    <form action="${_contextPath}/testPost" method="post">
        <input type="hidden" name="_method" value="POST"/>
        <input type="submit" value="testPost(新增)"/>
    </form>
    <br/>
    <form action="${_contextPath}/testDelete/1" method="post">
        <input type="hidden" name="_method" value="DELETE"/>
        <input type="submit" value="testDelete(删除)"/>
    </form>
    <br/>
    <form action="${_contextPath}/testPut/1" method="post">
        <input type="hidden" name="_method" value="PUT"/>
        <input type="submit" value="testPut(修改)"/>
    </form>
    <br/>
    <a href="${_contextPath}/testGet/1">testGet</a>

    <hr/>
    <h1>测试SpringRest风格的传递参数</h1>
    <a href="${_contextPath}/testpathvariable1/1">testpathvariable1</a><br/>
    <a href="${_contextPath}/testpathvariable2/zhangsan/28">testpathvariable2</a>

    <hr/>
    <h1>测试SpringMVC的@RequestParam注解</h1>
    <form action="${_contextPath}/testRequestParam" method="post">
        姓名:<input type="text" name="username" value="zhangsan"/></br>
        年龄:<input type="text" name="age" value="18"/></br>
        <%--用学校属性来测试@RequestParam注解的required和defaultValue属性--%>
        <%--学校:<input type="text" name="school" value="ufe"/></br>--%>
        <input type="submit" value="testRequestParam"/>
    </form>

    <hr/>
    <h1>测试SpringMVC的@RequestHeader注解</h1>
    <form action="${_contextPath}/testRequestHeader" method="post">
        <input type="submit" value="testRequestHeader"/>
    </form>

    <hr/>
    <h1>测试SpringMVC的@CookieValue注解</h1>
    <form action="${_contextPath}/testCookieValue" method="post">
        <input type="submit" value="testCookieValue"/>
    </form>

    <hr/>
    <h1>测试SpringMVC接受POJO类型的参数:支持级联操作</h1>
    <form action="${_contextPath}/testPojo" method="post">
        姓名:<input type="text" name="username" value="zhangsan"/></br>
        密码:<input type="text" name="password" value="123456"/></br>
        邮件:<input type="text" name="email" value="1458687169@163.com"/></br>
        年龄:<input type="text" name="age" value="28"/></br>
        省份:<input type="text" name="address.province" value="陕西"/></br>
        城市:<input type="text" name="address.city" value="西安"/></br>
        实体中不存在的属性:<input type="text" name="other" value="实体中不存在的属性"/></br>
        <input type="submit" value="testPojo"/>
    </form>

    <hr/>
    <h1>测试在SpringMVC中使用Servlet原生API</h1>
    <form action="${_contextPath}/testServletApi" method="post">
        姓名:<input type="text" name="username" value="zhangsan"/></br>
        密码:<input type="text" name="password" value="123456"/></br>
        邮件:<input type="text" name="email" value="1458687169@163.com"/></br>
        省份:<input type="text" name="address.province" value="陕西"/></br>
        年龄:<input type="text" name="age" value="28"/></br>
        城市:<input type="text" name="address.city" value="西安"/></br>
        <input type="submit" value="testServletApi0"/>
    </form>

    <hr/>
    <h1>测试SpringMVC模型:ModelAndView/Map/ModelMap/Model/</h1>
    <h1>测试ModelAndView</h1>
    <a href="${_contextPath}/testModelAndView1">testModelAndView1</a><br/>
    <a href="${_contextPath}/testModelAndView2">testModelAndView2</a><br/>
    <a href="${_contextPath}/testModelAndView3">testModelAndView3</a><br/>
    <h2>测试ModelAndView/直接返回String重定向到一个页面</h2>
    <a href="${_contextPath}/testModelAndViewRedirectJsp1">testModelAndViewRedirectJsp1(ModelAndView)</a><br/>
    <a href="${_contextPath}/testModelAndViewRedirectJsp2">testModelAndViewRedirectJsp2(直接返回String)</a><br/>
    <h2>测试ModelAndView/直接返回String重定向到一个Controller</h2>
    <a href="${_contextPath}/testModelAndViewRedirectAnotherController1">testModelAndViewRedirectAnotherController1(ModelAndView)</a><br/>
    <a href="${_contextPath}/testModelAndViewRedirectAnotherController2">testModelAndViewRedirectAnotherController2(直接返回String)</a><br/>
    <h2>测试ModelAndView/直接返回String转发到一个Controller</h2>
    <a href="${_contextPath}/testModelAndViewForwardAnotherController1">testModelAndViewForwardAnotherController1(ModelAndView)</a><br/>
    <a href="${_contextPath}/testModelAndViewForwardAnotherController2">testModelAndViewForwardAnotherController2(直接返回String)</a><br/>

    <h1>测试在SpringMVC中使用Map<String,Object> map作为模型</h1>
    <a href="${_contextPath}/testMap">testMap</a>

    <h1>测试在SpringMVC中使用ModelMap作为模型</h1>
    <a href="${_contextPath}/testModelMap">testModelMap</a>

    <h1>测试在SpringMVC中使用Model作为模型</h1>
    <a href="${_contextPath}/testModel">testModel</a>

    <hr/>
    <h1>测试@SessionAttribuate注解</h1>
    <form action="${_contextPath}/testSessionAttribuate" method="post">
        <%
            session.setAttribute("username","zhangsan");
        %>
        <input type="submit" value="testSessionAttribuate"/>
    </form>

    <h1>测试@SessionAttribuates注解</h1>
    <a href="${_contextPath}/testSessionAttribuates">testSessionAttribuates</a>


    <h1>测试通用的逻辑视图跳转控制器</h1>
    <a href="${_contextPath}/a/success.action">testPage_success</a><br/>
    <a href="${_contextPath}/modelView">testPage_modelAndView</a>

    <hr/>
    <h1>测试@ModelAttribuate注解</h1>
    <a href="${_contextPath}/toTestModelAttribuate">跳转到测试@ModelAttribuate用法的页面</a><br/>

    <hr/>
    <h1>测试mvc:view-controller:不经过Controlelr了，直接进行页面跳转</h1>
    <a href="${_contextPath}/testMVCView-controller">testMVCView-controller</a><br/>

    <hr/>
    <h1>测试转发和重定向</h1>
    <a href="${_contextPath}/testForward">testForward</a><br/>
    <a href="${_contextPath}/testRedirect">testRedirect</a><br/>

    <hr/>
    <h1>测试转发和重定向</h1>
    <a href="${_contextPath}/testRedirectAttributesToPageParamInURL">testRedirectAttributesToPageParamInURL(拼接参数到URL中,重定向到页面)</a><br/>
    <a href="${_contextPath}/testRedirectAttributesToControllerParamInURL">testRedirectAttributesToControllerParamInURL(拼接参数到URL中,重定向到Controller)</a><br/>
    <a href="${_contextPath}/testRedirectAttributesToPageParamInSession">testRedirectAttributesToPageParamInSession(拼接参数到Session中,重定向到页面)</a><br/>
    <a href="${_contextPath}/testRedirectAttributesToControllerParamInSession">testRedirectAttributesToControllerParamInSession(拼接参数到Session中,重定向到Controller)</a><br/>

    <hr/>
    <h1>Rest风格CRUD</h1>
    <a href="${_contextPath}/listempsPage">Rest风格CRUD</a><br/>

    <hr/>
    <h1>国际化</h1>
    <a href="i18n">I18N PAGE</a>

    <hr/>
    <h1>@ResponseBody返回json格式数据</h1>
    <a href="${_contextPath}/testResponsebody">@ResponseBody返回json格式数据</a>

    <hr/>
    <h1>测试拦截器</h1>
    <a href="${_contextPath}/testInterceptor1">testInterceptor1</a><br/>
    <a href="${_contextPath}/testInterceptor2">testInterceptor2</a>

    <hr/>
    <h1>测试拦截器</h1>
    <a href="${_contextPath}/testInterceptor1">testInterceptor1</a><br/>
    <a href="${_contextPath}/testInterceptor2">testInterceptor2</a>

    <hr/>
    <h1>测试异常处理器</h1>
    <a href="${_contextPath}/testExceptionHander">testExceptionHander</a><br/>

    <hr/>
    <h1>自定义类型的转换器:字符串到时间</h1>
    <form action="${_contextPath}/testConvererStringToDate">
        <input type="text" name="time" /><br/>
        <input type="submit" value="Submit" />
    </form>

    <h1>自定义类型的转换器:字符串到Employee</h1>
    <form action="${_contextPath}/testConvererStringToEmployee">
        <input type="text" name="employee" /><br/>
        <input type="submit" value="Submit" />
    </form>

    <h1>SpringMVC处理单层Json格式的参数</h1>
    <a href="javascript:void(0);" id="dealSignJsonUseJsonObject">dealSignJsonUseJsonObject</a><br/>
    <a href="javascript:void(0);" id="dealSignJsonUseJsonString">dealSignJsonUseJsonString</a><br/>
    <h1>SpringMVC处理多层Json格式的参数</h1>
    <a href="javascript:void(0);" id="dealMutiplyJson">dealMutiplyJsonFormartParam</a><br/>

    <hr/>
    <h1>ModelAndView.addObject()和Model.addAttribute()放置同名参数问题</h1>
    <a href="${_contextPath}/mergemodel">ModelAndView.addObject()和Model.addAttribute()放置同名参数问题</a>

    <hr/>
    <h1>SpringMVC WebRequest</h1>
    <a href="${_contextPath}/testwebrequest">测试WebRequest</a>

    <hr/>
    <h1>测试在Service层获取HttpServletRequest对象</h1>
    <a href="${_contextPath}/testServiceHttpServletRequest">测试在Service层获取HttpServletRequest对象</a>

    <hr/>
    <h1>测试在WarpperHttpServlet对象</h1>
    <a href="${_contextPath}/warpperHttpServletTest?name=zhangsan">测试在WarpperHttpServlet对象</a>
</body>
</html>
