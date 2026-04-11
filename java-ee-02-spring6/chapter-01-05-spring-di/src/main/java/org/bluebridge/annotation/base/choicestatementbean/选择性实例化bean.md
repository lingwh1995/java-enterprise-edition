# 1.业务场景
    假设在某个包下有很多bean，有的标注了@Component注解，有的标注了@Repositoty注解，有的标注了@Service注解，有的标注了@Controller注解，现
    在由于业务的需要，只允许标注了@Component注解的bean参与Spring容器的管理，标注其他注解的bean不参与Spring容器的管理，如果要实现这个需要，就
    要使用选择性实例化功能