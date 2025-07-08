package org.bluebridge.anno.dao;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;

/**
 * @author lingwh
 * @desc
 * @date   2019/3/21 14:30
 */
@Repository("personDao")
@EnableAspectJAutoProxy(exposeProxy = true)
public class PersonDao {
    public String save() {
        /**
         * 模拟发生了异常，后置通知依然执行，返回值通知不执行
         */
        //System.out.println(1/0);
        System.out.println("保存操作.....");
        return "";
    }
}
