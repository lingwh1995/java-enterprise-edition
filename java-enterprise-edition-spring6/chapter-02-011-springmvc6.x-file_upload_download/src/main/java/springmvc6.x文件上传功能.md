SpringMVC6.x版本文件上传注意事项:
    1.xml配置文件中添加
        <bean id="multipartResolver" class="org.springframework.web.multipart.support.StandardServletMultipartResolver"/>    
    2.不用引入commons-fileupload这个jar，只引入commons-io这个jar就可以了
    3.编写上传代码的类上要添加 @MultipartConfig 这个注解 
    4.工作时推荐使用多文件上传功能，不推荐使用单文件上传功能