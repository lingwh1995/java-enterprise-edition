-- 数据库环境初始化
CREATE DATABASE IF NOT EXISTS code_generator;

USE code_generator;

-- 创建用户表
CREATE TABLE t_sys_user (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(32) NOT NULL COMMENT '登录账号',
    nickname    VARCHAR(32) NOT NULL COMMENT '用户昵称',
    password    CHAR(64)    NOT NULL COMMENT '用户密码',
    email       VARCHAR(32) NULL COMMENT '用户邮箱',
    salt        VARCHAR(16) NULL COMMENT '用户密码盐',
    create_user BIGINT COMMENT '创建人id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user BIGINT COMMENT '更新人id',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记 0-未逻辑删除，1-已逻辑删除',
    KEY idx_username (username),
    KEY idx_is_deleted (is_deleted)
) ENGINE = INNODB CHARSET = utf8mb4 COMMENT '用户表';

-- 插入示例数据
INSERT INTO t_sys_user (username, nickname, password, salt, create_user)
VALUES ('admin', '管理员', '87B63A5A6286C569121B3C5444CE154C', '899DF0EADE99EA70', null);

-- 字典表
CREATE TABLE t_sys_dict (
    id          INT AUTO_INCREMENT PRIMARY KEY COMMENT '字典主键',
    dict_code   VARCHAR(16) UNIQUE NOT NULL COMMENT '字典编码',
    dict_name   VARCHAR(32) NOT NULL COMMENT '字典名称',
    dict_type   TINYINT(1) UNIQUE NOT NULL COMMENT '字典类型 0:系统字典 1:业务字典',
    dict_status TINYINT(1) DEFAULT 0 COMMENT '字典状态 0:正常 1:停用',
    dict_desc   VARCHAR(512) COMMENT '字典描述',
    sort_order  SMALLINT DEFAULT 0 COMMENT '排序',
    create_user BIGINT COMMENT '创建人id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user BIGINT COMMENT '更新人id',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除标记 0-未逻辑删除，1-已逻辑删除',
    UNIQUE KEY idx_dict_code (dict_code),
    KEY idx_dict_name (dict_name),
    KEY idx_is_deleted (is_deleted)
) ENGINE = INNODB CHARSET = utf8mb4 COMMENT = '字典类型表';

-- 插入字典表示例数据
INSERT INTO t_sys_dict (dict_code, dict_name, dict_type, dict_status, dict_desc, sort_order, create_user)
VALUES ('DATABASE_TYPE', '数据库类型', 0, 0, '数据库类型', 1, 1);

-- 字典数据表
CREATE TABLE t_sys_dict_data (
    id             INT AUTO_INCREMENT PRIMARY KEY COMMENT '字典数据主键',
    dict_code      VARCHAR(16) NOT NULL COMMENT '字典编码',
    dict_label     VARCHAR(32) NOT NULL COMMENT '字典标签',
    dict_value     VARCHAR(32) NOT NULL COMMENT '字典键值',
    is_default     TINYINT(1) DEFAULT 1 COMMENT '是否默认 0:是 1:否',
    dict_status    TINYINT(1) DEFAULT 0 COMMENT '字典数据状态 0:正常 1:停用',
    dict_data_desc VARCHAR(256) COMMENT '字典数据描述',
    sort_order     SMALLINT DEFAULT 0 COMMENT '排序',
    create_user    BIGINT COMMENT '创建人id',
    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user    BIGINT COMMENT '更新人id',
    update_time    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除标记 0-未逻辑删除，1-已逻辑删除',
    UNIQUE KEY idx_dict_label_value (dict_label, dict_value),
    KEY idx_dict_value (dict_value),
    KEY idx_is_deleted (is_deleted)
) ENGINE = INNODB CHARSET = utf8mb4 COMMENT = '字典数据表';

-- 插入字典数据表示例数据
INSERT INTO t_sys_dict_data (dict_code, dict_label, dict_value, is_default, dict_status, dict_data_desc, sort_order, create_user)
VALUES ('DATABASE_TYPE', 'MySQL', 'MySQL', 0, 0, 'MySQL数据库', 1, null),
       ('DATABASE_TYPE', 'PostgreSQL', 'PostgreSQL', 1, 0, 'PostgreSQL数据库', 2, null),
       ('DATABASE_TYPE', 'Oracle', 'Oracle', 0, 0, 'Oracle数据库', 3, null),
       ('DATABASE_TYPE', 'SQLServer', 'SQLServer', 1, 0, 'SQLServer数据库', 4, null);

-- 创建数据源表
CREATE TABLE t_bus_datasource (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL COMMENT '数据源名称',
    type        VARCHAR(50)  NOT NULL COMMENT '数据库类型(mysql,postgresql,oracle,sqlserver)',
    host        VARCHAR(100) NOT NULL COMMENT '主机地址',
    port        INT          NOT NULL COMMENT '端口号',
    `database`    VARCHAR(100) NOT NULL COMMENT '数据库',
    username    VARCHAR(100) NOT NULL COMMENT '用户名',
    password    VARCHAR(100) NOT NULL COMMENT '密码',
    enable      TINYINT(1) DEFAULT 0  COMMENT '是否可用 0:不可用 1:可用',
    create_user BIGINT COMMENT '创建人id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user BIGINT COMMENT '更新人id',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记 0-未逻辑删除，1-已逻辑删除',
    KEY idx_name (name),
    KEY idx_type (type),
    KEY idx_is_deleted (is_deleted)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '数据源表';

-- 插入示例数据
INSERT INTO t_bus_datasource (name, type, host, port, `database`, username, password, is_deleted, create_user)
VALUES
    ('本地mysql', 'mysql', 'localhost', 3306, 'test_db', 'root', '123456', 0, null),
    ('生产环境postgresql', 'postgresql', '192.168.1.100', 5432, 'prod_db', 'admin', '123456', 0, null);


-- 项目方案表
CREATE TABLE t_bus_project (
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(32)  NOT NULL COMMENT '项目名称',
    springboot_version   VARCHAR(32)  NOT NULL COMMENT 'springboot版本',
    repository_framework VARCHAR(32)  NOT NULL COMMENT '持久层框架',
    view_framework       VARCHAR(32)  NOT NULL COMMENT '显示层框架',
    context_path         VARCHAR(256) NULL     COMMENT '上下文路径',
    port                 INT          NULL     COMMENT '访问端口',
    status               TINYINT      DEFAULT 1 COMMENT '状态 1:正常 0:禁用',
    `desc`                 VARCHAR(256) NULL     COMMENT '项目描述',
    create_user BIGINT COMMENT '创建人id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user BIGINT COMMENT '更新人id',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记 0-未逻辑删除，1-已逻辑删除',
    KEY idx_name (name),
    KEY idx_is_deleted (is_deleted)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '项目表';

-- 实体模型表
CREATE TABLE t_bus_entity (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    package_name VARCHAR(32) NOT NULL COMMENT '包名称',
    name         VARCHAR(32) NOT NULL COMMENT '实体名称',
    `desc`         VARCHAR(256) NULL    COMMENT '实体描述',
    sql_code     TEXT        NOT NULL COMMENT 'sql代码',
    java_code    TEXT        NOT NULL COMMENT 'java代码',
    status       TINYINT   DEFAULT 1  COMMENT '状态 1:正常 0:禁用',
    scheme_id    INT         NOT NULL COMMENT '方案id',
    create_user BIGINT COMMENT '创建人id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user BIGINT COMMENT '更新人id',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记 0-未逻辑删除，1-已逻辑删除',
    KEY idx_name (name),
    KEY idx_is_deleted (is_deleted)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT = '实体表';