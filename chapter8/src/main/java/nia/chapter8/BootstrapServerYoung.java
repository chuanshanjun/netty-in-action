package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * @author:ChuanShanJun
 * @date:2020/11/9
 * @description:
 */
public class BootstrapServerYoung {

    public void bootstrap() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 创建ServerBootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置EventLoopGroup,其提供了用于处理Channel事件的EventLoop
        bootstrap.group(group)
                // 指定要使用的Channel
                .channel(NioServerSocketChannel.class)
                // 设置用于处理已被接受的子Channel的I/O及数据的ChannelInbound-Handler
                .childHandler(new SimpleChannelInboundHandler<ByteBuffer>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        // 通过配置好的ServerBootstrap的实例绑定该Channelch
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.out.println("Bound attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
