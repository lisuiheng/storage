package cn.com.kxcomm.storage.domain.manager;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@ChannelHandler.Sharable
public class BackendHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(BackendHandler.class);
    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();

    private Channel inboundChannel;

    void setInboundChannel(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if(inboundChannel == null) {
            throw new RuntimeException("inboundChannel can not null, please setInboundChannel");
        }
        ctx.read();
    }

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

    public void putIfAbsent(Request request) {
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
    }

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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        FrontendHandler.closeOnFlush(inboundChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        FrontendHandler.closeOnFlush(ctx.channel());
    }
}