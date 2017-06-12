package cn.com.kxcomm.storage.domain.manager;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @class Backend handler
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class BackendHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(BackendHandler.class);

    private final Channel inboundChannel;

    public BackendHandler(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
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
     * @since JDK1.8
     * @version 002.00.00
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
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
     * @since JDK1.8
     * @version 002.00.00
     */
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
//        log.debug("{} {} receive", msg.getClass().getName(), msg);
        inboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    ctx.channel().read();
                } else {
                    future.channel().close();
                }
            }
        });
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
     * @since JDK1.8
     * @version 002.00.00
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        FrontendHandler.closeOnFlush(inboundChannel);
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
     * @since JDK1.8
     * @version 002.00.00
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        FrontendHandler.closeOnFlush(ctx.channel());
    }
}