package org.bluebridge.zookeeper.demo_01_curator.service;

import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Curator 常用 API 接口
 *
 * @author lingwh
 * @date 2026/8/31 16:00
 */
public interface ICuratorService {

    /**
     * 创建节点（自动创建父目录）
     *
     * @param path 节点路径
     * @param data 节点数据
     * @param mode 节点类型
     * @return 创建后的实际节点路径
     */
    String createNode(String path, String data, CreateMode mode) throws Exception;

    /**
     * 获取节点数据
     */
    String getData(String path) throws Exception;

    /**
     * 设置节点数据
     */
    Stat setData(String path, String data) throws Exception;

    /**
     * 删除节点（连同子节点）
     */
    void deleteNode(String path) throws Exception;

    /**
     * 判断节点是否存在
     */
    boolean exists(String path) throws Exception;

    /**
     * 获取子节点列表
     */
    List<String> getChildren(String path) throws Exception;

    /**
     * 注册 CuratorCache 持续监听（自动重注册，无需手动续期）
     *
     * @param path 监听路径
     */
    void watch(String path) throws Exception;

    /**
     * 关闭指定路径的监听
     */
    void unwatch(String path);

    /**
     * 使用分布式锁执行任务
     *
     * @param lockPath 锁节点路径
     * @param taskName 任务名称
     * @return 执行结果
     */
    String doWithLock(String lockPath, String taskName) throws Exception;
}
