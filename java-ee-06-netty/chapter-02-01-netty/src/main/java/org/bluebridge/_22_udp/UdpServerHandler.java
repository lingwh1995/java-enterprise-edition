package org.bluebridge._22_udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lingwh
 * @desc 服务端UDP处理器
 * @date 2025/11/12 16:53
 */
@Slf4j
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        // 解析 UDP 数据包（获取数据和客户端地址）
        String msg = packet.content().toString(CharsetUtil.UTF_8);
        String clientAddr = packet.sender().toString();

        log.info("收到客户端 [{}] 的数据：{}", clientAddr, msg);

        // （可选）给客户端回复数据
        String response = "服务端已收到：" + msg;
        ctx.writeAndFlush(new DatagramPacket(
                io.netty.buffer.Unpooled.copiedBuffer(response, CharsetUtil.UTF_8),
                packet.sender() // 回复到原客户端地址
        ));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}