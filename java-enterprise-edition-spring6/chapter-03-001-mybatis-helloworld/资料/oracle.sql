create sequence t_car_seq
    minvalue 1  --最小值
    --nomaxvalue  --不设置最大值(由机器决定)，或 根据表字段的值范围设置 maxvalue
    maxvalue 999999999999999999999999  -- 最大值
    start with 1   --从1开始计数，数值可变
    increment by 1  --每次加1，数值可变
    nocycle  --一直累加，不循环；cycle：达到最大值后，将从头开始累加
    nocache;

-- 创建表 汽⻋表t_car
create table t_car(
    id number primary key,
    car_num varchar2(255),
    brand varchar2(255),
    guide_price number(10, 2),
    produce_time varchar2(10),
    car_type varchar2(255)
);

-- 添加数据
insert into t_car (id,car_num,brand,guide_price,produce_time,car_type) values (t_car_seq.nextval,'1001','宝马520li',10,'2022-10-11','燃油车');
insert into t_car (id,car_num,brand,guide_price,produce_time,car_type) values (t_car_seq.nextval,'1002','奔驰e300l',55,'2022-11-11','新能源');