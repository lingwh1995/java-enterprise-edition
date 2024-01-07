# 创建数据库
create database mybatis;
# 使用数据库
use mybatis;
# 创建雇员表
create table t_employee (
    id varchar(32) primary key,
    last_name varchar(50),
    email varchar(50),
    gender varchar(50)
);
insert into t_employee (id, last_name, email, gender) values ('1', 'zhangsan', '1458687169@qq.com', '男');

-- 使用id和tbl_employee中id相关联
create table t_department (
    id varchar(32) primary key,
    dname varchar(50)
);