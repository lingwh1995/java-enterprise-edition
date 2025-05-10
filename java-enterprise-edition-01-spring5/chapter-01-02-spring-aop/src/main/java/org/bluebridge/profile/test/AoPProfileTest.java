package org.bluebridge.profile.test;

import org.bluebridge.profile.dao.CustomerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-aop-profile.xml")
public class AoPProfileTest {

    @Resource(name="customerDao")
    private CustomerDao customerDao;

    @Test
    public void fun1(){
        customerDao.save();
    }

    /**
     * 测试环绕通知发生异常仍然可以执行
     * @throws Exception
     */
    @Test
    public void fun2()  throws Exception{
        customerDao.aroundMethod();
    }

    /**
     * 测试PonitCut
     * @throws Exception
     */
    @Test
    public void fun3()  throws Exception{
        customerDao.testPointCut();
    }
}
