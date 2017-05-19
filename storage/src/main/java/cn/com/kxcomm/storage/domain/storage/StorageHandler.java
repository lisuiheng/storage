package cn.com.kxcomm.storage.domain.storage;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse2;
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
                        UploadRequest2 uploadRequest2 = (UploadRequest2) msg;
                        response = api.upload(uploadRequest2);
                    } else if(msg instanceof DownloadRequest3) {
                        DownloadRequest3 downloadRequest3 = (DownloadRequest3) msg;
                        response = api.download(downloadRequest3);
                    } else if(msg instanceof SpaceRequest) {
                        SpaceRequest spaceRequest = (SpaceRequest) msg;
                        response = api.space(spaceRequest);
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
