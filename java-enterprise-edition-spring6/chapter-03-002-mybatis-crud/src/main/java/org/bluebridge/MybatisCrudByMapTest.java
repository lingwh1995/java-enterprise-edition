package org.bluebridge;

import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisCrudByMapTest {
    private static final String ENV = "mysql";

    /**
     * 使用Map传递参数实现新增功能
     */
    @Test
    public void testMybatisInsertByMap() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("last_name","zhangsan");
            paramsMap.put("email","1458687169@qq.com");
            paramsMap.put("gender","男");
            int count = sqlSession.insert("crudByMap.insertEmployee",paramsMap);
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

    /**
     * 使用Map传递参数实现更新功能
     */
    @Test
    public void testMybatisUpdateByMap() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("id","14");
            paramsMap.put("last_name","zs");
            paramsMap.put("email","1458687169@qq.com1");
            paramsMap.put("gender","男1");
            int count = sqlSession.update("crudByMap.updateEmployee",paramsMap);
            System.out.println("更新几条数据：" + count);
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

    /**
     * 使用Map传递参数实现删除功能
     */
    @Test
    public void testMybatisDeleteByMap() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("id","14");
            int count = sqlSession.delete("crudByMap.deleteEmployee",paramsMap);
            System.out.println("删除几条数据：" + count);
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

    /**
     * 使用Map传递参数实现查询一个Employee功能
     */
    @Test
    public void testMybatisSelectOne() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("id","14");
            Map<String,Object> result = (Map)sqlSession.selectOne("crudByMap.selectOneEmployee", paramsMap);

            //除了使用Map作为参数,也可以直接传递一个参数进去，xml中使用#{id}也是可以获取到的
            //Map<String,Object> result = (Map)sqlSession.selectOne("crudByMap.selectOneEmployee", 14);
            System.out.println("查询到的数据：" + result);
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

    /**
     * 查询多个Employee功能
     */
    @Test
    public void testMybatisSelectList() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            List<Map<String,Object>> list = sqlSession.selectList("crudByMap.selectEmployees");
            System.out.println("查询到的数据：" + list);
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
