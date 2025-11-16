package org.bluebridge._21_optimize.bio_accept_queue;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/16 15:53
 */
@Slf4j
public class Server {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PORT, 2, InetAddress.getByName(HOST));
        Socket accept = ss.accept();
        log.info("连接成功:{}",accept);
        System.in.read();
    }

}
