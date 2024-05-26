# 创建数据库
create database mybatis;
# 使用数据库
use mybatis;
# 创建雇员表
create table t_employee (
    id int(11) primary key auto_increment,
    last_name varchar(20),
    email varchar(20),
    gender varchar(10),
    dept_no varchar(2)
);
insert into t_employee (id, last_name, email, gender,dept_no) values (1, 'zhangsan', '1458687169@qq.com', '男','10');

-- 使用deptNo和t_employee中id相关联
create table t_department (
    id int(11) primary key auto_increment,
    dept_no varchar(2),
    dept_name varchar(20)
);
insert into t_department(id,dept_no,dept_name) values (1,'10','研发部门');