# 1.方式一     激活Tomcat的defaultServlet来处理静态文件
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.jpg</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.js</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>default</servlet-name>
        <url-pattern>*.css</url-pattern>
    </servlet-mapping>
# 2.方式二     配置如下代码

    <mvc:resources mapping="/images/**" location="/images/" />
    <mvc:annotation-driven />

    ResourceHttpRequestHandler 进行处理，location指定静态资源的位置.可以是web application根目录下、jar包里面，这样可以把静态资源压缩到jar包中。cache-period可以使得静态资源进行web cache
    如果出现下面的错误，可能是没有配置 <mvc:annotation-driven /> 的原因。
    报错WARNING: No mapping found for HTTP request with URI [/mvc/user/findUser/lisi/770] in DispatcherServlet with name 'springMVC'使用 <mvc:resources/> 元素,把 mapping 的 URI 注册到 SimpleUrlHandlerMapping的urlMap 中,
    key 为 mapping 的 URI pattern值,而 value为 ResourceHttpRequestHandler,
    这样就巧妙的把对静态资源的访问由 HandlerMapping 转到 ResourceHttpRequestHandler 处理并返回,所以就支持 classpath 目录, jar 包内静态资源的访问.
    另外需要注意的一点是,不要对 SimpleUrlHandlerMapping 设置 defaultHandler. 因为对 static uri 的 defaultHandler 就是ResourceHttpRequestHandler,
    否则无法处理static resources request.
# 3.方式三     配置如下代码
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven />