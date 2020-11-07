package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
@Sharable // 标注线程安全
public class SharableHandlerYoung extends ChannelInboundHandlerAdapter {
    // ChannelHandler要加入多个ChannelPipele中需要加上@Sharable注解还要注意不能持有任何的状态

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Channel read message: " + msg);
        // 记录方法调用，并转发给下一个ChannelHandler
        ctx.fireChannelRead(msg);
    }
}
