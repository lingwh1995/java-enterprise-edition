# 1.返回Json字符串形式的对象
## 1.1.引入pom依赖
       <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
## 1.2.修改配置文件
    在配置文件中添加 <mvc:annotation-driven/>，如果不添加这个配置，会报406错误
    开启注解驱动后，此时HandlerAdaptor中会自动装配一个消息转换器:MappingJsckson2HttpMessageConverter，可以将给浏览器响应的Java对象转换为JSON字符串
## 1.3.在方法上添加@ResponseBody注解
    @ResponseBody
    @RequestMapping(value = "/responseBody/object")
    public User responseObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }
# 2.返回Xml文本形式的对象
## 2.1.引入pom依赖
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-xml</artifactId>
    </dependency>
## 2.2.修改配置文件
    在配置文件中添加 <mvc:annotation-driven/>，如果不添加这个配置，会报406错误
    开启注解驱动后，此时HandlerAdaptor中会自动装配一个消息转换器:MappingJsckson2HttpMessageConverter，可以将给浏览器响应的Java对象转换为JSON字符串
## 2.3.在方法上添加@ResponseBody注解
    @ResponseBody
    @RequestMapping(value = "/responseBody/xmlText/object", produces={"application/xml; charset=UTF-8"})
    public User responseXmlTextFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }