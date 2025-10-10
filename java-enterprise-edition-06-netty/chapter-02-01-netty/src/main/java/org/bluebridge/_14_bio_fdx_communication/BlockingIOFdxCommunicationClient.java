package org.bluebridge._14_bio_fdx_communication;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc 基于Java的BIO+多线程实现全双工通信 客户端
 * @date 2025/10/10 11:01
 */
@Slf4j
public class BlockingIOFdxCommunicationClient {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", PORT);

        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                while (true) {
                    log.info("接收到的数据: {}", reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                for (int i = 0; i < 100; i++) {
                    writer.write("我是来自客户端的消息: " + i);
                    writer.newLine();
                    writer.flush();
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
