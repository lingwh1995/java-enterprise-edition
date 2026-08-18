package org.bluebridge.section_22_udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 客户端 UDP 处理器
 *
 * @author lingwh
 * @date 2025/11/12 16:56
 */
@Slf4j
public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        // 解析服务端回复
        String response = packet.content().toString(CharsetUtil.UTF_8);
        String serverAddr = packet.sender().toString();
        log.info("收到服务端 [{}] 的回复：{}", serverAddr, response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}