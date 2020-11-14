package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author:ChuanShanJun
 * @date:2020/11/9
 * @description:
 */
public class BootstrapClientYoung {
    public static void main(String[] args) {
        BootstrapClientYoung client = new BootstrapClientYoung();
        client.bootstrap();
    }

    public void bootstrap() {
        // 设置EventLoopGroup，提供用于处理Channel事件的EventLoop
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建一个Bootstrap类的实例以创建和连接新的客户端Channel
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                // 指定要使用的Channel实现
                .channel(NioSocketChannel.class)
                // 设置用于Channel事件和数据的ChannelInboundHandler
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                System.out.println("Received data");
            }
        });
        ChannelFuture future = bootstrap.connect(
                // 连接到远程主机
                new InetSocketAddress("www.manning.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.out.println("Connection attempt failed");
                    f.cause().printStackTrace();
                }
            }
        });
    }
}
