package org.bluebridge.zookeeper.demo_01_helloworld.service;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * ZooKeeper 常用 API 接口
 *
 * @author lingwh
 * @date 2026/8/31 15:30
 */
public interface IZookeeperService {

    /**
     * 创建节点
     *
     * @param path 节点路径
     * @param data 节点数据
     * @param mode 节点类型（持久/临时/顺序）
     * @return 创建后的实际节点路径
     */
    String createNode(String path, String data, CreateMode mode) throws Exception;

    /**
     * 获取节点数据
     *
     * @param path 节点路径
     * @return 节点数据字符串
     */
    String getData(String path) throws Exception;

    /**
     * 设置节点数据
     *
     * @param path 节点路径
     * @param data 新数据
     * @return 节点状态信息
     */
    Stat setData(String path, String data) throws Exception;

    /**
     * 删除节点
     *
     * @param path 节点路径
     */
    void deleteNode(String path) throws Exception;

    /**
     * 判断节点是否存在
     *
     * @param path 节点路径
     * @return 是否存在
     */
    boolean exists(String path) throws Exception;

    /**
     * 获取子节点列表
     *
     * @param path 节点路径
     * @return 子节点名称列表
     */
    List<String> getChildren(String path) throws Exception;

    /**
     * 注册节点数据变化监听（一次性，触发后需重新注册）
     *
     * @param path    节点路径
     * @param watcher 监听器
     */
    void watchNodeData(String path, Watcher watcher) throws Exception;

    /**
     * 注册子节点变化监听（一次性，触发后需重新注册）
     *
     * @param path    节点路径
     * @param watcher 监听器
     */
    void watchChildren(String path, Watcher watcher) throws Exception;
}
