package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
 * @author:ChuanShanJun
 * @date:2020/11/9
 * @description:
 */
public class BootstrapWithInitializerYoung {

    public void bootstrap() throws InterruptedException {
        // 创建ServerBootstrap以创建和绑定新的Channel
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置EventLoopGroup，并将提供用以处理Channel事件的EventLoop
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                // 指定channel的实现
                .channel(NioServerSocketChannel.class)
                // 注册一个ChannelInitializerImpl的实例来设置ChannelPipeline
                .childHandler(new YoungChannelInitializerImpl());
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.sync();
    }

    // 用以设置ChannelPipeline的自定义ChannelInitializerImpl
    final class YoungChannelInitializerImpl extends ChannelInitializer<Channel> {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            // 将所需的ChannelHandler添加到ChannelPipeline的自定义ChannelInitializerImpl实现
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}
