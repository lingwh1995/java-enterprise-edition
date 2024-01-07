create table tbl_employee (
    id varchar2(32) primary key,
    last_name varchar2(50),
    email varchar2(50),
    gender varchar2(50)
);
insert into tbl_employee ("id", "last_name", "email", "gender") values ('1', 'zhangsan', '1458687169@qq.com', '男');

create sequence tbl_employee_seq
minvalue 1  --最小值
--nomaxvalue  --不设置最大值(由机器决定)，或 根据表字段的值范围设置 maxvalue
maxvalue 9999999  -- 最大值
start with 1   --从1开始计数，数值可变
increment by 1  --每次加1，数值可变
nocycle  --一直累加，不循环；cycle：达到最大值后，将从头开始累加
nocache;

--使用id和tbl_employee中id相关联
create table tbl_department (
    id varchar2(32) primary key,
    dname varchar2(50)
);