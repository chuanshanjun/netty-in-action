package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

/**
 * @author:ChuanShanJun
 * @date:2020/11/10
 * @description:
 */
public class GracefulShutdownYoung {

    public void bootstrap() {
        // 创建处理I/O的EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建一个Bootstrap类的实例并配置它
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
        // shutdownGracefully()方法将释放所有的资源，并且关闭所有当前正在使用中的Channel
        Future<?> future = group.shutdownGracefully();
        future.syncUninterruptibly();
    }
}
