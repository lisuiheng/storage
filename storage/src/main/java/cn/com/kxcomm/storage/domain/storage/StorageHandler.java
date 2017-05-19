package cn.com.kxcomm.storage.domain.storage;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse2;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse2;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
@ChannelHandler.Sharable
public class StorageHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(StorageHandler.class);
    private final Api api;

    public StorageHandler(Api api) {
        this.api = api;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.debug("channel {} {} {} receive", ctx.channel().localAddress(), msg.getClass().getName(), msg);
        if(msg instanceof Request) {
            Request request = (Request) msg;
            CompletableFuture.supplyAsync(() -> {
                Response response = null;
                try {
                    if(msg instanceof UploadRequest2) {
                        response = api.upload((UploadRequest2) msg);
                    } else if(msg instanceof DownloadRequest3) {
                        response = api.download((DownloadRequest3) msg);
                    } else if(msg instanceof SpaceRequest) {
                        response = api.space((SpaceRequest)msg);
                    } else if(msg instanceof ListFileRequest) {
                        api.listFile((ListFileRequest) msg);
                    }
                    else {
                        //TODO test
                        response = new Response(request);
                    }
                } catch (Throwable t) {
                    throw new RuntimeException(t);
                }
                return response;
            }).thenAcceptAsync(ctx::writeAndFlush).exceptionally(t -> {
                log.error("Unexpected error {}", t);
                ctx.writeAndFlush(new DownloadResponse2(t, request));
                return null;
            });


        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
