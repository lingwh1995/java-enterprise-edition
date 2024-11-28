# 创建数据库
create database javaee;
# 使用数据库
use javaee;

# 创建雇员表
create table t_employee (
    id int(11) primary key auto_increment,
    last_name varchar(20),
    email varchar(20),
    gender varchar(10),
    dept_no varchar(2)
);
insert into t_employee (id, last_name, email, gender,dept_no) values (1, 'zhangsan', 'aaa@qq.com', '男','10');
insert into t_employee (id, last_name, email, gender,dept_no) values (2, 'lisi', 'bbb@qq.com', '男','10');
insert into t_employee (id, last_name, email, gender,dept_no) values (3, 'wangwu', 'ccc@qq.com', '女','20');