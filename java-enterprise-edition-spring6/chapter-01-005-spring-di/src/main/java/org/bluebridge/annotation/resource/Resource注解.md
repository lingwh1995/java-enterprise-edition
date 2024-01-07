# 1.@Resource注解使用场景
    @Resource用于完成非简单类型的注入
# 2.@Resource注解和@Autowired注解的区别
    1.@Resource是JDK提供的规范，@Autowired是Spring框架提供的
    2.@Resource默认ByName进行装配，未指定name时，使用属性名作为name，通过name找不到对应的bean会ByType进行装配
      @Autowired默认ByType进行装配，如果想根据名称进行装配，则需要配置@Qualifier注解一起使用
    3.@Resource用在属性、setter方法上，@Autowired属性、setter方法、构造方法上
# 3.如何使用
    JDK8环境                  不需要额外引入依赖
    低于JDK8高于JDK11环境       需要引入依赖
        Spring5环境引入下面依赖
            <dependency>
                <groupId>javax.annotation</groupId>
                <artifactId>javax.annotation-api</artifactId>
                <version>1.3.2</version>
            </dependency>
        Spring6环境引入下面依赖
            <dependency>
               <groupId>jakarta.annotation</groupId>
               <artifactId>jakarta.annotation-api</artifactId>
               <version>2.1.1</version>
            </dependency>
