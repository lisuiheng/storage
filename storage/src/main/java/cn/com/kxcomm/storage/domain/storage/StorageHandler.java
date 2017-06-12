package cn.com.kxcomm.storage.domain.storage;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest3;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @class Storage handler
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
public class StorageHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(StorageHandler.class);
    private final Api api;

    public StorageHandler(Api api) {
        this.api = api;
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.debug("channel {} {} {} receive", ctx.channel().localAddress(), msg.getClass().getName(), msg);
        if(msg instanceof Request) {
            Request request = (Request) msg;
            CompletableFuture.supplyAsync(() -> {
                Response response = new Response(new RuntimeException(String.format("no handle request:(%s) method exit ", request.toString())), request);
                try {
                    if(request instanceof UploadRequest3) {
                        response = api.upload((UploadRequest3) request);
                    } else if(request instanceof DownloadRequest3) {
                        response = api.download((DownloadRequest3) request);
                    } else if(request instanceof RemoveRequest3) {
                        response = api.remove((RemoveRequest3) request);
                    } else if(request instanceof SpaceRequest) {
                        response = api.space((SpaceRequest)request);
                    } else if(request instanceof ListFileRequest) {
                        response = api.listFile((ListFileRequest) request);
                    }
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                return response;
            }).thenAcceptAsync(resp -> {
                ctx.writeAndFlush(resp);
                log.debug("writeAndFlush response {}", resp);
            }).exceptionally(t -> {
                log.error("Unexpected error {}", t);
                ctx.writeAndFlush(new Response(t, request));
                return null;
            });


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
     * @since JDK1.8
     * @version 002.00.00
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
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
        ctx.close();
    }
}
