//package org.bluebridge;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class IDepartmentResultMapTest {
//    /**
//     * 获取SqlSession
//     * @return
//     * @throws IOException
//     */
//    public SqlSession getSqlSession() throws IOException{
//        /**
//         * 1.加载配置文件
//         */
//        InputStream inputStream = Resources.getResourceAsStream("mysql/mybatis-config.xml");
//        /**
//         * 2.获取SqlSession对象
//         */
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        /**
//         * 3.获取SqlSession对象
//         */
//        //获取可以自动提交的openSession对象,传入true
//        //SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        //获取需要手动提交的openSession对象,传入fasle或者什么都不传
//        SqlSession sqlSession = sqlSessionFactory.openSession(false);
//        //SqlSession sqlSession = sqlSessionFactory.openSession();
//        return sqlSession;
//    }
//
//
//    /**
//     * 根据id查询Department对象和该对象中的List<Employee>
//     * @throws IOException
//     */
//    @Test
//    public void getDeptAndEmployeesByIdUseCascade() throws IOException {
//        SqlSession sqlSession = this.getSqlSession();
//        try {
//            /**
//             * 4.获取接口实现类对象
//             */
//            IDept ideptImpl = sqlSession.getMapper(IDept.class);
//            /**
//             * 5.根据id获取对象
//             */
//            Department department = ideptImpl.getDeptAndEmployeesByIdUseCascade("1");
//            System.out.println("department:"+department.getEmps());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            /**
//             * 6.关闭sqlSession
//             */
//            sqlSession.close();
//        }
//    }
//
//
//    /**
//     * 根据id查询Department对象和该对象中的List<Employee>
//     * 分步骤查询
//     * @throws IOException
//     */
//    @Test
//    public void getDeptAndEmployeesStepById() throws IOException {
//        SqlSession sqlSession = this.getSqlSession();
//        try {
//            /**
//             * 4.获取接口实现类对象
//             */
//            IDept ideptImpl = sqlSession.getMapper(IDept.class);
//            /**
//             * 5.根据id获取对象
//             */
//            Department department = ideptImpl.getDeptAndEmployeesStepById("1");
//            //只发一条sql
//            System.out.println("departmentName:"+department.getDname());
//            //发两条sql
//            System.out.println("department:"+department.getEmps());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            /**
//             * 6.关闭sqlSession
//             */
//            sqlSession.close();
//        }
//    }
//}
