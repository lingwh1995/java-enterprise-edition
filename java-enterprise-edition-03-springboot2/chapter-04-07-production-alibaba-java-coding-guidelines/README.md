# 项目介绍

## 1.功能点介绍
- 符合阿里巴巴Java开发手册
- 符合RESTful风格最佳实践
- 清晰标准的包结构划分
- 服务器基于 Undertow+Docker 部署而非 tomcat+war 部署
- 日志框架基于异步log4j2而非logback或同步log4j2
- 数据库连接池基于HikariCP而非allibaba druid或其他
## 2.使用docker构建
- mvn clean package
- cd docker
- docker build -t alibaba-java-coding-guidelines:latest .