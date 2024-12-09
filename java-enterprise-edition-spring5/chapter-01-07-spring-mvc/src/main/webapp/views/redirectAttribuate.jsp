<%--
  Created by IntelliJ IDEA.
  User: ronin
  Date: 2019/5/16
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
   -- <h1>${name}</h1>--</br>
   == <h1>${sessionScope['org.springframework.web.servlet.support.SessionFlashMapManager.FLASH_MAPS'][0]['name']}</h1>==

</body>
</html>
