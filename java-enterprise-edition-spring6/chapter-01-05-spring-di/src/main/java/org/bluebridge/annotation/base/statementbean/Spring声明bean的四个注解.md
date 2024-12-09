# 1.声明bean的四个注解
    @Component
    @Repository
    @Service
    @Controller
# 2.这个四个注解之间的关系
    @Repository、@Service、@Controller这三个注解是@Component这个注解的别名，具体可以点进源码查看
# 3.使用注解配置方式配置声明bean的注意事项
    以@Component注解为例，其他注解同理

        @Component("user")
        public class UserInjectByAnnotationComponent {
    
        }
    
        @Component
        public class UserInjectByAnnotationComponent {
    
        }
    如果@Component配置了value属性的值，则使用时取配置的value属性的值，
    如果@Component没有配置value属性的值，则使用时取类名的首字母小写