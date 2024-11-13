create database test;
use test;
create table t_user(
  id varchar(32),
  username varchar(32),
  password varchar(32)
);
insert into t_user values ('001','zhangsan','28');
insert into t_user values ('002','lisi','38');
insert into t_user values ('003','wangwu','48');