package org.bluebridge.zookeeper.demo_01_helloworld;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.bluebridge.zookeeper.demo_01_helloworld.service.IZookeeperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ZooKeeper 原生客户端常用 API 测试
 *
 * 直接调用 Service 层，比走 Controller 测试更方便。运行前请确保 application.yml
 * 中配置的 ZooKeeper 地址可连通。
 *
 * @author lingwh
 * @date 2026/8/31 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperTest {

    @Autowired
    private IZookeeperService zookeeperService;

    private static final String BASE = "/zk_test";

    /**
     * 测试创建节点（持久 / 临时 / 持久顺序 三种类型）
     */
    @Test
    public void testCreateNode() throws Exception {
        System.out.println("持久节点: " + zookeeperService.createNode(BASE + "/p1", "hello", CreateMode.PERSISTENT));
        System.out.println("临时节点: " + zookeeperService.createNode(BASE + "/e1", "temp", CreateMode.EPHEMERAL));
        System.out.println("持久顺序节点: " + zookeeperService.createNode(BASE + "/seq-", "seq", CreateMode.PERSISTENT_SEQUENTIAL));
    }

    /**
     * 测试获取节点数据
     */
    @Test
    public void testGetData() throws Exception {
        System.out.println("节点数据: " + zookeeperService.getData(BASE + "/p1"));
    }

    /**
     * 测试修改节点数据
     */
    @Test
    public void testSetData() throws Exception {
        zookeeperService.setData(BASE + "/p1", "world");
        System.out.println("修改完成, 当前数据: " + zookeeperService.getData(BASE + "/p1"));
    }

    /**
     * 测试判断节点是否存在
     */
    @Test
    public void testExists() throws Exception {
        System.out.println("存在: " + zookeeperService.exists(BASE + "/p1"));
        System.out.println("存在: " + zookeeperService.exists(BASE + "/not_exist"));
    }

    /**
     * 测试获取子节点列表
     */
    @Test
    public void testGetChildren() throws Exception {
        List<String> children = zookeeperService.getChildren(BASE);
        System.out.println("子节点: " + children);
    }

    /**
     * 测试节点数据变化监听（原生 Watcher 为一次性，触发后需重新注册）
     */
    @Test
    public void testWatchNodeData() throws Exception {
        String path = BASE + "/watch_node";
        zookeeperService.createNode(path, "data0", CreateMode.PERSISTENT);
        zookeeperService.watchNodeData(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("收到监听事件: type=" + event.getType() + ", path=" + event.getPath());
            }
        });
        // 修改数据触发监听
        zookeeperService.setData(path, "data1");
        Thread.sleep(1000);
        zookeeperService.deleteNode(path);
    }

    /**
     * 测试子节点变化监听（一次性）
     */
    @Test
    public void testWatchChildren() throws Exception {
        String parent = BASE + "/watch_parent";
        zookeeperService.createNode(parent, "", CreateMode.PERSISTENT);
        zookeeperService.watchChildren(parent, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("收到监听事件: type=" + event.getType() + ", path=" + event.getPath());
            }
        });
        // 新增子节点触发监听
        zookeeperService.createNode(parent + "/c1", "child1", CreateMode.PERSISTENT);
        Thread.sleep(1000);
        zookeeperService.deleteNode(parent);
    }

    /**
     * 测试删除节点
     */
    @Test
    public void testDeleteNode() throws Exception {
        zookeeperService.deleteNode(BASE + "/p1");
        System.out.println("删除完成, 是否存在: " + zookeeperService.exists(BASE + "/p1"));
    }
}
