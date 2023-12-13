<%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/6/11
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%--正确引入jquery--%>
    <script type="text/javascript" src="${_contextPath}/resources/js/jquery2.2.js"></script>
    <%--错误引入jquery--%>
    <%--<script type="text/javascript" src="${_contextPath}/resources/js/jquery2.2.js"/>--%>
    <script>
        $(function () {
            $(".delete").click(function () {
                $("form").attr("action",$(this).attr("href")).submit();
                return false;
            });
        });
    </script>
</head>
<body>

    <form action="" method="post">
        <input type="hidden" name="_method" value="delete"/>
    </form>


    <c:if test="${empty requestScope.emps}">
        没有员工信息！<br/>
    </c:if>
    <%--
        EL表达式获取数组:${requestScope.emps}
        EL表达式获取数组中第0个元素::${requestScope.emps}[0]
    --%>
    <c:if test="${!empty requestScope.emps}">
        <table border="1px solid red" cellspacing="0">
            <tr>
                <th>ID</th>
                <th>lastName</th>
                <th>gender</th>
                <th>email</th>
                <th>departmentName</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${requestScope.emps}" var="emp">
            <tr>
                <th>${emp.id}</th>
                <th>${emp.lastName}</th>
                <th>${emp.gender}</th>
                <th>${emp.email}</th>
                <th>${emp.department.departmentName}</th>
                <th><a href="${_contextPath}/addOrEditempPage/${emp.id}">Edit</a></th>
                <th><a class="delete" href="delete/${emp.id}">Delete</a></th>
            </tr>
            </c:forEach>
        </table>
    </c:if>
    <a href="${_contextPath}/addOrEditempPage">ADD NEW EMPLOYEE</a>
</body>
</html>
