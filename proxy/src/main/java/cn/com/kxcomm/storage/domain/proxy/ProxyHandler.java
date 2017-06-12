package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.PreDownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.PreUploadRequest2;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @class Proxy handler
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
public class ProxyHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(ProxyHandler.class);

    private final Api api;

    public ProxyHandler(Api api) {
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
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        log.debug("channel {} {} {} receive", ctx.channel().localAddress(), msg.getClass().getName(), msg);
        if(msg instanceof Request) {
            Request request = (Request) msg;
            CompletableFuture.supplyAsync(() -> {
                try {
                    Response response = new Response(new RuntimeException(String.format("no handle request:(%s) method exit ", request.toString())), request);
                    if(request instanceof ConnectRequest) {
                        response = api.connect((ConnectRequest) request);
                    } else if(request instanceof PreUploadRequest2) {
                        response = api.preUpload((PreUploadRequest2) request);
                    } else if(request instanceof PreDownloadRequest2) {
                        response = api.preDownload((PreDownloadRequest2) request);
                    }
                    ctx.writeAndFlush(response);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                return null;
            }).exceptionally(throwable -> {
                ctx.writeAndFlush(new Response(throwable, request));
                return null;
            });
            ctx.channel().read();
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
