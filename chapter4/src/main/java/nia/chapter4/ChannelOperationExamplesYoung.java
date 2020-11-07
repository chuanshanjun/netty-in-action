package nia.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author:ChuanShanJun
 * @date:2020/11/1
 * @description:
 */
public class ChannelOperationExamplesYoung {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void writingToChannel() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // 创建持有要写数据的ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("your data", StandardCharsets.UTF_8);
        // 写数据并且冲刷它
        ChannelFuture cf = channel.writeAndFlush(buf);
        // 添加ChannelFutureListener以便在写操作完成后接收通知
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Write successful");
                } else {
                    // 记录错误
                    System.err.println("write error");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMEWHERE;
        // 创建有要写数据的ByteBuf
        final ByteBuf buf = Unpooled.copiedBuffer("your data", StandardCharsets.UTF_8);
        // 创建将数据写到Channel的Runnable
        Runnable writer = new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(buf.duplicate());
            }
        };
        // 获取线程池Executor的引用
        Executor executor = Executors.newCachedThreadPool();
        // write in one thread
        executor.execute(writer); // 递交写任务给线程池以便在某个线程中执行
        // write in another thread
        executor.execute(writer); // 递交另一个写任务以便在另一个线程中执行
    }
}
