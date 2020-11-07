package nia.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
public class WriteHandlerYoung extends ChannelHandlerAdapter {
    private ChannelHandlerContext ctx;

    /**
     * 存储到ChannelHandlerContext的引用以供稍后使用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    /**
     * 使用之前存储的到ChannelHandlerContext的引用来发送消息
     * @param msg
     */
    public void send(String msg) {
        ctx.writeAndFlush(msg);
    }
}
