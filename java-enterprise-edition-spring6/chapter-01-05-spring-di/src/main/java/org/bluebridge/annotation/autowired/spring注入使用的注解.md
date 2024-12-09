# 1.注入使用的注解
    @Autowired  按类型进行注入
    @Autowired + @Qualifier  按名称进行注入
# 2.@Autowired可以配置的位置
    位置1     可以在属性上
    位置2     可以配置在setter方法上
    位置3     可以配置在构造方法上

    @Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Autowired {
    
        /**
         * Declares whether the annotated dependency is required.
         * <p>Defaults to {@code true}.
         */
        boolean required() default true;
    
    }