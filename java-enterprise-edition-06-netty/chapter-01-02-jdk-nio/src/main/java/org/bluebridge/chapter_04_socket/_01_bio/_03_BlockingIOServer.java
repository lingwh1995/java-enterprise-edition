package org.bluebridge.chapter_04_socket._01_bio;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lingwh
 * @desc
 * @date 2025/7/7 18:27
 */

/**
 * BIO模型网络通信Server端
 *  V3.0 1.服务端接收多个客户端多条消息发送和接收需求(一个客户端对应一个线程)  2.通过线程池实现伪异步通信架构
 *
 * 测试方法
 *  1.cmd -> telnet 127.0.0.1 8080/telnet localhost 8080 -> 直接输入内容（只能发送单个字符）/按下Ctrl+]后输入 send + 内容（可以发送字符串） -> 查看idea控制台接收到的信息
 *  2.启动多个客户端
 *
 * 测试结论
 *  1.使用BIO模型时，有多少个客户端，服务端就会产生多少个线程，即服务端会为每一个客户端创建一个线程
 *  2.BIO会阻塞
 *  3.32位机器下每个线程320kb，64位机器下每个线程1024kb，如果创建的线程过多，极有可能出现OOM现象，可以使用线程池进行优化，但是
 *    仍要注意，即使使用了线程池进行优化，也不适用于处理连接线程过多且都是长连接的的情况，一定程度上具备处理多个短连接的能力
 */
@Slf4j
public class _03_BlockingIOServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // 1.获取ServerSocket
            ServerSocket serverSocket = new ServerSocket(PORT);
            // 2.初始化一个线程池
            ServerThreadPool serverThreadPool = new ServerThreadPool(6, 10);
            log.info("阻塞服务器启动，端口：{}......", PORT);
            while (true) {
                //3.获取Socket
                Socket socket = serverSocket.accept();
                //4.把Socket封装成任务对象交给线程池处理
                ServerRunnableTask task = new ServerRunnableTask(socket);
                //5.使用线程池处理任务
                serverThreadPool.execute(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 用于处理多个客户端连接同一个服务端的线程池类
 */
class ServerThreadPool {
    private ExecutorService executorService;

    /**
     *
     * @param maxThreadNum 线程池最大的线程数量
     * @param taskQueueSize 线程池中任务队列的大小
     */
    public ServerThreadPool(int maxThreadNum, int taskQueueSize) {
        /**
         * 创建一个线程池对象,最好使用这个API进行创建,不推荐使用Executors.xxx()来创建
         * 参数信息：
         * int corePoolSize     核心线程大小，同时可以处理的线程数目，如果超过这个数目，则先会在队列中进行缓存
         * int maximumPoolSize  线程池最大容量大小
         * long keepAliveTime   线程空闲时，线程存活的时间
         * TimeUnit unit        时间单位
         * BlockingQueue<Runnable> workQueue  任务队列大小(一个阻塞队列)
         */
        this.executorService =
                new ThreadPoolExecutor(3, maxThreadNum, 120,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(taskQueueSize));
    }

    /**
     * 提供一个方法来提交任务给线程池的任务队列来缓存,等着线程池来处理
     * @param task
     */
    public void execute(Runnable task) {
        executorService.execute(task);
    }
}


/**
 * 封装了Socket的任务对象
 */
@Slf4j
class ServerRunnableTask implements Runnable {

    private Socket socket;

    public ServerRunnableTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        try {
            //1.通过Socket获取输入流
            InputStream socketInputStream = socket.getInputStream();
            //2.获取包装流
            bufferedReader = new BufferedReader(new InputStreamReader(socketInputStream));
            String message = null;
            //3.读取消息
            while((message = bufferedReader.readLine()) != null) {
                log.info("来自客户端的消息: {}，当前线程: {}", message, Thread.currentThread().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
