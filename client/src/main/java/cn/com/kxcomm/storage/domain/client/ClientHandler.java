package cn.com.kxcomm.storage.domain.client;


import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @class Client handler
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private final Client client;

    private Channel channel;

    public ClientHandler(Client client) {
        this.client = client;
    }

    /**
     * @method Channel active.
     * @description
     * @author 李穗恒
     * @param ctx the ctx
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        this.channel = ctx.channel();
    }

    /**
     * @method Channel read.
     * @description
     * @author 李穗恒
     * @param ctx the ctx
     * @param msg the msg
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        log.debug("{} {} receive", msg.getClass().getName(), msg);
        if(msg instanceof Response) {
            Response response = (Response)msg;
            client.putResponse(response);
        }

    }

    /**
     * @method Channel read complete.
     * @description
     * @author 李穗恒
     * @param ctx the ctx
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();

    }

    /**
     * @method Channel inactive.
     * @description
     * @author 李穗恒
     * @param ctx the ctx
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
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

    /**
     * @method Exception caught.
     * @description
     * @author 李穗恒
     * @param ctx the ctx
     * @param cause the cause
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * @method Write and flush.
     * @description
     * @author 李穗恒
     * @param msg the msg
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public void writeAndFlush(Object msg) {
        while(channel == null) {
            Thread.yield();
        }
        channel.writeAndFlush(msg);
    }

    /**
     * @method Channel is active boolean.
     * @description
     * @author 李穗恒
     * @return the boolean
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public boolean channelIsActive() {
        if(channel == null) {
            return false;
        }
        return channel.isActive();
    }

}
