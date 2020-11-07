package io.netty.channel;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
public class DummyChannelPipelineYoung extends DefaultChannelPipeline {
    public static final ChannelPipeline DUMMY_INSTANCE_YOUNG = new DummyChannelPipelineYoung(null);
    protected DummyChannelPipelineYoung(Channel channel) {
        super(channel);
    }
}
