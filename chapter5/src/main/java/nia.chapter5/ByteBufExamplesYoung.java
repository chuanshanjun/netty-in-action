package nia.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static io.netty.channel.DummyChannelHandlerContext.DUMMY_INSTANCE;

/**
 * @author:ChuanShanJun
 * @date:2020/11/1
 * @description:
 */
public class ByteBufExamplesYoung {
    private final static Random random = new Random();
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;

    /**
     * backing array 支撑数组
     */
    public static void heapBuffer() {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 检查ByteBuf是否有一个支撑数组
        if (heapBuf.hasArray()) {
            // 如果有则获取对该数组的银行
            byte[] array = heapBuf.array();
            // 计算第一个字节的偏移量
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            // 获得可读字节数
            int length = heapBuf.readableBytes();
            // 使用数组、偏移量和长度作为参数调用你的方法
            handleArray(array, offset, length);
        }
    }

    /**
     * 直接缓冲区
     */
    public static void directBuffer() {
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 检查ByteBuf是否由数组支撑。如果不是，则这是一个直接缓冲区
        if (!directBuf.hasArray()) {
            // 获取可读字节数
            int length = directBuf.readableBytes();
            // 分配一个新的数组来保存具有该长度的字节数据
            byte[] array = new byte[length];
            // 将字节复制到该数据
            directBuf.getBytes(directBuf.readerIndex(), array);
            // 使用数组、偏移量和长度作为参数调用你的方法
            handleArray(array, 0, length);
        }
    }

    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        // Use an array to hold the message parts
        ByteBuffer[] message = new ByteBuffer[] {header, body};
        // create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
    }

    public static void byteBufComposite() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE;
        ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;
        // 将ByteBuf实例追加到CompositeByteBuf
        messageBuf.addComponents(headerBuf, bodyBuf);

        // 删除位于索引位置为0(第一个组件)的ByteBuf
        messageBuf.removeComponent(0);
        // 循环遍历所有ByteBuf实例
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString());
        }
    }

    public static void byteBufCompositeArray() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        // 获得可读字节数
        int length = compBuf.readableBytes();
        // 分配一个具有可读字节数长度的新数组
        byte[] array = new byte[length];
        // 将字节读到该数组中
        compBuf.getBytes(compBuf.readerIndex(), array);
        // 作为偏移量和长度作为参数使用该数组
        handleArray(array, 0, array.length);
    }

    public static void byteBufRelativeAccess() {
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        for (int i = 0; i < byteBuf.capacity() - 1; i++) {
            byte b = byteBuf.getByte(i);
            System.out.println((char) b);
        }
    }

    public static void readAllData() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        while (buffer.isReadable()) {
            System.out.println(buffer.readByte());
        }
    }

    public static void write() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        while (buffer.writableBytes() >=4 ) {
            buffer.writeInt(random.nextInt());
        }
    }

    public static void byteProcessor() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        int indesc = buffer.forEachByte(ByteProcessor.FIND_CR);
    }

    /**
     * Slice a ByteBuf
     */
    public static void byteBufSlice() {
        // 创建一个用于保存给定字符串的字节的ByteBuf
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action", StandardCharsets.UTF_8);
        // 创建该ByteBuf从索引0开始到索引15结束的一个新切片
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(StandardCharsets.UTF_8));
        // 更新索引为0处的字节
        buf.setByte(0, (byte)'J');
        System.out.println("(byte) J " + (byte)'J');
        // 将会成功，因为数据是共享的，对其中一个所做的更改对另外一个也是可见的
        assert buf.getByte(0) == sliced.getByte(0);
        System.out.println(buf.getByte(0) == sliced.getByte(0));
    }

    /**
     * 除了修改原始ByteBuf的切片或者副本的效果以外，这两种场景是相同的。
     * 只要有可能，使用slice()方法来避免复制内存的开销
     * Copying a ByteBuf
     */
    public static void byteBufCopy() {
        // 创建ByteBuf以保存所提供的字符串的字节
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action rocks!", StandardCharsets.UTF_8);
        // 创建该ByteBuf从索引0开始到索引15结束的分段副本
        ByteBuf copy = buf.copy(0, 15);
        // 打印Netty In Action
        System.out.println(copy.toString(StandardCharsets.UTF_8));
        // 更新索引为0处的字节
        copy.setByte(0, (byte)'J');
        // 将会成功，因为数据不是共享的
        assert buf.getByte(0) != copy.getByte(0);
        System.out.println(buf.getByte(0) != copy.getByte(0));
    }

    public static void byteBufSetGet() {
        // 创建一个新的ByteBuf以保存给定字符串的字节
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action rocks", StandardCharsets.UTF_8);
        // 打印第一个字符'N'
        System.out.println((char)buf.getByte(0));
        // 存储当前的readerIndex和writeIndex
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        // 将索引0处的字节更新为字符'B'
        buf.setByte(0, (byte)'B');
        // 打印第一个字符，现在是'B'
        System.out.println((char)buf.getByte(0));
        // 将会成功，因为这些操作并不会修改相应的索引
        assert readerIndex == buf.readerIndex();
        assert writerIndex == buf.writerIndex();
    }

    public static void byteBufWriteRead() {
        ByteBuf buf = Unpooled.copiedBuffer("Netty In Action rocks!", StandardCharsets.UTF_8);
        System.out.println((char)buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        // 将字符'?'追加到缓冲区
        buf.writeByte((byte)'?');
        assert readerIndex == buf.readerIndex();
        // 将会成功，因为writeByte()方法移动了writeIndex
        assert writerIndex != buf.writerIndex();
    }

    /**
     * Listing 5.14 Obtaining a ByteBufAllocator reference
     */
    public static void obtainingByteBufAllocatorReference() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();

        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ByteBufAllocator allocator2 = ctx.alloc();
    }

    public static void referenceCounting() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // 从Channel获取ByteBufAllocator
        ByteBufAllocator allocator = channel.alloc();
        // 从ByteBufAllocator分配一个ByteBuf
        ByteBuf buffer = allocator.directBuffer();
        // 检查引用计数是否为预期的1
        assert buffer.refCnt() == 1;
    }

    public static void releaseReferenceCountedObject() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
        // 减少到该对象的活动引用。当减少到0时，该对象被释放，并且该方法返回true
        boolean released = buffer.release();
    }

    public static void main(String[] args) {
        byteBufWriteRead();
    }


    private static void handleArray(byte[] array, int offset, int len) {}
}
