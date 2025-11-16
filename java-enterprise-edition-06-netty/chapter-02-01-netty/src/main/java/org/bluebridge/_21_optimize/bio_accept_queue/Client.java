package org.bluebridge._21_optimize.bio_accept_queue;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/16 15:53
 */
@Slf4j
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(HOST, PORT),1000);
            log.info("连接成功: {}", s);
            s.getOutputStream().write(1);
            System.in.read();
        } catch (IOException e) {
            log.error("连接超时:{}", e);
        }
    }

}
