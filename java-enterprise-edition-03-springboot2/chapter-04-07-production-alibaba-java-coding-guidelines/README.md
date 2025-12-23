# 项目功能介绍

## 1. 符合阿里巴巴Java开发手册
## 2. 符合RESTful风格最佳实践
## 3. 清晰标准的包结构划分
## 4. 服务器基于Undertow而非tomcat
## 5. 日志框架基于异步log4j2而非logback或同步log4j2
## 6. 数据库连接池基于HikariCP而非allibaba druid或其他

## 使用docker构建
    1.mvn clean package
    2.cd docker
    3.docker build -t alibaba-java-coding-guidelines:latest .