package org.bluebridge._03_groupchat._01_without_gui;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * BIO聊天室(群聊)
 *    目标：BIO模式下的端口转发思想-Server实现
 *
 * 服务端实现需求
 *    1.注册端口
 *    2.接收客户端的socket连接，交给一个独立的线程来处理
 *    3.把当前连接的客户端socket存入到一个所谓的在线socket集合中保存
 *    4.接收客户端的消息，然后推送给当前所有的在线socket接收
 */
@Slf4j
public class ChatServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    // 定义一个静态集合
    public static List<Socket> allSocketOnLine = new ArrayList<>();

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName(HOST);
            ServerSocket ss = new ServerSocket(PORT, 50, address);
            log.info("聊天服务器启动成功......");
            while (true){
                Socket socket = ss.accept();
                System.out.println(socket.hashCode());
                // 把登录的客户端socket存入到一个在线集合中去
                allSocketOnLine.add(socket);
                // 为当前登录成功的socket分配一个独立的线程来处理与之通信
                new ChatServerThread(socket).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

@Slf4j
class ChatServerThread extends Thread{

    private Socket socket;
    public ChatServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 1.从socket中去获取当前客户端的输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;
            while ((msg = br.readLine()) != null){
                log.info("服务器收到消息： {}", msg);
                // 2.服务端接收到了客户端的消息后，需要推送给所有的当前在线的socket
                sendMsgToAllClient(msg,socket);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("当前有人下线了！");
            // 从在线socket集合中移除本socket
            ChatServer.allSocketOnLine.remove(socket);
        }
    }

    /**
     * 把当前客户端发送来的消息推送给全部在线的socket
     * @param msg
     */
    private void sendMsgToAllClient(String msg, Socket socket) throws Exception {
        for(Socket sk : ChatServer.allSocketOnLine){
            // 只发送给除自己以外的其他客户端
            if(socket != sk){
                PrintStream ps = new PrintStream(sk.getOutputStream());
                ps.println(msg);
                ps.flush();
            }
        }
    }

}
