package cn.com.kxcomm.storage.domain.manager;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.SocketAddress;

public class FrontendHandler extends ChannelInboundHandlerAdapter {


    private final SocketAddress address;

    // As we use inboundChannel.eventLoop() when building the Bootstrap this does not need to be volatile as
    // the outboundChannel will use the same EventLoop (and therefore Thread) as the inboundChannel.
    private Channel outboundChannel;

    public FrontendHandler(SocketAddress address) {
        this.address = address;
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final Channel inboundChannel = ctx.channel();

        // Start the connection attempt.
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(1048576 * 101, ClassResolvers.cacheDisabled(null)),
                                new BackendHandler(inboundChannel));
                    }
                })
                .option(ChannelOption.AUTO_READ, false);
        ChannelFuture f = b.connect(address);
        outboundChannel = f.channel();
        f.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                // connection complete start to read first data
                inboundChannel.read();
            } else {
                // Close the connection if the connection attempt has failed.
                inboundChannel.close();
            }
        });
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        if (outboundChannel.isActive()) {
            outboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        // was able to flush out data, start to read the next chunk
                        ctx.channel().read();
                    } else {
                        future.channel().close();
                    }
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (outboundChannel != null) {
            closeOnFlush(outboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}