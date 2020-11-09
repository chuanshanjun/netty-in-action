package nia.chapter7;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.*;

/**
 * @author:ChuanShanJun
 * @date:2020/11/8
 * @description:
 */
public class ScheduleExamplesYoung {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void schedule() {
        // 创建一个其线程池具有10个线程的ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            // 创建一个Runnable，以供调度稍后执行
            @Override
            public void run() {
                // 该任务要打印的消息
                System.out.println("60 seconds later");
            }
            // 调度任务在从现在开始的60秒之后执行
        }, 60, TimeUnit.SECONDS);
        // 一旦调度任务执行完成，就关闭ScheduledExecutorService以释放资源
        executor.shutdown();
    }

    public static void scheduleViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);
    }

    public static void scheduleFixedViaEventLoop() {
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 这将一直运行，直到ScheduledFuture被取消
                System.out.println("60 seconds later");
            }
            // 调度在60秒之后，并且以后每间隔60秒运行
        }, 60, 60, TimeUnit.SECONDS);
    }

    public static void cancelingTaskUsingScheduledFuture() {
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        // 调度任务，并获得所返回的ScheduledFuture
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 这将一直运行，直到ScheduledFuture被取消
                System.out.println("60 seconds later");
            }
            // 调度在60秒之后，并且以后每间隔60秒运行
        }, 60, 60, TimeUnit.SECONDS);

        // 取消该任务，防止它再次运行
        boolean mayInterruptRunning = false;
        future.cancel(mayInterruptRunning);
    }
}
