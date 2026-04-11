package org.bluebridge._02_file_transfer;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author lingwh
 * @desc 文件传输客户端
 * @date 2025/11/13 15:29
 */
@Slf4j
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            log.info("Client启动......");
            // 1.获取Socket
            Socket socket = new Socket(HOST, PORT);
            // 2.获取输出流
            OutputStream socketOutputStream = socket.getOutputStream();
            // 3.获取数据输出流并把字节输出流包装成一个数据输出流
            DataOutputStream dataOutputStream = new DataOutputStream(socketOutputStream);
            // 4.发送文件后缀名
            dataOutputStream.writeUTF(".txt");
            // 5.读取本地文件数据
            FileInputStream fileInputStream = new FileInputStream("D:\\a.txt");
            byte[] buffer = new byte[1024];
            int len = 0;
            // 6.发送文件
            while ((len = fileInputStream.read(buffer)) > 0) {
                System.out.println("本次从管道中读取到的数据的长度:" + len);
                dataOutputStream.write(buffer,0,len);
            }
            // 7.从管道中刷出数据
            dataOutputStream.flush();
            // 8.通知服务端Client的数据发送完毕(不处理的话发送的图片过去打不开,本质是没有成功发送成功)
            // 加了下面这句,服务端这个异常就不会出现了 java.net.SocketException: Connection reset
            socket.shutdownOutput();
            log.info("Client发送文件完成......");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
