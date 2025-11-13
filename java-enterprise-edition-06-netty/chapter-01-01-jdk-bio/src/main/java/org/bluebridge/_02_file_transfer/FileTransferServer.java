package org.bluebridge._02_file_transfer;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lingwh
 * @desc 文件传输服务端
 * @date 2025/11/13 15:20
 */
@Slf4j
public class FileTransferServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 1.获取ServerSocket
            InetAddress address = InetAddress.getByName(HOST);
            ServerSocket serverSocket = new ServerSocket(PORT, 50, address);
            log.info("文件传输服务器启动......");
            while (true) {
                // 2.获取Socket
                Socket socket = serverSocket.accept();
                // 3.接收文件并存储到另一个地方
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

@Slf4j
class ServerThread implements Runnable {

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        FileOutputStream fileOutputStream = null;
        try {
            // 1.获取输入流
            InputStream socketInputStream = socket.getInputStream();
            // 2.获得数据输入流
            DataInputStream dataInputStream = new DataInputStream(socketInputStream);
            // 3.定义输出流
            String suffix = dataInputStream.readUTF();
            log.info("服务端成功接收到文件后缀名：{}", suffix);
            String fileName = "a1" + suffix;
            fileOutputStream = new FileOutputStream("D:\\" + fileName);
            // 4.写文件
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = dataInputStream.read(buffer)) > 0 ) {
                fileOutputStream.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}