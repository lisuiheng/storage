package cn.com.kxcomm.storage.domain.proxy;


import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse2;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.*;

@Component
@ChannelHandler.Sharable
public class FrontendHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(FrontendHandler.class);

    private final ProxyConfig proxyConfig;

    private final static Map<SocketAddress, Channel> outboundChannelMap = new HashMap<>();
    private ArrayList<SocketAddress> outboundChannelKeys;
    private final Random random = new Random();


    @Autowired
    public FrontendHandler(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        final Channel inboundChannel = ctx.channel();
        proxyConfig.getStorageMap().forEach((address, storage) -> {
            // Start the connection attempt.
            Bootstrap b1 = new Bootstrap();
            b1.group(inboundChannel.eventLoop())
                    .channel(ctx.channel().getClass())
//                    .handler(new BackendHandler(inboundChannel))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new BackendHandler(inboundChannel));
                        }
                    })
                    .option(ChannelOption.AUTO_READ, false);
            ;


            ChannelFuture f1 = b1.connect(address);
            log.info("proxy channel *:8006 to {}, upload limit size is {}", address, storage.getFileUplimit());
            Channel outboundChannel = f1.channel();
            putOutboundChannel(address, outboundChannel);
            f1.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    // connection complete start to read first data
                    inboundChannel.read();
                } else {
                    // Close the connection if the connection attempt has failed.
                    inboundChannel.close();
                }
            });

        });

        outboundChannelKeys = new ArrayList<>(outboundChannelMap.keySet());
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        log.info("HexDumpProxyFrontendHandler read");

        Channel outboundChannel;
        if(msg instanceof TransportRequest) {
            TransportRequest request = (TransportRequest) msg;
            outboundChannel = getOutboundChannel(request.getTargetAddress());
        } else {
            outboundChannel = getOutboundChannel();
        }


        if (outboundChannel.isActive()) {
            //upload limit
            if(msg instanceof UploadRequest2) {
                FileStoragePrositionModel storage = proxyConfig.getStorageMap().get(outboundChannel.remoteAddress());
                Long uplimit = storage.getFileUplimit();
                UploadRequest2 uploadRequest2 = (UploadRequest2) msg;
                int uploadSize = uploadRequest2.getData().length;
                if(uploadSize > uplimit) {
                    String errMsg = String.format("uploadsize:%d is over upload limit:%d", uploadSize, uplimit);
                    log.error(errMsg);
                    ctx.writeAndFlush(new Response(new RuntimeException(errMsg), uploadRequest2));
                }
            }
            outboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    // was able to flush out data, start to read the next chunk
                    ctx.channel().read();
                } else {
                    future.channel().close();
                }
            });
        } else {
            log.info("outboundChannel is not Active");
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        for (Channel outboundChannel : outboundChannelMap.values()) {
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


    private Channel getOutboundChannel() {
        return getOutboundChannel(outboundChannelKeys.get(random.nextInt(outboundChannelKeys.size())));
    }

    private Channel getOutboundChannel(SocketAddress socketAddress) {
        return outboundChannelMap.get(socketAddress);
    }

    private void putOutboundChannel(SocketAddress socketAddress, Channel channel) {
        outboundChannelMap.put(socketAddress, channel);
    }

}