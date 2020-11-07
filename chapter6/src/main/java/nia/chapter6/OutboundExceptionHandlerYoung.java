package nia.chapter6;

import io.netty.channel.*;

/**
 * @author:ChuanShanJun
 * @date:2020/11/8
 * @description:
 */
public class OutboundExceptionHandlerYoung extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if (!f.isSuccess()) {
                    f.cause().printStackTrace();
                    f.channel().close();
                }
            }
        });
    }
}
