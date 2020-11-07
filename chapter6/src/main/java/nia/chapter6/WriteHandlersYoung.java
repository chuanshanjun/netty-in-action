package nia.chapter6;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.nio.charset.StandardCharsets;

import static io.netty.channel.DummyChannelHandlerContextYoung.DUMMY_INSTANCE_YOUNG;

/**
 * @author:ChuanShanJun
 * @date:2020/11/7
 * @description:
 */
public class WriteHandlersYoung {
    private static final ChannelHandlerContext
            CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE_YOUNG;
    private static final ChannelPipeline
            CHANNEL_HANDLER_FROM_SOMEWHERE = DummyChannelPipelineYoung.DUMMY_INSTANCE_YOUNG;

    public static void writeViaChannel() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        // 获取到与ChannelHandlerContext相关联的Channel的引用
        Channel channel = ctx.channel();
        // 通过Channel写入缓冲区
        channel.write(Unpooled.copiedBuffer("Netty in Action", StandardCharsets.UTF_8));
    }

    public static void writeViaChannelPipeline() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        // 获取到与ChannelHandlerContext相关联的ChannelPipeline的引用
        ChannelPipeline pipeline = ctx.pipeline();
        // 通过ChannelPipeline写入缓冲区
        pipeline.write(Unpooled.copiedBuffer("Netty In Action",
                StandardCharsets.UTF_8));
    }

    public static void writeViaChannelHandlerContext() {
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ctx.write(Unpooled.copiedBuffer("Netty in Action", StandardCharsets.UTF_8));
    }
}
