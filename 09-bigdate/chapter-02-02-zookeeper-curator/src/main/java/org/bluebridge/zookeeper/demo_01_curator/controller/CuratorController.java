package org.bluebridge.zookeeper.demo_01_curator.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.bluebridge.zookeeper.demo_01_curator.service.ICuratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Curator REST 演示接口
 *
 * @author lingwh
 * @date 2026/8/31 16:00
 */
@Slf4j
@RestController
@RequestMapping("/curator")
public class CuratorController {

    @Autowired
    private ICuratorService curatorService;

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
        return curatorService.createNode(path, data, createMode);
    }

    /**
     * 获取节点数据
     */
    @GetMapping("/get")
    public String get(@RequestParam String path) throws Exception {
        return curatorService.getData(path);
    }

    /**
     * 设置节点数据
     */
    @PostMapping("/set")
    public Stat set(@RequestParam String path, @RequestParam String data) throws Exception {
        return curatorService.setData(path, data);
    }

    /**
     * 删除节点
     */
    @PostMapping("/delete")
    public String delete(@RequestParam String path) throws Exception {
        curatorService.deleteNode(path);
        return "删除成功: " + path;
    }

    /**
     * 判断节点是否存在
     */
    @GetMapping("/exists")
    public boolean exists(@RequestParam String path) throws Exception {
        return curatorService.exists(path);
    }

    /**
     * 获取子节点列表
     */
    @GetMapping("/children")
    public List<String> children(@RequestParam String path) throws Exception {
        return curatorService.getChildren(path);
    }

    /**
     * 注册持续监听（修改该路径下节点可触发日志，自动重注册）
     */
    @PostMapping("/watch")
    public String watch(@RequestParam String path) throws Exception {
        curatorService.watch(path);
        return "已注册持续监听: " + path + "（增删改该路径下的节点都会触发日志）";
    }

    /**
     * 关闭持续监听
     */
    @PostMapping("/unwatch")
    public String unwatch(@RequestParam String path) {
        curatorService.unwatch(path);
        return "已关闭监听: " + path;
    }

    /**
     * 分布式锁演示：模拟获取锁 -> 执行任务(2秒) -> 释放锁
     *
     * @param lockPath 锁节点路径
     * @param taskName 任务名称
     */
    @PostMapping("/lock")
    public String lock(@RequestParam String lockPath, @RequestParam String taskName) throws Exception {
        return curatorService.doWithLock(lockPath, taskName);
    }
}
