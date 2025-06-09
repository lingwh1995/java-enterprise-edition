package org.bluebridge;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Test;

public class ProcessEngineTest {

    ProcessEngineConfiguration processEngineConfiguration = null;

    @Test
    public void processEngine01() {
        processEngineConfiguration = new StandaloneProcessEngineConfiguration();
        //配置数据库连接信息
        processEngineConfiguration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("Mysql123456_");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://192.168.0.5:3306/flowable?useUnicode=true&rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai");

        //如果数据库中的表结构不存在就新建
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }

    /**
     * 加载默认的配置文件
     */
    @Test
    public void processEngin02(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("defaultProcessEngine = " + defaultProcessEngine);
    }

    /**
     * 加载自定义名称的配置文件
     */
    @Test
    public void processEngin03() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("flowable.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println("processEngine = " + processEngine);
    }

}
