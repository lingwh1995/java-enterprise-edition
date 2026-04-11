use `javaee`;

# 创建雇员表
create table `t_employee` (
    `id` bigint primary key auto_increment,
    `last_name` varchar(20),
    `email` varchar(20),
    `gender` varchar(10),
    `dept_no` varchar(2)
);
insert into `t_employee` (`last_name`, `email`, `gender`,`dept_no`) values ('zhangsan', '1458687169@qq.com', '男','10');
insert into `t_employee` (`last_name`, `email`, `gender`,`dept_no`) values ('lisi', '1458687169@qq.com', '男','10');
insert into `t_employee` (`last_name`, `email`, `gender`,`dept_no`) values ('王五', '2926517283@qq.com', '女','20');