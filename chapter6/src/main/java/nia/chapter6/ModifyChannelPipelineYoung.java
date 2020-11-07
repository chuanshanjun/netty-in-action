package nia.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import static io.netty.channel.DummyChannelPipelineYoung.DUMMY_INSTANCE_YOUNG;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
public class ModifyChannelPipelineYoung {
    private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = DUMMY_INSTANCE_YOUNG;

    public static void modifyPipeline() {
        ChannelPipeline pipeline = CHANNEL_PIPELINE_FROM_SOMEWHERE;
        FirstHandler firstHandler = new FirstHandler();
        pipeline.addLast("handler1", firstHandler);
        pipeline.addFirst("handler2", new SecondHandler());
        pipeline.addLast("handler3", new ThirdHandler());

        pipeline.remove("handler3");
        pipeline.remove(firstHandler);
        pipeline.replace("handler2", "handler4", new ForthHandler());
    }

    private static final class FirstHandler extends ChannelHandlerAdapter {}

    private static final class SecondHandler extends ChannelHandlerAdapter {}

    private static final class ThirdHandler extends ChannelHandlerAdapter {}

    private static final class ForthHandler extends ChannelHandlerAdapter {}
}
