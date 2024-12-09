package org.bluebridge;

import org.bluebridge.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisCrudBasicByMapTest {

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
            paramsMap.put("dept_no","20");
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
     * 使用Map传递参数实现查询一个Employee功能
     */
    @Test
    public void testMybatisSelectOne() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Map<String,Object> result = (Map)sqlSession.selectOne("crudByMap.selectOneEmployee", 1);
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

    /**
     * 使用Map传递参数实现更新功能
     */
    @Test
    public void testMybatisUpdateByMap() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionUtil.openSession();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("id","1");
            paramsMap.put("last_name","zs");
            paramsMap.put("email","1458687169@qq.com1");
            paramsMap.put("gender","男1");
            paramsMap.put("dept_no","30");
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
            int count = sqlSession.delete("crudByMap.deleteEmployee",1);
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
}
