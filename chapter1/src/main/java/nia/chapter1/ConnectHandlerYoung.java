package nia.chapter1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author:ChuanShanJun
 * @date:2020/10/31
 * @description:
 */
public class ConnectHandlerYoung extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当一个新的链接被建立时，channelActive(ChannelHandlerContext ctx)
        // 将会被回调
        System.out.println(
                "client" + ctx.channel().remoteAddress() + " connected");
    }
}
