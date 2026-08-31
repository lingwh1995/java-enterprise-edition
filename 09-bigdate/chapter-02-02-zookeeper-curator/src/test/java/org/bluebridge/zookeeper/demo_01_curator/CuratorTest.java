package org.bluebridge.zookeeper.demo_01_curator;

import org.apache.zookeeper.CreateMode;
import org.bluebridge.zookeeper.demo_01_curator.service.ICuratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Curator 常用 API 测试
 *
 * 直接调用 Service 层，比走 Controller 测试更方便。运行前请确保 application.yml
 * 中配置的 ZooKeeper 地址可连通。
 *
 * @author lingwh
 * @date 2026/8/31 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CuratorTest {

    @Autowired
    private ICuratorService curatorService;

    private static final String BASE = "/curator_test";

    /**
     * 测试创建节点（creatingParentsIfNeeded 会自动创建父目录）
     */
    @Test
    public void testCreateNode() throws Exception {
        System.out.println("持久节点: " + curatorService.createNode(BASE + "/p1", "hello", CreateMode.PERSISTENT));
        System.out.println("临时节点: " + curatorService.createNode(BASE + "/e1", "temp", CreateMode.EPHEMERAL));
    }

    /**
     * 测试获取节点数据
     */
    @Test
    public void testGetData() throws Exception {
        System.out.println("节点数据: " + curatorService.getData(BASE + "/p1"));
    }

    /**
     * 测试修改节点数据
     */
    @Test
    public void testSetData() throws Exception {
        curatorService.setData(BASE + "/p1", "world");
        System.out.println("修改完成, 当前数据: " + curatorService.getData(BASE + "/p1"));
    }

    /**
     * 测试判断节点是否存在
     */
    @Test
    public void testExists() throws Exception {
        System.out.println("存在: " + curatorService.exists(BASE + "/p1"));
        System.out.println("存在: " + curatorService.exists(BASE + "/not_exist"));
    }

    /**
     * 测试获取子节点列表
     */
    @Test
    public void testGetChildren() throws Exception {
        List<String> children = curatorService.getChildren(BASE);
        System.out.println("子节点: " + children);
    }

    /**
     * 测试 CuratorCache 持续监听（自动重注册，无需手动续期）
     */
    @Test
    public void testWatch() throws Exception {
        String path = BASE + "/watch_node";
        curatorService.createNode(path, "data0", CreateMode.PERSISTENT);
        curatorService.watch(path);
        // 修改数据触发监听
        curatorService.setData(path, "data1");
        Thread.sleep(1000);
        curatorService.unwatch(path);
        curatorService.deleteNode(path);
    }

    /**
     * 测试分布式锁
     */
    @Test
    public void testDoWithLock() throws Exception {
        String result = curatorService.doWithLock(BASE + "/lock", "test_task");
        System.out.println("分布式锁任务结果: " + result);
    }

    /**
     * 测试删除节点
     */
    @Test
    public void testDeleteNode() throws Exception {
        curatorService.deleteNode(BASE + "/p1");
        System.out.println("删除完成, 是否存在: " + curatorService.exists(BASE + "/p1"));
    }
}
