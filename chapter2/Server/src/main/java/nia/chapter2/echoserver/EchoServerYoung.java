package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author:ChuanShanJun
 * @date:2020/10/31
 * @description:
 */
public class EchoServerYoung {
    private final int port;

    public EchoServerYoung(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println(
                    "Usage: " + EchoServer.class.getSimpleName() + " <port>"
            );
        }
        int port = Integer.parseInt(args[0]);
        new EchoServerYoung(port).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandlerYoung serverHandlerYoung = new EchoServerHandlerYoung();
        // 1 创建EventGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 2 创建ServerBootStrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个EchoServer-Handler到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // EchoServerHandler被标注为@Shareable
                            // 所以我们可以总是使用同样的实例
                            ch.pipeline().addLast(serverHandlerYoung);
                        }
                    });
            // 异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 获取Channel呃CloseFuture,并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup,释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
