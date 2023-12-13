<%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/5/13
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>request域中的值:${requestScope.time}</h1>
    <h1>session域中的值:${sessionScope.time}</h1>
    <c:if test="${!empty session}">
        <hr/>
        <h1>session域中的值(time):${sessionScope.time}</h1>
        <hr/>
        <h1>session域中的值(address):${sessionScope.address}</h1>
        <hr/>
        <h1>session域中的值(user):${sessionScope.user}</h1>
        <hr/>
    </c:if>
</body>
</html>
