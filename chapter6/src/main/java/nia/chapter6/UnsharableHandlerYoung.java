package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author:ChuanShanJun
 * @date:2020/11/8
 * @description:
 */
@Sharable
public class UnsharableHandlerYoung extends ChannelInboundHandlerAdapter {
    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        count++;
        System.out.println("channelRead(...) called the" + count + "time");
        ctx.fireChannelRead(msg);
    }
}
