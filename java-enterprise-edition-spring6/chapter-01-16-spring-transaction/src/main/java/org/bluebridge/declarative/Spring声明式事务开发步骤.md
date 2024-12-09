**Spring框架的事务管理之声明式事务管理，即通过配置文件来完成事务管理(AOP思想)**

	1. 声明式事务管理又分成两种方式
		* 基于AspectJ的XML方式（重点掌握）
		* 基于AspectJ的注解方式（重点掌握）
	
----------

**Spring框架的事务管理之基于AspectJ的XML方式（重点掌握）**

	1. 步骤一:恢复转账开发环境
	
	2. 步骤二:引入AOP的开发包
	
	3. 步骤三:配置事务管理器
		<!-- 配置事务管理器 -->
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			<property name="dataSource" ref="dataSource"/>
		</bean>
	
	4. 步骤四:配置事务增强
		<!-- 配置事务增强 -->
		<tx:advice id="txAdvice" transaction-manager="transactionManager">
			<tx:attributes>
				<!--
					name		：绑定事务的方法名，可以使用通配符，可以配置多个。
					propagation	：传播行为
					isolation	：隔离级别
					read-only	：是否只读
					timeout		：超时信息
					rollback-for：发生哪些异常回滚.
					no-rollback-for：发生哪些异常不回滚.
				 -->
				<!-- 哪些方法加事务 -->
				<tx:method name="pay" propagation="REQUIRED"/>
			</tx:attributes>
		</tx:advice>
	
	5. 步骤五:配置AOP的切面
		<!-- 配置AOP切面产生代理 -->
		<aop:config>
	    	<aop:advisor advice-ref="myAdvice" pointcut="execution(* com.itheima.demo2.AccountServiceImpl.pay(..))"/>
	    </aop:config>
		
		* 注意：如果是自己编写的切面，使用<aop:aspect>标签，如果是系统制作的，使用<aop:advisor>标签。
	
	6. 步骤六:编写测试类
		@RunWith(SpringJUnit4ClassRunner.class)
		@ContextConfiguration("classpath:applicationContext2.xml")
		public class Demo2 {
			
			@Resource(name="accountService")
			private AccountService accountService;
			
			@Test
			public void run1(){
				accountService.pay("冠希", "美美", 1000);
			}
		}
	
----------

**Spring框架的事务管理之基于AspectJ的注解方式（重点掌握，最简单的方式）**

	1. 步骤一:恢复转账的开发环境
	
	2. 步骤二:配置事务管理器
		<!-- 配置事务管理器  -->
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			<property name="dataSource" ref="dataSource"/>
		</bean>
	
	3. 步骤三:开启注解事务
		<!-- 开启注解事务 -->
		<tx:annotation-driven transaction-manager="transactionManager"/>
	
	4. 步骤四:在业务层上添加一个注解:@Transactional
	
	5. 编写测试类
		@RunWith(SpringJUnit4ClassRunner.class)
		@ContextConfiguration("classpath:applicationContext3.xml")
		public class Demo3 {
			
			@Resource(name="accountService")
			private AccountService accountService;
			
			@Test
			public void run1(){
				accountService.pay("冠希", "美美", 1000);
			}
		}
	
----------