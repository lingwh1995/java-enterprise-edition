# 1.什么是事务?
    在一个业务流程中，通常需要多条DML(INSERT、UPDATE、DELETE)语句共同联合才能完成。这几条DML语句必须同时成功或者同时失败，这样才能保证数据的安全。
    多条DML语句要么同时成功，要么同时失败，这叫做事务
# 2.事务四大特征(一原持久隔离)
    A(Atomicity)原子性     所有事务操作都是最小的单元，不可再分
    C(Consistency)一致性   事务要么同时成功要么同时失败，事务前和事务后的总量不改变
    I(Isolation)隔离性     事务和事务之间要有隔离性，才能保证互不影响，主要是体现在多事务场景中
    D(Durability)持久性    持久性是事务结束的标志
# 3.事务的四个处理过程
    第一步     开始事务(start transaction)
    第二步     执行核心业务代码
    第三步     提交事务:如果事务过程中没有出现异常则提交事务(commit transaction)
    第四步     回滚事务:如果事务过程中发生了异常则回滚事务(rollback transaction)
# 4.Spring支持的事务操作
    编程式事务
    声明式事务
# 5.Spring框架的事务管理相关的类和API
	1. PlatformTransactionManager接口		-- 平台事务管理器.(真正管理事务的类)。该接口有具体的实现类，根据不同的持久层框架，需要选择不同的实现类！
	2. TransactionDefinition接口			-- 事务定义信息.(事务的隔离级别,传播行为,超时,只读)
	3. TransactionStatus接口				-- 事务的状态
	
	4. 总结：上述对象之间的关系：平台事务管理器真正管理事务对象.根据事务定义的信息TransactionDefinition 进行事务管理，在管理事务中产生一些状态.将状态记录到TransactionStatus中
	
	5. PlatformTransactionManager接口中实现类和常用的方法
		1. 接口的实现类
			* 如果使用的Spring的JDBC模板或者MyBatis框架，需要选择DataSourceTransactionManager实现类
			* 如果使用的是Hibernate的框架，需要选择HibernateTransactionManager实现类
		
		2. 该接口的常用方法
			* void commit(TransactionStatus status) 
			* TransactionStatus getTransaction(TransactionDefinition definition) 
			* void rollback(TransactionStatus status) 
 	
# 6.常见事务问题和事务隔离级别
    事务的并发操作中可能会出现脏读、不可重复读、幻读、事务丢失问题
        脏读(读了未提交的事务，然后被回滚了)
            事务A中读取了事务B中尚未提交的数据，如果B事务回滚，则A读取使用了错误的数据
        不可重复读(读取了提交的新事物，指更新操作)
            不可重复读是指在对于数据库中的某个数据，一个事务范围内多次查询却返回了不同的值，这是由于在查询间隔，被另一个事务修改并提交了
        幻读(也是指读取了提交的新事物，指增删操作)
            在事务A多次读取构过程中，事务B对数据进行了新增操作，导致了A事务多次读取的值不一致
        第一类事务丢失(称为回滚丢失)
            对于第一类事务丢失，也称为覆盖丢失，就是A和B一起执行一个数据，两个同时取到了一个数据，然后B事务已经提交了，A事务回滚，这样B事务的操作就因为A事务的回滚而丢失了
        第二类事务丢失(提交覆盖丢失)
            A事务和B事务一起执行一个数据，同时取到一个数据，然后B事务先提交，A事务后面又提交，这样A事务就覆盖了B事务
    数据库事务隔离级别有四种，从低到高分别是 Read Uncommited、Read Commited、Repeatable Read、Serializable
		Read Uncommited(读未提交)
			一个事务可以读取到另一个事务未提交的数据，会产生脏读
		Read Commited(读已提交)[Oracle、SqlServer默认隔离级别]
			一个事务要等另一个事务提交后才能读取数据，会产生不可重复读
		Repeatable Read(可重复读)[Mysql默认事务隔离级别]
			在开始读取数据(事务开启时)，不再允许修改操作，可能会产生幻读
		Serializable
        	事务串行化顺序执行，可以避免 脏读、不可重复读与幻读，但是这个隔离级别效率很低下，影响数据库效率
TransactionDefinition
		1. 事务隔离级别的常量
			* static int ISOLATION_DEFAULT 					-- 采用数据库的默认隔离级别
			* static int ISOLATION_READ_UNCOMMITTED 
			* static int ISOLATION_READ_COMMITTED 
			* static int ISOLATION_REPEATABLE_READ 
			* static int ISOLATION_SERIALIZABLE 
 		
		2. 事务的传播行为常量（不用设置，使用默认值）
			* 先解释什么是事务的传播行为：解决的是业务层之间的方法调用！！
			
			* PROPAGATION_REQUIRED（默认值）	-- A中有事务,使用A中的事务.如果没有，B就会开启一个新的事务,将A包含进来.(保证A,B在同一个事务中)，默认值！！
			* PROPAGATION_SUPPORTS			-- A中有事务,使用A中的事务.如果A中没有事务.那么B也不使用事务.
			* PROPAGATION_MANDATORY			-- A中有事务,使用A中的事务.如果A没有事务.抛出异常.
			
			* PROPAGATION_REQUIRES_NEW（记）-- A中有事务,将A中的事务挂起.B创建一个新的事务.(保证A,B没有在一个事务中)
			* PROPAGATION_NOT_SUPPORTED		-- A中有事务,将A中的事务挂起.
			* PROPAGATION_NEVER 			-- A中有事务,抛出异常.
			
			* PROPAGATION_NESTED（记）		-- 嵌套事务.当A执行之后,就会在这个位置设置一个保存点.如果B没有问题.执行通过.如果B出现异常,运行客户根据需求回滚(选择回滚到保存点或者是最初始状态)
