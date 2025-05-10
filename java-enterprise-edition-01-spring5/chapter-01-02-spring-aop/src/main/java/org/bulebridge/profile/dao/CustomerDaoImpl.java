package org.bulebridge.profile.dao;

public class CustomerDaoImpl implements CustomerDao{
    public String save() {
        /**
         * 模拟发生了异常，后置通知依然执行，返回值通知不执行
         */
        //System.out.println(1 / 0);
        System.out.println("保存操作.....");
        return "我是save方法返回值...";
    }

    /**
     * 测试环绕通知
     */
    public String aroundMethod() {
        //System.out.println(1/0);
        System.out.println("测试环绕通知的方法.....");
        return "我是aroundMethod方法返回值...";
    }

    /**
     * 测试接入点
     */
    public void testPointCut() {
        System.out.println("测试接入点...");
    }
}
