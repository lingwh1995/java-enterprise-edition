create database master;
use master;
create table t_user(
  id int primary key auto_increment,
  username varchar(32),
  password varchar(32)
);
insert into t_user(username,password) values ('001','123456');
insert into t_user(username,password) values ('002','123456');
insert into t_user(username,password) values ('003','123456');
insert into t_user(username,password) values ('004','123456');
insert into t_user(username,password) values ('005','123456');

create database slave;
use slave;
create table t_user(
   id int primary key auto_increment,
   username varchar(32),
   password varchar(32)
);
insert into t_user(username,password) values ('001','123456');
insert into t_user(username,password) values ('002','123456');
insert into t_user(username,password) values ('003','123456');
insert into t_user(username,password) values ('004','123456');
insert into t_user(username,password) values ('005','123456');