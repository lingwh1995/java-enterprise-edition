# 1.Spring中Bean的生命周期五步
    第一步：实例化Bean
    第二步：Bean属性赋值
    第三步：初始化Bean
    第四步：使用Bean
    第五步：销毁Bean
# 2.Spring中Bean的生命周期七步
    第一步：实例化Bean
    第二步：Bean属性赋值
    第三步：执行Bean后处理器的before方法(新增步骤)
    第四步：初始化Bean
    第五步：执行Bean后处理器的after方法(新增步骤)
    第六步：使用Bean
    第七步：销毁Bean
# 3.Spring中Bean的生命周期十步，比七步多的三步分别是在以下三个点位
    点位1：在Bean后处理器的before方法执行之前
            检查Spring中Bean是否实现了BeanNameAware, BeanClassLoaderAware, BeanFactoryAware这个接口中的一个或者全部
    点位2：在Bean后处理器的before方法执行之后
            检查Spring中Bean是否实现了InitializingBean接口
    点位3：在使用Bean之后或者说在销毁Bean之前
            检查Spring中Bean是否实现了DisposableBean接口
# 4.Bean的作用域与生命周期管理
    Spirng容器只对作用域为singleton的Bean进行完整的生命周期管理
    如果是prototype作用域的bean，Spring容器负责将Bean初始化完成，等客户端一旦获取到Bean之后，Spring容器就不再管理该Bean的生命周期了