package org.bulebridge.ioc.autoinject.dbutils;

import org.springframework.stereotype.Component;

/**
 * @author ronin
 */
@Component(value="dbutils")
public class DBUtils {

    public void save(){
        System.out.println("执行保存到数据库的方法......DBUtils......");
    }
}
