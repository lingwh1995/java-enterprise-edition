<%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/5/12
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Success Pape!</h1>
    <c:if test="${!empty sessionScope.time}">
        <h1>${sessionScope.time}</h1>
    </c:if>
    <c:if test="${!empty sessionScope.time1}">
        <h1>${sessionScope.time1}</h1>
    </c:if>

    <%--
        测试从域中获取使用@ModelAttribute直接暴露在域中的数据，而不是
        通过Model/ModelAndView/Map/MoedelMap/HttpServelt等放入域中的数据,
        获取该数据的方法:
                @ModelAttribute("cityList")
                public List cityList() {
                    return Arrays. asList("北京", "山东");
                }
        ${key},key的值就是@ModelAttribute注解中value属性的值
    --%>
    <c:if test="${modelattributeexposedatatomodel}">
        ${requestScope.cityList[0]}
        ${sessionScope.cityList[0]}
    </c:if>
</body>
</html>
