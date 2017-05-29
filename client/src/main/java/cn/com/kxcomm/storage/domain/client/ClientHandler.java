package cn.com.kxcomm.storage.domain.client;


import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private final Client client;

    private Channel channel;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        log.debug("{} {} receive", msg.getClass().getName(), msg);
        if(msg instanceof Response) {
            Response response = (Response)msg;
            client.putResponse(response);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("channelInactive");
        log.debug("channelActive");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                client.start();
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    public void writeAndFlush(Object msg) {
        while(channel == null) {
            Thread.yield();
        }
        channel.writeAndFlush(msg);
    }

    public boolean channelIsActive() {
        if(channel == null) {
            return false;
        }
        return channel.isActive();
    }

}
