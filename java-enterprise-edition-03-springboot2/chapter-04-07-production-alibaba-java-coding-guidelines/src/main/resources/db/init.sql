-- 创建数据库
CREATE DATABASE IF NOT EXISTS j2ee;

-- 使用数据库
USE j2ee;

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
VALUES ('DATABASE_TYPE', 'MySQL', 'MySQL', 0, 0, 'MySQL数据库', 1, 1),
       ('DATABASE_TYPE', 'PostgreSQL', 'PostgreSQL', 1, 0, 'PostgreSQL数据库', 2, 1),
       ('DATABASE_TYPE', 'Oracle', 'Oracle', 0, 0, 'Oracle数据库', 3, 1),
       ('DATABASE_TYPE', 'SQLServer', 'SQLServer', 1, 0, 'SQLServer数据库', 4, 1);

-- 创建商品表
CREATE TABLE IF NOT EXISTS t_bus_product (
    id          BIGINT(20) AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    name        VARCHAR(100) NOT NULL COMMENT '商品名称',
    description VARCHAR(500) DEFAULT NULL COMMENT '商品描述',
    price       DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    stock       INT(11)      NOT NULL DEFAULT '0' COMMENT '商品库存',
    status      INT(11)      NOT NULL DEFAULT '1' COMMENT '商品状态（0：下架，1：上架）',
    create_user BIGINT COMMENT '创建人id',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_user BIGINT COMMENT '更新人id',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记 0-未逻辑删除，1-已逻辑删除',
    KEY idx_name (name),
    KEY idx_is_deleted (is_deleted)
) ENGINE = INNODB CHARSET = utf8mb4 COMMENT = '商品表';

-- 添加测试数据
INSERT INTO t_bus_product (name, description, price, stock, status, create_user)
VALUES ('商品1', '测试商品1', 19.99, 100, 1, null),
       ('商品2', '测试商品2', 29.99, 50, 1, null),
       ('商品3', '测试商品3', 39.99, 20, 1, null),
       ('商品4', '测试商品4', 49.99, 10, 1, null),
       ('商品5', '测试商品5', 59.99, 5, 1, null);