package org.bluebridge;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 测试解析 eclipse打包生成的bar文件
 */
public class ProcessByEclipseBarTest {

    ProcessEngineConfiguration processEngineConfiguration = null;
    @Before
    public void before() {
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
     * 部署流程
     */
    @Test
    public void testDeploy() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
        //2.获取RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("holiday-process-eclipse.bar");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        //3.获取DeploymentBuilder对象，zip和bar使用addZipInputStream()进行关联
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)//关联要部署的流程文件
                .name("请假流程-Eclipse版")//设置流程名称
                .deploy();//部署流程
        System.out.println("deploy.getId():" + deploy.getId());
        System.out.println("deploy.getName():" + deploy.getName());
    }

    /**
     * 查询部署的流程
     */
    @Test
    public void testDeployQuery() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //使用key查询已经定义的流程，这里的key就是流程id
        /*
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holidayProcessEclipse")
                .singleResult();
        */

        //使用deploymentId查询已经定义的流程
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("62501")
                .singleResult();
        System.out.println("processDefinition.getDeploymentId():" + processDefinition.getDeploymentId());
        System.out.println("processDefinition.getName():" + processDefinition.getName());
        System.out.println("processDefinition.getDescription():" + processDefinition.getDescription());
        System.out.println("processDefinition.getId():" + processDefinition.getId());
    }


    /**
     * 删除已经部署的流程
     */
    @Test
    public void testDeployDelete() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除部署的流程，如果流程启动了，就不可以被删除了
            //第一个参数:流程id  第二个参数: 级联删除，如果流程启动了，相关的任务一并会被删除
        repositoryService.deleteDeployment("62501",true);
    }

    /**
     * 启动流程实例
     */
    @Test
    public void testRunProcess() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //构建流程变量
            //模拟客户端传递过来的数据
        HashMap<String, Object> variables = new HashMap<>();
        //请假人
        variables.put("employee","张三");
        //请假时长
        variables.put("LeaveDuration",3);
        //请假原因
        variables.put("LeaveReason","出去旅游");

        //启动流程实例
        ProcessInstance holidayProcessByEclipse = runtimeService.startProcessInstanceByKey("holidayProcessEclipse", variables);
        System.out.println("holidayProcessByEclipse.getProcessDefinitionId():" + holidayProcessByEclipse.getProcessDefinitionId());
        System.out.println("holidayProcessByEclipse.getActivityId():" + holidayProcessByEclipse.getActivityId());
        System.out.println("holidayProcessByEclipse.getId():" + holidayProcessByEclipse.getId());
    }

    /**
     * 测试任务查询
     */
    @Test
    public void testQueryTask() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("holidayProcessEclipse")//指定查询的流程编号
                .taskAssignee("zhangsan")
                .list();

        taskList.forEach(task -> {
            System.out.println("task.getProcessDefinitionId():" + task.getProcessDefinitionId());
            System.out.println("task.getName():" + task.getName());
            System.out.println("task.getAssignee():" + task.getAssignee());
            System.out.println("task.getDescription():" + task.getDescription());
            System.out.println("task.getId():" + task.getId());
        });
    }

    /**
     * 完成当前任务
     */
    @Test
    public void testCompleteTask() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        System.out.println("zhangsan开始处理任务=============>");
        //zhangsan处理任务
        Task zhagnsanTask = taskService.createTaskQuery()
                .processDefinitionKey("holidayProcessEclipse")
                .taskAssignee("zhangsan")
                .singleResult();
        taskService.complete(zhagnsanTask.getId());
        System.out.println("zhangsan完成处理任务=============>");

        System.out.println("lisi开始处理任务=============>");
        //lisi完成任务
        Task lisiTask = taskService.createTaskQuery()
                .processDefinitionKey("holidayProcessEclipse")
                .taskAssignee("lisi")
                .singleResult();
        //完成任务
        taskService.complete(lisiTask.getId());
        System.out.println("lisi完成处理任务=============>");
    }

    /**
     * 获取流程任务历史数据
     */
    @Test
    public void testHistoryQuery() {
        //1.获取流程引擎对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> holidayRequestList = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId("holidayProcessEclipse:1:132504")
                .finished()//查询的历史记录状态是已经完成的
                .orderByHistoricActivityInstanceEndTime().asc()//指定排序的字段和顺序
                .list();
        holidayRequestList.forEach( holidayRequest -> {
            System.out.println("holidayRequest.getActivityId():" + holidayRequest.getActivityId());
            System.out.println("holidayRequest.getAssignee():" + holidayRequest.getAssignee());
            System.out.println("holidayRequest.getActivityName():" + holidayRequest.getActivityName());
            System.out.println("holidayRequest.getDurationInMillis():" + holidayRequest.getDurationInMillis());
        });
    }
}
