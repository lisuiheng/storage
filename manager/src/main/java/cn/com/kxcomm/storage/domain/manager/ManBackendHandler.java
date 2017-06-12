package cn.com.kxcomm.storage.domain.manager;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @class Man backend handler
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Component
@ChannelHandler.Sharable
public class ManBackendHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(ManBackendHandler.class);
    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();

    private Channel inboundChannel;

    /**
     * @method Set inbound channel.
     * @description
     * @author 李穗恒
     * @param inboundChannel the inbound channel
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    void setInboundChannel(Channel inboundChannel) {
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
        if(inboundChannel == null) {
            throw new RuntimeException("inboundChannel can not null, please setInboundChannel");
        }
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
        log.debug("{} {} receive", msg.getClass().getName(), msg);
        if(!(msg instanceof Response)) {
            log.error("receive wrong class {}, should be Response", msg.getClass().getName());
        } else {
            Response response = (Response)msg;
            BlockingQueue<Response> queue = responseMap.get(response.getId());
            queue.add(response);
        }

//        inboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                if (future.isSuccess()) {
//                    ctx.channel().read();
//                } else {
//                    future.channel().close();
//                }
//            }
//        });
    }

    /**
     * @method Put if absent.
     * @description
     * @author 李穗恒
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public void putIfAbsent(Request request) {
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
    }

    /**
     * @method Get response response.
     * @description
     * @author 李穗恒
     * @return the response
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public Response getResponse(Request request) {
        Response result;
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
        try {
            result = responseMap.get(request.getId()).take();
            if (null == result) {
                throw new RuntimeException("not result");
            }
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            responseMap.remove(request.getId());
        }
        return result;
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
        ManFrontendHandler.closeOnFlush(inboundChannel);
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
        ManFrontendHandler.closeOnFlush(ctx.channel());
    }
}