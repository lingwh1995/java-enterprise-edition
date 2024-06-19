##创建用户信息表
create table t_user(
                       uid int(10) not null auto_increment,
                       uname varchar(100) not null,
                       pwd varchar(100) not null,
                       rid int(10),
                       PRIMARY key(uid)
);
##创建角色信息表
create table t_role(
                       rid int(10) not null auto_increment,
                       rname varchar(100) not null,
                       rdesc varchar(200),
                       PRIMARY key(rid)
);
##创建菜单信息表
create table t_menu(
                       mid int(10) not null auto_increment,
                       mname varchar(100) not null,
                       murl varchar(100),
                       parentid int(10) not null,
                       mdesc varchar(200),
                       PRIMARY key(mid)

);
##创建角色菜单中间表
create table r_menu(
                       rid int(10) not null,
                       mid int(10) not null
);
##添加用户测试数据
insert into t_user values(default,'张三','123',1);
insert into t_user values(default,'李四','456',2);
##添加角色测试数据
insert into t_role values(default,'人事经理','管理用户信息');
insert into t_role values(default,'管理员','可以操作所有的菜单');
##添加菜单测试数据
##添加一级菜单信息
insert into t_menu values(default,'用户管理','',0,'一级菜单');
insert into t_menu values(default,'班级管理','',0,'一级菜单');
insert into t_menu values(default,'查看通告','',0,'一级菜单');
insert into t_menu values(default,'系统设置','',0,'一级菜单');
##添加二级菜单信息
insert into t_menu values(default,'用户查询','',1,'二级菜单');
insert into t_menu values(default,'增加用户','',1,'二级菜单');
insert into t_menu values(default,'班级查询','',2,'二级菜单');
insert into t_menu values(default,'增加班级','',2,'二级菜单');
##添加角色菜单测试数据
insert into r_menu values(1,1);
insert into r_menu values(1,5);
insert into r_menu values(1,6);
insert into r_menu values(2,1);
insert into r_menu values(2,2);
insert into r_menu values(2,3);
insert into r_menu values(2,4);
insert into r_menu values(2,5);
insert into r_menu values(2,6);
insert into r_menu values(2,7);
insert into r_menu values(2,8);

#查询张三的所有一级菜单
# 1.从t_user(用户信息表)中查询出张三的rid(角色id)
select * from t_user where uname = '张三' and pwd = '123';
# 2.从r_menu(角色菜单中间表)中查询张三的rid(角色id)对应的mid(菜单id)
select * from r_menu where rid = 1;
# 3.从菜单信息表(t_menu)中查询张三的rid(角色id)对应的mid(菜单id)对应的一级菜单名称
select * from t_menu where mid in(1,5,6) and parentid = 0;
