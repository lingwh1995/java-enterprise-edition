# 1. <mvc:view-controller path="/" view-name="index"/>作用
    用于代替一些只进行页面跳转而不作任何其他操作的代码
# 2.<mvc:annotation-driven/>作用
    一旦配置了<mvc:view-controller path="/" view-name="index"/>这个，所有的使用@RequestMapping(value="xxx")配置的
    路径都会失效，访问时会报404错误，为了解决这个报错，还要加上<mvc:annotation-driven/>这个配置，才能使@RequestMapping(value="xxx")
    中配置的路径生效，此外，如果要对静态资源进行放行，也需要加上这个配置
    