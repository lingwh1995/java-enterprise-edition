# 基础镜像阶段：定义公共基础配置
FROM eclipse-temurin:21.0.9_10-jre-alpine-3.23 as base

# 维护者信息
LABEL maintainer="lingwh"

# 安装时区数据和中文字符集支持
RUN apk add --no-cache tzdata fontconfig ttf-dejavu && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 设置工作目录
WORKDIR /app


# 构建阶段：使用基础镜像作为起点
FROM base as builder

# 复制打包好的JAR文件到构建器中
COPY application.jar app.jar


# 运行阶段：使用基础镜像作为起点
FROM base as runtime

# 从构建阶段复制JAR文件到运行时镜像
COPY --from=builder /app/app.jar /app/app.jar

# 暴露应用端口（通常Spring Boot默认是8080）
EXPOSE 8080

# 设置JVM参数以优化容器环境中的性能
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Xmx512m"

# 运行应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]