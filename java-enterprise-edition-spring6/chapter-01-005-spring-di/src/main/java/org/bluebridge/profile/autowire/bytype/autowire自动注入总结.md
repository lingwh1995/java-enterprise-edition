# 1.autowire自动装配分类
    byName  按照属性名称进行自动装配
    byType  按照属性类型进行自动装配
# 2.autowire自动装配条件
    必须要为属性提供setter方法，因为autowire是基于set方式进行自动装配的
# 3.根据类型进行自动装配的缺陷
    配置文件中属性的实例(bean标签)只能有一个，如果有两个，就算加了id对不同的bean进行区别，还是会报错的

