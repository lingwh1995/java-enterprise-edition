package org.bluebridge.section_20_rpc.server.service;

/**
 * HelloServiceImpl
 *
 * @author lingwh
 * @date 2025/10/27 15:20
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        // int i = 1 / 0;
        return "你好, " + msg;
    }
}