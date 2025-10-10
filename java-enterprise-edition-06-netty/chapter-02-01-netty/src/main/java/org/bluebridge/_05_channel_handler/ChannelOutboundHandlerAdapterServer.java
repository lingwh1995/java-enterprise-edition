package org.bluebridge._05_channel_handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc ChannelHandler的具体实现，以入站处理器ChannelOutboundHandlerAdapter为例 服务端
 * @date 2025/10/10 14:39
 */

/**
 * ChannelPipeline是由多个ChannelHander组成的.txt、ChannelHandler、和ChannelInboundHandlerAdapter之间的关系
 *      ChannelPipeline是由多个ChannelHander组成的.txt 包含一组 ChannelHandler，形成一条处理链。
 *      ChannelInboundHandlerAdapter 是 ChannelHandler 的一种具体实现（专注于入站事件）。
 */
@Slf4j
public class ChannelOutboundHandlerAdapterServer {

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new ChannelInboundHandlerAdapter() {

                        /**
                         * 方法作用
                         *    读取到数据时调用，处理入站数据，每读取到一条数据就调用一次
                         * @param ctx
                         * @param msg
                         * @throws Exception
                         */
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        String s = byteBuf.toString(Charset.defaultCharset());
                        log.info("收到客户端消息: {}", s);
                        // 这里不需要再向下传递了，直接使用出站处理器把数据返回给客户端
                        //super.channelRead(ctx, msg);

                        // 写一些数据，触发出站处理器
                        // 使用pipeline则从最后开始找出站处理器h6->h5->h4
                        ch.writeAndFlush(ctx.alloc().buffer().writeBytes(s.getBytes()));
                        }

                    });

                    pipeline.addLast(new ChannelOutboundHandlerAdapter() {
                        /**
                         * 方法作用
                         *    处理 Channel 绑定到本地地址的出站操作，这是一个 ChannelOutboundHandler 的方法，用于处理绑定请求
                         * 典型应用场景
                         *    地址绑定前的验证: 在绑定操作前验证地址的有效性
                         *    日志记录: 记录绑定操作的信息
                         *    权限检查: 检查是否有权限绑定到指定地址
                         *    资源准备: 为绑定操作准备必要的资源
                         * @param ctx
                         * @param localAddress
                         * @param promise
                         * @throws Exception
                         */
                        @Override
                        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
                            log.info("尝试绑定到地址: {}", localAddress);
                            // 可以添加地址验证逻辑
                            ctx.bind(localAddress, promise);
                        }

                        /**
                         * 方法作用
                         *    处理 Channel 连接到远程地址的出站操作，这是 ChannelOutboundHandler 接口的方法，用于处理连接请求
                         * 典型应用场景
                         *    连接前验证: 在发起连接前验证地址的有效性
                         *    日志记录: 记录连接操作的信息
                         *    连接参数设置: 设置连接超时时间、套接字选项等
                         *    权限检查: 检查是否有权限连接到指定地址
                         * @param ctx
                         * @param remoteAddress
                         * @param localAddress
                         * @param promise
                         * @throws Exception
                         */
                        @Override
                        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
                                            SocketAddress localAddress, ChannelPromise promise) throws Exception {
                            log.info("尝试连接到远程地址: {}", remoteAddress);
                            // 可以添加连接前的准备工作
                            ctx.connect(remoteAddress, localAddress, promise);
                        }

                        /**
                         * 方法作用
                         *    处理 Channel 断开连接的出站操作，这是 ChannelOutboundHandler 接口的方法，用于处理断开连接请求
                         * 典型应用场景
                         *    断开前清理: 在断开连接前执行清理工作
                         *    日志记录: 记录断开连接的操作信息
                         *    资源释放: 释放与连接相关的资源
                         *    状态更新: 更新连接状态或统计信息
                         * @param ctx
                         * @param promise
                         * @throws Exception
                         */
                        @Override
                        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                            log.info("断开连接，Channel信息: {}", ctx.channel().remoteAddress());
                            // 可以添加断开前的清理工作
                            ctx.disconnect(promise);
                        }

                        /**
                         * 方法作用
                         *    处理 Channel 关闭的出站操作，这是 ChannelOutboundHandler 接口的方法，用于处理关闭 Channel 的请求
                         * 典型应用场景
                         *    关闭前清理: 在关闭 Channel 前执行清理工作
                         *    日志记录: 记录 Channel 关闭的操作信息
                         *    资源释放: 释放与 Channel 相关的资源
                         *    连接统计: 更新连接统计信息或状态
                         * @param ctx
                         * @param promise
                         * @throws Exception
                         */
                        @Override
                        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                            log.info("关闭 Channel，Channel信息: {}", ctx.channel().remoteAddress());
                            // 可以添加关闭前的清理工作
                            ctx.close(promise);
                        }

                        /**
                         * 方法作用
                         *    处理 Channel 从 EventLoop 取消注册的出站操作，这是 ChannelOutboundHandler 接口的方法，用于处理取消注册请求
                         * 典型应用场景
                         *    取消注册前清理: 在 Channel 从 EventLoop 取消注册前执行清理工作
                         *    日志记录: 记录 Channel 取消注册的操作信息
                         *    资源释放: 释放与 EventLoop 注册相关的资源
                         *    状态更新: 更新 Channel 的注册状态信息
                         * @param ctx
                         * @param promise
                         * @throws Exception
                         */
                        @Override
                        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
                            log.info("Channel 从 EventLoop 取消注册: {}", ctx.channel().id());
                            // 可以添加取消注册前的清理工作
                            ctx.deregister(promise);
                        }

                        /**
                         * 方法作用
                         *    处理读取操作的出站方法，这是 ChannelOutboundHandler 接口的方法，用于触发从 Channel 读取数据的操作
                         * 典型应用场景
                         *    控制读取操作: 在适当的时候触发读取操作
                         *    流量控制: 根据应用状态决定何时读取数据
                         *    资源管理: 在资源准备好后再触发读取
                         *    自定义读取策略: 实现特定的读取逻辑
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void read(ChannelHandlerContext ctx) throws Exception {
                            log.info("触发读取操作，通道信息: {}", ctx.channel().remoteAddress());
                            // 可以添加读取前的准备工作
                            ctx.read();
                        }

                        /**
                         * 方法作用
                         *    处理出站数据写入操作，是 ChannelOutboundHandler 接口的核心方法，当调用 ChannelHandlerContext#write 或 Channel#write 时触发
                         * 执行流程
                         *    1.在当前示例中，当 ChannelInboundHandlerAdapter#channelRead 方法中调用 ch.writeAndFlush 时触发
                         *    2.由于 ChannelPipeline是由多个ChannelHander组成的.txt 的出站处理顺序是从后往前，会找到并执行 ChannelOutboundHandlerAdapter 的 write 方法
                         *    3.当前实现直接调用 super.write 将操作继续传播
                         * 典型应用场景
                         *    数据预处理（编码、加密、格式转换）
                         *    发送数据日志记录
                         *    流量控制和性能监控
                         *    自定义写入策略
                         * @param ctx
                         * @param msg
                         * @param promise
                         * @throws Exception
                         */
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                            // 添加日志记录
                            if (msg instanceof ByteBuf) {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                String s = byteBuf.toString(Charset.defaultCharset());
                                log.info("准备发送数据，数据内容: {}", s);
                            }
                            // 继续传播写入操作
                            super.write(ctx, msg, promise);
                        }

                        /**
                         * 方法作用
                         *    处理刷新操作的出站方法，这是 ChannelOutboundHandler 接口的方法，用于将缓冲区中的数据立即写出到网络
                         * 典型应用场景
                         *    数据立即发送: 强制将缓冲区中的数据立即发送出去
                         *    批量发送优化: 结合 write 操作实现批量发送后统一刷新
                         *    性能调优: 控制数据发送时机以优化网络性能
                         *    协议要求: 某些协议要求特定时机发送数据
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void flush(ChannelHandlerContext ctx) throws Exception {
                            log.info("执行刷新操作，通道信息: {}", ctx.channel().remoteAddress());
                            // 可以添加刷新前的处理逻辑
                            ctx.flush();
                        }
                    });
                }
            })
            .bind(8080);
    }

}
