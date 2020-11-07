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
public class DiscardInboundHandlerYoung extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 通过调用ReferenceCountUtil.release(msg)方法释放资源
        ReferenceCountUtil.release(msg);
    }
}
