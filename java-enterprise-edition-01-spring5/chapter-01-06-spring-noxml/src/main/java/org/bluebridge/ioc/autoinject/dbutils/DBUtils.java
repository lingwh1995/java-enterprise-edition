package org.bluebridge.ioc.autoinject.dbutils;

import org.springframework.stereotype.Component;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/12 12:21
 */
@Component(value="dbutils")
public class DBUtils {

    public void save(){
        System.out.println("执行保存到数据库的方法......DBUtils......");
    }
}
