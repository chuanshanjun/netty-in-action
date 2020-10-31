package nia.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author:ChuanShanJun
 * @date:2020/10/31
 * @description:
 */
public class ConnectExampleYoung {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void main(String[] args) {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // Does not block
        ChannelFuture future = channel.connect(new InetSocketAddress("127.0.0.1", 3338));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    future.channel().writeAndFlush(buffer);
                    //...
                } else {
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }
}
