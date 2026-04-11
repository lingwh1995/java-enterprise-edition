<%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/5/13
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    console.log();
</script>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>测试@ModelAttribuate注解(不用在方法参数中):其他的字段都可以需改，Id和密码不能修改,所以不往后台传递密码字段</h1>
    <form action="${_contextPath}/testModelAttribuateNoUserInFunctionParam">
        <input type="hidden" name="id" value="${person.id}"/></br>
        姓名:<input type="text" name="username" value="${person.username}+NoUseInfunctionParam"/></br>
        <%--密码:<input type="text" name="password" value="${person.password}"/></br>--%>
        邮件:<input type="text" name="email" value="${person.email}"/></br>
        年龄:<input type="text" name="age" value="${person.age}"/></br>
        <input type="submit" value="testModelAttribuateNoUserInFunctionParam"/>
    </form>

    <hr/>
    <h1>测试@ModelAttribuate注解(用在方法参数中,通过模型添加给域中添加数据):其他的字段都可以需改，Id和密码不能修改,所以不往后台传递密码字段</h1>
    <form action="${_contextPath}/testModelAttribuateUserInFunctionParam">
        <input type="hidden" name="id" value="${person.id}"/></br>
        姓名:<input type="text" name="username" value="${person.username}+UseInfunctionParam"/></br>
        <%--密码:<input type="text" name="password" value="${person.password}"/></br>--%>
        邮件:<input type="text" name="email" value="${person.email}"/></br>
        年龄:<input type="text" name="age" value="${person.age}"/></br>
        <input type="submit" value="testModelAttribuateUserInFunctionParam"/>
    </form>

    <hr/>
    <h1>测试@ModelAttribuate注解(用在方法参数中，通过@ModelaAttribuate("xxx")直接把数据暴露在模型中):</h1>
    <a href="${_contextPath}/testmodelattributesuperior/expose_data_tomodel_automic">测试@ModelAttribuate注解(用在方法参数中，通过@ModelaAttribuate("xxx")直接把数据暴露在模型中)</a><br/>

    <h1>测试@ModelAttribuate注解高级特性:和url路径传参结合，把路径中的参数封装到实体中</h1>
    <a href="${_contextPath}/testmodelattributesuperior/getparamvaluefromurl/zhangsan">@ModelAttribuate自动将路径中参封装到实体中</a><br/>
    <h1>@ModelAttribuate把数据自动暴露到模型(Model/Map/ModelMap)中，并从模型(Model/Map/ModelMap)中获取该数据</h1>
    <a href="${_contextPath}/testmodelattributesuperior/exposedatatomodel">@ModelAttribuate把数据暴露到模型(Model/Map/ModelMap)中，并从模型(Model/Map/ModelMap)中获取该数据</a><br/>
</body>
</html>
