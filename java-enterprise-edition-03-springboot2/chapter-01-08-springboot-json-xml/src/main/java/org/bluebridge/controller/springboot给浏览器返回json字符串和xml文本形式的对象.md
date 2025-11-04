# 1.返回Json字符串形式的对象
## 1.1.在方法上添加@ResponseBody注解
    @ResponseBody
    @RequestMapping(value = "/responseBody/object")
    public User responseObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }
# 2.返回Xml文本形式的对象
## 2.1.引入pom依赖
    <!-- 不用添加版本号，版本号会自动和jackson版本保持一致 -->
    <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-xml</artifactId>
    </dependency>
## 2.2.在方法上添加@ResponseBody注解和@RequestMapping注解
    @ResponseBody
    @RequestMapping(value = "/responseBody/xmlText/object", produces={"application/xml; charset=UTF-8"})
    public User responseXmlTextFormatObjectToBrowserByResponseBody() {
        User user = new User("001", "张三", "123456", "zhangsan@gmail.com");
        return user;
    }