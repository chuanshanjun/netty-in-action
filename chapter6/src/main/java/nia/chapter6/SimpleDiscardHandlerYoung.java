package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
@Sharable
public class SimpleDiscardHandlerYoung extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // No need to do anything special 不需要任何显示的资源释放
    }
}
