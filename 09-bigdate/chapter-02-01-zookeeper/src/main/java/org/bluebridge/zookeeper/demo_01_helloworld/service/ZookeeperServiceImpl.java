package org.bluebridge.zookeeper.demo_01_helloworld.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ZooKeeper 常用 API 实现
 *
 * @author lingwh
 * @date 2026/8/31 15:30
 */
@Slf4j
@Service
public class ZookeeperServiceImpl implements IZookeeperService {

    @Autowired
    private ZooKeeper zooKeeper;

    @Override
    public String createNode(String path, String data, CreateMode mode) throws Exception {
        String createdPath = zooKeeper.create(path, data.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
        log.info("执行链路 - 创建节点: path={}, data={}, mode={}, result={}", path, data, mode, createdPath);
        return createdPath;
    }

    @Override
    public String getData(String path) throws Exception {
        byte[] data = zooKeeper.getData(path, false, null);
        String result = new String(data, StandardCharsets.UTF_8);
        log.info("执行链路 - 获取节点数据: path={}, data={}", path, result);
        return result;
    }

    @Override
    public Stat setData(String path, String data) throws Exception {
        Stat stat = zooKeeper.setData(path, data.getBytes(StandardCharsets.UTF_8), -1);
        log.info("执行链路 - 设置节点数据: path={}, data={}, version={}", path, data, stat.getVersion());
        return stat;
    }

    @Override
    public void deleteNode(String path) throws Exception {
        zooKeeper.delete(path, -1);
        log.info("执行链路 - 删除节点: path={}", path);
    }

    @Override
    public boolean exists(String path) throws Exception {
        boolean exists = zooKeeper.exists(path, false) != null;
        log.info("执行链路 - 判断节点是否存在: path={}, exists={}", path, exists);
        return exists;
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        List<String> children = zooKeeper.getChildren(path, false);
        log.info("执行链路 - 获取子节点: path={}, children={}", path, children);
        return children;
    }

    @Override
    public void watchNodeData(String path, Watcher watcher) throws Exception {
        // exists + getData 都注册 watcher，节点创建/删除/数据变化都能感知
        zooKeeper.exists(path, watcher);
        zooKeeper.getData(path, watcher, null);
        log.info("执行链路 - 注册节点数据监听: path={}", path);
    }

    @Override
    public void watchChildren(String path, Watcher watcher) throws Exception {
        zooKeeper.getChildren(path, watcher);
        log.info("执行链路 - 注册子节点监听: path={}", path);
    }
}
