package org.bluebridge.zookeeper.demo_01_helloworld.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.bluebridge.zookeeper.demo_01_helloworld.service.IZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ZooKeeper REST 演示接口
 *
 * @author lingwh
 * @date 2026/8/31 15:30
 */
@Slf4j
@RestController
@RequestMapping("/zookeeper")
public class ZookeeperController {

    @Autowired
    private IZookeeperService zookeeperService;

    /**
     * 创建节点
     *
     * @param path 节点路径
     * @param data 节点数据
     * @param mode 节点类型: PERSISTENT / PERSISTENT_SEQUENTIAL / EPHEMERAL / EPHEMERAL_SEQUENTIAL
     */
    @PostMapping("/create")
    public String create(@RequestParam String path,
                         @RequestParam(defaultValue = "") String data,
                         @RequestParam(defaultValue = "PERSISTENT") String mode) throws Exception {
        CreateMode createMode = CreateMode.valueOf(mode.toUpperCase());
        return zookeeperService.createNode(path, data, createMode);
    }

    /**
     * 获取节点数据
     */
    @GetMapping("/get")
    public String get(@RequestParam String path) throws Exception {
        return zookeeperService.getData(path);
    }

    /**
     * 设置节点数据
     */
    @PostMapping("/set")
    public Stat set(@RequestParam String path, @RequestParam String data) throws Exception {
        return zookeeperService.setData(path, data);
    }

    /**
     * 删除节点
     */
    @PostMapping("/delete")
    public String delete(@RequestParam String path) throws Exception {
        zookeeperService.deleteNode(path);
        return "删除成功: " + path;
    }

    /**
     * 判断节点是否存在
     */
    @GetMapping("/exists")
    public boolean exists(@RequestParam String path) throws Exception {
        return zookeeperService.exists(path);
    }

    /**
     * 获取子节点列表
     */
    @GetMapping("/children")
    public List<String> children(@RequestParam String path) throws Exception {
        return zookeeperService.getChildren(path);
    }

    /**
     * 注册节点数据变化监听（一次性，触发后需重新注册）
     */
    @PostMapping("/watch/data")
    public String watchData(@RequestParam String path) throws Exception {
        zookeeperService.watchNodeData(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                log.info("执行链路 - [数据监听] 收到事件: type={}, path={}, state={}",
                        event.getType(), event.getPath(), event.getState());
            }
        });
        return "已注册数据监听: " + path + "（修改该节点数据可触发，监听为一次性）";
    }

    /**
     * 注册子节点变化监听（一次性，触发后需重新注册）
     */
    @PostMapping("/watch/children")
    public String watchChildren(@RequestParam String path) throws Exception {
        zookeeperService.watchChildren(path, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                log.info("执行链路 - [子节点监听] 收到事件: type={}, path={}, state={}",
                        event.getType(), event.getPath(), event.getState());
            }
        });
        return "已注册子节点监听: " + path + "（增删子节点可触发，监听为一次性）";
    }
}
