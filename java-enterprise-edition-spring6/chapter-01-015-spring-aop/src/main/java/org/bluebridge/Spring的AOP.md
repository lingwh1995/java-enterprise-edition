# 1.AOP
## 1.1.IoC与AOP
    IoC能使软件组件松耦合，AOP能捕捉系统中常用的功能，并把这些功能转换为组件
## 1.2.AOP介绍
    AOP(Aspect Oriented Programing)面向切面编程，Spring的AOP底层是使用JDK动态代理和CGLIB动态代理实现的,Spring aop是处理
    交叉业务的最佳解决方案
## 1.3.AOP应用场景
    日志、事务管理、安全
## 1.4.AOP的优缺点好处
    缺点
        1.当不使用AOP开发时，非核心并且的通用的代码没有实现复用，而且修改时需要修改多处
        2.开发人员要兼顾核心业务代码和通用业务代码的开发，无法更专注的开发核心业务
    优点
        1.实现了通用的代码复用
        2.代码维护性增强了
        3.使开发者更专注业务逻辑
## 1.5.AOP开发七大术语
    1.连接点(JoinPoint)
        在程序的整个执行流程中，可以织入切面的位置，方法执行前后、异常抛出后等位置
    2.切点(PointCut)
        在程序执行流程中，真正织入切面的方法，一个切点对应多个连接点
    3.通知(Advice)
        通知又叫增强，就是具体要织入的代码
        通知包括
            前置通知
            后置通知
            环绕通知
            异常通知
            最终通知
    4.切面(Aspect)
        切点+通知就是切面
    5.织入(Weaving)
        把通知应用到目标对象上的过程
    6.代理对象(Proxy)
        一个目标对象被织入通知后产生的新对象
    7.目标对象(Target)
        被织入通知的对象
    8.伪代码体现
        public class UserService {
            //切点(Pointcut)
            public void do1() {
                System.out.println("do1...");
            }
            //切点(Pointcut)
            public void do2() {
                System.out.println("do2...");
            }
            //切点(Pointcut)
            public void do3() {
                System.out.println("do3...");
            }
            public void do4() {
                System.out.println("do4...");
            }
            //切点(Pointcut)
            public void do5() {
                System.out.println("do5...");
            }
        
            //核心业务方法
            public void service() {
                try {
                    //连接点(JoinPoint)
                    do1();//切点(Pointcut)
                    //连接点(JoinPoint)
                    do2();//切点(Pointcut)
                    //连接点(JoinPoint)
                    do3();//切点(Pointcut)
                    //连接点(JoinPoint)
                    do5();//切点(Pointcut)
                    //连接点(JoinPoint)
                }catch(Exception e) {
                    //连接点(JoinPoint)
                }finally {
                    //连接点(JoinPoint)
                }
            }
        }
    1.连接点(JoinPoint)描述的是位置
    2.切点(Pointcut)描述的是方法，真正织入切面的那个方法就叫做切点，也可以说放在连接点这个位置上的方法就叫做切点
    3.通知(Advice)又叫增强，描述的具体增强的代码
    例如	具体的日志、事务管理、安全代码、统计时长的代码
    4.切面 = 切点 + 通知
# 2.AOP切入点的表达式
	在配置切入点的时候，需要定义表达式，重点的格式如下：execution(public * *(..))，具体展开如下：
		* 切入点表达式的格式如下：
			* execution([访问权限修饰符] 返回值类型 [包名.类名.]方法名(形式参数列表) [异常])
              execution([访问权限修饰符] 返回值类型 [全限定类名]方法名(形式参数列表) [异常])
		
		* 修饰符可以省略不写，不是必须要出现的。
		* 返回值类型是不能省略不写的，根据你的方法来编写返回值。可以使用 * 代替。
		* 包名例如：com.itheima.demo3.BookDaoImpl
			* 首先com是不能省略不写的，但是可以使用 * 代替
			* 中间的包名可以使用 * 号代替
			* 如果想省略中间的包名可以使用 .. 
		
		* 类名也可以使用 * 号代替，也有类似的写法：*DaoImpl
		* 方法也可以使用 * 号代替
		* 参数如果是一个参数可以使用 * 号代替，如果想代表任意参数使用 ..

# 3.通知类型	
##    3.1. 前置通知
		* 在目标类的方法执行之前执行。
		* 配置文件信息：<aop:after method="before" pointcut-ref="myPointcut3"/>
		* 应用：可以对方法的参数来做校验
	
##	3.2. 最终通知
		* 在目标类的方法执行之后执行，如果程序出现了异常，最终通知也会执行。
		* 在配置文件中编写具体的配置：<aop:after method="after" pointcut-ref="myPointcut3"/>
		* 应用：例如像释放资源
	
##  3.3. 后置通知
		* 方法正常执行后的通知。		
		* 在配置文件中编写具体的配置：<aop:after-returning method="afterReturning" pointcut-ref="myPointcut2"/>
		* 应用：可以修改方法的返回值
	
##	3.4. 异常抛出通知
		* 在抛出异常后通知
		* 在配置文件中编写具体的配置：<aop:after-throwing method="afterThorwing" pointcut-ref="myPointcut3"/>	
		* 应用：包装异常的信息
	
##	3.5. 环绕通知
		* 方法的执行前后执行。
		* 在配置文件中编写具体的配置：<aop:around method="around" pointcut-ref="myPointcut2"/>
		* 要注意：目标的方法默认不执行，需要使用ProceedingJoinPoint对来让目标对象的方法执行。