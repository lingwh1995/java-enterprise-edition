package org.bluebridge._05_channel_handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author lingwh
 * @desc ChannelHandler 的具体实现 ChannelInboundHandlerAdapter 服务端
 * @date 2025/10/10 11:32
 */

/**
 * ChannelPipeline 是由多个 ChannelHander 组成的， ChannelHandler和 ChannelInboundHandlerAdapter 之间的关系
 *     ChannelPipeline 是由多个 ChannelHander 组成的 包含一组 ChannelHandler，形成一条处理链。
 *     ChannelInboundHandlerAdapter 是 ChannelHandler 的一种具体实现（专注于入站事件）。
 */
@Slf4j
public class ChannelInboundHandlerAdapterServer {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

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
                         *    当 Channel 注册到 EventLoop 时调用，标志着 Channel 已经准备好处理I/O操作
                         * 典型应用场景
                         *    记录通道注册日志
                         *    初始化与通道相关的资源
                         *    设置通道属性或上下文信息
                         *    执行注册时的业务逻辑
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                            log.info("Channel 注册到 EventLoop中了，通道id: {}", ctx.channel().id());
                            super.channelRegistered(ctx);
                        }

                        /**
                         * 方法作用
                         *    当 Channel 从 EventLoop 取消注册时调用，标志着 Channel 不再能够处理 I/O 操作
                         * 典型应用场景
                         *    记录通道取消注册日志
                         *    清理与通道相关的资源
                         *    执行取消注册时的业务逻辑
                         *    更新连接统计信息
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                            log.info("Channel 从 EventLoop中取消注册了，通道id: {}", ctx.channel().id());
                            super.channelUnregistered(ctx);
                        }

                        /**
                         * 方法作用
                         *    在 Channel 处于活动状态时被调用，表示连接已经建立，可以开始传输数据
                         * 典型应用场景
                         *    通常在这个方法中可以添加连接建立时的处理逻辑，例如：
                         *        记录连接建立日志
                         *        初始化连接相关的资源
                         *        发送欢迎消息给客户端
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            log.info("客户端连接已建立，连接信息: {}", ctx.channel().remoteAddress());
                            super.channelActive(ctx);
                        }

                        /**
                         * 方法作用
                         *    当 Channel 失去连接，变成非活动状态时会被调用，通常用于处理连接断开后的清理工作
                         * 典型应用场景
                         *    可以在该方法中添加以下处理逻辑：
                         *        记录客户端断开连接的日志
                         *        清理与该连接相关的资源
                         *        更新在线用户统计
                         *        执行其他连接断开时需要的业务逻辑
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            log.info("客户端连接已断开，连接信息: {}", ctx.channel().remoteAddress());
                            super.channelInactive(ctx);
                        }

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
                            super.channelRead(ctx, msg);
                        }

                        /**
                         * 方法作用
                         *    当一次数据读取操作完全完成时被调用，标志着当前批次的数据读取结束
                         * 典型应用场景
                         *    刷新数据: 通常在此方法中调用 ctx.flush() 将缓冲的数据发送出去
                         *    批量处理完成: 标识一批数据处理的结束
                         *    资源清理: 清理与本次读取操作相关的临时资源
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                            log.info("本次读取数据完成......");
                            // 刷新缓冲区，确保数据发送
                            ctx.flush();
                            super.channelReadComplete(ctx);
                        }

                        /**
                         * 方法作用
                         *    处理用户自定义事件的回调方法，当 ChannelPipeline 是由多个 ChannelHander 组成的 中触发用户事件时被调用
                         * 典型应用场景
                         *    处理空闲检测事件（如 IdleStateEvent）
                         *    处理自定义业务事件
                         *    心跳检测和超时处理
                         *    协议升级等特殊事件处理
                         * @param ctx
                         * @param evt
                         * @throws Exception
                         */
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            if (evt instanceof IdleStateEvent) {
                                IdleStateEvent idleEvent = (IdleStateEvent) evt;
                                log.info("空闲检测事件: {}", idleEvent.state());
                                // 处理空闲事件逻辑
                            }
                            // 继续传播事件
                            ctx.fireUserEventTriggered(evt);
                        }

                        /**
                         * 方法作用
                         *    当 Channel 的可写状态发生改变时被调用，用于处理通道写入能力变化的事件
                         * 触发条件
                         *    当 Channel 从可写状态变为不可写状态时触发
                         *    当 Channel 从不可写状态变为可写状态时触发
                         *    通常与 Channel 的写缓冲区状态相关
                         * 典型应用场景
                         *    流量控制: 监控和控制数据发送速率
                         *    缓冲区管理: 根据可写状态调整数据写入策略
                         *    性能优化: 在通道不可写时暂停数据发送，避免内存溢出
                         * @param ctx
                         * @throws Exception
                         */
                        @Override
                        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                            if (ctx.channel().isWritable()) {
                                log.info("Channel is writable now......");
                                // 恢复数据发送
                            } else {
                                log.info("Channel is not writable......");
                                // 暂停数据发送，避免缓冲区溢出
                            }
                            ctx.fireChannelWritabilityChanged();
                        }

                        /**
                         * 方法作用
                         *    处理 ChannelPipeline 是由多个 ChannelHander 组成的 中发生的异常事件，当 ChannelHandler 处理过程中抛出异常时被调用
                         * 典型应用场景
                         *    异常处理: 捕获和处理网络通信过程中的各种异常
                         *    错误日志记录: 记录异常信息用于调试和监控
                         *    连接清理: 在发生严重异常时关闭连接或执行清理操作
                         *    故障恢复: 根据异常类型采取相应的恢复措施
                         * @param ctx
                         * @param cause
                         * @throws Exception
                         */
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            // 记录异常日志
                            log.error("处理过程中发生异常: ", cause);

                            // 根据异常类型决定是否关闭连接
                            if (cause instanceof IOException) {
                                // IO异常通常需要关闭连接
                                ctx.close();
                            } else {
                                // 其他异常继续传播
                                ctx.fireExceptionCaught(cause);
                            }
                        }

                    });
                }
            })
            .bind(HOST, PORT);
    }

}
