# 创建数据库
create database javaee;
# 使用数据库
use javaee;

# 创建表 汽⻋表t_car
CREATE TABLE t_car(
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '自然主键',
    car_num VARCHAR(255) COMMENT '汽车编号',
    brand VARCHAR(255) COMMENT '汽车品牌',
    guide_price DECIMAL(10, 2) COMMENT '厂家指导价',
    produce_time CHAR(10) COMMENT '生产时间 如：2022-10-11',
    car_type VARCHAR(255) COMMENT '汽车类型'
);
# 添加数据
INSERT INTO t_car(car_num, brand, guide_price, produce_time, car_type)
VALUES ('1001', '宝马520Li', 10.00, '2022-10-11', '燃油车'),
       ('1002', '奔驰E300L', 55.00, '2022-11-11', '新能源');


# 创建日志表t_log_202401
create table `t_log_202401`
(
    id   int primary key auto_increment,
    log  varchar(255),
    time datetime
);

INSERT INTO `t_log_202401` (`id`, `log`, `time`)
VALUES (1, '插入数据', '2024-01-01 11:08:31');
INSERT INTO `t_log_202401` (`id`, `log`, `time`)
VALUES (2, '删除数据', '2024-01-02 11:08:45');

# 创建日志表t_log_202402
create table `t_log_202402`
(
    id   int primary key auto_increment,
    log  varchar(255),
    time datetime
);

INSERT INTO `t_log_202402` (`id`, `log`, `time`)
VALUES (1, '插入数据', '2024-02-01 11:08:31');
INSERT INTO `t_log_202402` (`id`, `log`, `time`)
VALUES (2, '删除数据', '2024-02-02 11:08:45');