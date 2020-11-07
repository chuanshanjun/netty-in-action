package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
@Sharable
public class DiscardHandlerYoung extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 丢弃已接收的消息
        ReferenceCountUtil.release(msg);
    }
}
