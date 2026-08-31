package org.bluebridge.zookeeper.demo_01_curator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Curator 常用 API 实现
 *
 * @author lingwh
 * @date 2026/8/31 16:00
 */
@Slf4j
@Service
public class CuratorServiceImpl implements ICuratorService {

    @Autowired
    private CuratorFramework client;

    /** 缓存已注册的 CuratorCache，避免重复创建并支持关闭 */
    private final Map<String, CuratorCache> caches = new ConcurrentHashMap<>();

    @Override
    public String createNode(String path, String data, CreateMode mode) throws Exception {
        String createdPath = client.create()
                .creatingParentsIfNeeded()
                .withMode(mode)
                .forPath(path, data.getBytes(StandardCharsets.UTF_8));
        log.info("执行链路 - 创建节点: path={}, data={}, mode={}, result={}", path, data, mode, createdPath);
        return createdPath;
    }

    @Override
    public String getData(String path) throws Exception {
        byte[] data = client.getData().forPath(path);
        String result = new String(data, StandardCharsets.UTF_8);
        log.info("执行链路 - 获取节点数据: path={}, data={}", path, result);
        return result;
    }

    @Override
    public Stat setData(String path, String data) throws Exception {
        Stat stat = client.setData().forPath(path, data.getBytes(StandardCharsets.UTF_8));
        log.info("执行链路 - 设置节点数据: path={}, data={}, version={}", path, data, stat.getVersion());
        return stat;
    }

    @Override
    public void deleteNode(String path) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(path);
        log.info("执行链路 - 删除节点: path={}", path);
    }

    @Override
    public boolean exists(String path) throws Exception {
        boolean exists = client.checkExists().forPath(path) != null;
        log.info("执行链路 - 判断节点是否存在: path={}, exists={}", path, exists);
        return exists;
    }

    @Override
    public List<String> getChildren(String path) throws Exception {
        List<String> children = client.getChildren().forPath(path);
        log.info("执行链路 - 获取子节点: path={}, children={}", path, children);
        return children;
    }

    @Override
    public void watch(String path) throws Exception {
        // 已注册则先关闭旧的
        CuratorCache old = caches.remove(path);
        if (old != null) {
            old.close();
        }
        CuratorCache cache = CuratorCache.builder(client, path).build();
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(childData -> log.info("执行链路 - [CuratorCache] CREATE: path={}, data={}",
                        childData.getPath(), toStr(childData.getData())))
                .forChanges((oldData, newData) -> log.info("执行链路 - [CuratorCache] CHANGE: path={}, old={}, new={}",
                        newData.getPath(), toStr(oldData.getData()), toStr(newData.getData())))
                .forDeletes(childData -> log.info("执行链路 - [CuratorCache] DELETE: path={}", childData.getPath()))
                .forInitialized(() -> log.info("执行链路 - [CuratorCache] 初始化完成: path={}", path))
                .build();
        cache.listenable().addListener(listener);
        cache.start();
        caches.put(path, cache);
        log.info("执行链路 - 注册 CuratorCache 持续监听: path={}", path);
    }

    @Override
    public void unwatch(String path) {
        CuratorCache cache = caches.remove(path);
        if (cache != null) {
            cache.close();
            log.info("执行链路 - 关闭 CuratorCache 监听: path={}", path);
        }
    }

    @Override
    public String doWithLock(String lockPath, String taskName) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        log.info("执行链路 - 尝试获取分布式锁: lockPath={}, task={}", lockPath, taskName);
        if (!lock.acquire(10, TimeUnit.SECONDS)) {
            log.warn("执行链路 - 获取锁超时: lockPath={}", lockPath);
            return "获取锁失败(超时): " + lockPath;
        }
        try {
            log.info("执行链路 - 获取锁成功, 执行任务: task={}", taskName);
            // 模拟业务耗时，便于并发请求时观察锁排队效果
            Thread.sleep(2000);
            return "任务完成: " + taskName;
        } finally {
            lock.release();
            log.info("执行链路 - 释放分布式锁: lockPath={}", lockPath);
        }
    }

    /**
     * 安全地把字节数组转成字符串，避免 null
     */
    private static String toStr(byte[] bytes) {
        return bytes == null ? "null" : new String(bytes, StandardCharsets.UTF_8);
    }
}
