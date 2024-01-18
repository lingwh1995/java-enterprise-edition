package org.bluebridge;

import org.apache.ibatis.session.SqlSession;
import org.bluebridge.utils.SqlSessionUtil;
import org.junit.Test;

public class SqlSessionUtilTest {
    @Test
    public void testMybatisHelloWorld() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            int count = sqlSession.insert("emp.insertEmployee"); // 这个"insertCar"必须是sql的id
            System.out.println("插入几条数据：" + count);
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        }finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
