# 创建数据库
create database mybatis;
# 使用数据库
use mybatis;
# 创建表 汽⻋表t_car
create table t_car(
    id bigint primary key auto_increment comment '自然主键',
    car_num varchar(255) comment '汽车编号',
    brand varchar(255) comment '汽车品牌',
    guide_price decimal(10, 2) comment '厂家指导价',
    produce_time char(10) comment '生产时间 如：2022-10-11',
    car_type varchar(255) comment '汽车类型'
);
# 添加数据
insert into t_car(car_num, brand, guide_price, produce_time, car_type)
values ('1001', '宝马520li', 10.00, '2022-10-11', '燃油车'),
       ('1002', '奔驰e300l', 55.00, '2022-11-11', '新能源');
