# 1.什么情况下会出现循环依赖问题
    只有当两个bean的scope都是prototype时才会出现异常，其中任何一个bean的scope为singleton就不会报错