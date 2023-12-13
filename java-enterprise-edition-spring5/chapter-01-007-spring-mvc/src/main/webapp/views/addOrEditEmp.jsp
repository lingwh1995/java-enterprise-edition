<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/6/11
  Time: 19:01
  To change this template_a use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>新增或编辑EMP的页面</title>
</head>
<body>
    <%--
        SpringMVC表单标签:
            1.path:就是正常表单中的name,必须和name值一一对应,否则表单无法显示,支持级联属性
            2.为什么使用SpringMVC表单标签: 1>更快的开发出表单 2>更方便的进行数据回显
            3.使用ModelAttribute指定绑定的模型属性,如果没有指定该属性
              则默认从request域中获取command对应的表单bean,如果没有还就会报错
            4.springmvc认为表单是一定要回显的,即使是在新增时,一般编辑时会回显
            5.springmvc的表单项没有value属性
            6.springmvc path和name不冲突,可以没有name,但是必须有path
    --%>
    <form:form action="${_contextPath}/addOrEdit" method="post" modelAttribute="employee">
        <%-- 修改的时候lastName不能被显示,修改的时候id不为空--%>
        <c:if test="${employee.id == null}">
            lastName:<form:input path="lastName" name="lastName"/>
            <form:errors path="lastName"/><br/>
        </c:if>
        <%-- 修改的时候id要传回去,_method的值要改为put--%>
        <c:if test="${employee.id != null}">
            <form:hidden path="id"/><br/>
            <%--正确写法--%>
            <input type="hidden" name="_method" value="put"/>
            <%--错误写法:springmvc要求path的值和表单中modelAttributel属性绑定的bean中的属性一一对应,此modelAttribute绑定的bean是
            employee,而employee中并没有_method这个属性,而传统的html表单项并不和modelAttribute绑定的bean相关联--%>
            <%--<form:hidden path="_method"/>--%>
        </c:if>
        email:<form:input path="email" name="email"/>
        <form:errors path="email"/><br/>
        <%
            Map<String,String> genders = new HashMap<String,String>();
            genders.put("1","Male");
            genders.put("0","FeMale");
            request.setAttribute("genders",genders);
        %>
        gender:<form:radiobuttons path="gender" items="${genders}" delimiter="<br/>"/><br/>
        department:<form:select path="department.id"
                                items="${departments}"
                                itemLabel="departmentName"
                                itemValue="id"/><br/>
        <%--
            1.数据类型转换
            2.数据类型格式化
            3.数据校验
            1). 如何校验 ? 注解 ?
			①. 使用 JSR 303 验证标准
			②. 加入 hibernate validator 验证框架的 jar 包
			③. 在 SpringMVC 配置文件中添加 <mvc:annotation-driven />
			④. 需要在 bean 的属性上添加对应的注解
			⑤. 在目标方法 bean 类型的前面添加 @Valid 注解
			2). 验证出错转向到哪一个页面 ?
			注意: 需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参
			3). 错误消息 ? 如何显示, 如何把错误消息进行国际化


            关于国际化:
            1. 在页面上能够根据浏览器语言设置的情况对文本(不是内容), 时间, 数值进行本地化处理
            2. 可以在 bean 中获取国际化资源文件 Locale 对应的消息
            3. 可以通过超链接切换 Locale, 而不再依赖于浏览器的语言设置情况

            解决:
            1. 使用 JSTL 的 fmt 标签
            2. 在 bean 中注入 ResourceBundleMessageSource 的示例, 使用其对应的 getMessage 方法即可
            3. 配置 LocalResolver 和 LocaleChangeInterceptor
        --%>
        birth:<form:input path="birth"/>
        <form:errors cssStyle="color: red;" path="birth"/><br/>
        salary:<form:input path="salary"/><br/>
        <input type="submit" value="submit"/>
    </form:form>
</body>
</html>
