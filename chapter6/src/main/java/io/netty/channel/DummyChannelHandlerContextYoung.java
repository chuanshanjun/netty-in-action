package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
public class DummyChannelHandlerContextYoung extends AbstractChannelHandlerContext {
    public static DummyChannelHandlerContextYoung DUMMY_INSTANCE_YOUNG = new DummyChannelHandlerContextYoung(
            null,null,null,true,true
    );

    DummyChannelHandlerContextYoung(DefaultChannelPipeline pipeline,
                                    EventExecutor executor,
                                    String name, boolean inbound, boolean outbound) {
        super(pipeline, executor, name, inbound, outbound);
    }

    @Override
    public ChannelHandler handler() {
        return null;
    }
}
