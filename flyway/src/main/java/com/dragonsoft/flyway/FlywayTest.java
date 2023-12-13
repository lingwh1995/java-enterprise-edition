package com.dragonsoft.flyway;

import org.flywaydb.core.Flyway;
import org.junit.Test;

/**
 * @author ronin
 * @version V1.0
 * @desc
 * @since 2019/7/15 13:21
 */
public class FlywayTest {

    @Test
    public void fun(){
        String url = "jdbc:oracle:thin:@20.20.30.112:1521/orcl";
        String user = "flyway";
        String password = "flyway";

        Flyway flyway = new Flyway();
        //设置加载数据库的相关配置信息
        flyway.setDataSource(url,user,password);
        //设置存放flyway metadata数据的表名，默认"schema_version"，可不写
        //flyway.setTable("SCHMA_VERSION");
        //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径，默认"db/migration"，可不写
        flyway.setLocations("db/migration");
        //设置sql脚本文件的编码，默认"UTF-8"，可不写
        flyway.setEncoding("UTF-8");
        //迁移
        //flyway.migrate();
        //清除
        //flyway.clean();
        //打印信息
        //flyway.info();
        //校验
        //flyway.validate();
        //flyway.repair();
        flyway.migrate();
    }
}
