# 使用国内OpenJDK 21 JRE Alpine镜像
FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23

# 维护者信息
LABEL maintainer="lingwh"

# 安装时区数据
RUN apk add --no-cache tzdata
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone

# 设置工作目录
WORKDIR /app

# 添加依赖包，用于处理中文字符集
RUN apk add --no-cache fontconfig ttf-dejavu

# 复制打包好的JAR文件到容器中
COPY application.jar app.jar

# 暴露应用端口（通常Spring Boot默认是8080）
EXPOSE 8080

# 设置JVM参数以优化容器环境中的性能
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Xmx512m"

# 运行应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]