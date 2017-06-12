package cn.com.kxcomm.storage.domain.manager;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @class Man frontend handler
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
@PropertySource(value = "classpath:manager.properties")
public class ManFrontendHandler extends ChannelInboundHandlerAdapter {
    private final Logger log = LoggerFactory.getLogger(ManFrontendHandler.class);

    @Value("${remote.host}")
    private String remoteHost;
    @Value("${remote.port}")
    private int remotePort;

    private final Api api;
    private final ManBackendHandler manBackendHandler;

    // As we use inboundChannel.eventLoop() when buildling the Bootstrap this does not need to be volatile as
    // the outboundChannel will use the same EventLoop (and therefore Thread) as the inboundChannel.
    private Channel outboundChannel;


    @Autowired
    public ManFrontendHandler(Api api, ManBackendHandler manBackendHandler) {
        this.api = api;
        this.manBackendHandler = manBackendHandler;
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
        final Channel inboundChannel = ctx.channel();

        manBackendHandler.setInboundChannel(inboundChannel);
//         Start the connection attempt.
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                manBackendHandler);
                    }
                })
                .option(ChannelOption.AUTO_READ, false);
        ChannelFuture f = b.connect(remoteHost, remotePort);
        outboundChannel = f.channel();
        f.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                // connection complete start to read first data
                inboundChannel.read();
            } else {
                // Close the connection if the connection attempt has failed.
                inboundChannel.close();
            }
        });
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
        log.debug("frontendHandler receive {} {}", msg.getClass().getName(), msg);
        if(!(msg instanceof Request)) {
            log.error("receive wrong class {}, should be Request", msg.getClass().getName());
        } else {
            api.handel((Request) msg, outboundChannel, ctx.channel());
        }
    }

//    @Transactional
//    private void checkFileExist(CheckFileExistRequest checkFileExistRequest,
//                                Channel inboundChannel, Long headCorpId, Long loginOperId,String sysCode) {
//        CompletableFuture.supplyAsync(() -> {
//            FileVo fileVo = fileManagerService.getFileIsExist(checkFileExistRequest.getMd5(), checkFileExistRequest.getFileSize(), headCorpId);
//            String fileViewCode = null;
//            if(fileVo != null) {
//                fileViewCode = fileManagerService.saveFileView(checkFileExistRequest.getFileName(), fileVo.getStorageCount(), loginOperId, headCorpId, sysCode, fileVo.getId());
//            }
//            return fileViewCode;
//        }).thenAcceptAsync(fileViewCodeStirng -> {
//            Long fileViewCode = null;
//            if(fileViewCodeStirng != null) {
//                fileViewCode = Long.parseLong(fileViewCodeStirng);
//            }
//            CheckFileExistResponse response = new CheckFileExistResponse(fileViewCode, checkFileExistRequest);
//            inboundChannel.writeAndFlush(response)
//                    .addListener((ChannelFutureListener) future -> {
//                        if (future.isSuccess()) {
//                            // was able to flush out data, start to read the next chunk
//                            inboundChannel.read();
//                        } else {
//                            future.channel().close();
//                        }
//                    });
//            log.debug("{} {} handel", response.getClass().getName(), response);
//        }).exceptionally(t -> {
//            log.error("Unexpected error", t);
//            inboundChannel.writeAndFlush(new CheckFileExistResponse(t, checkFileExistRequest));
//            return null;
//        });
//    }
//
//    private void upload(UploadRequest1 uploadRequest1,
//                                Channel inboundChannel, Long headCorpId, Long loginOperId,String sysCode) {
//        CompletableFuture.supplyAsync(() -> {
//            outboundChannelWriteAndFlush(inboundChannel, new UploadRequest2(uploadRequest1));
//            return (UploadResponse2) backendHandler.getResponse(uploadRequest1);
//        }).thenAcceptAsync((uploadResponse2) ->{
//            FileManagerBean fileBean = uploadResponse2.getFileBean();
//            if(fileBean == null) {
//                throw new RuntimeException(uploadResponse2.getThrowable());
//            }
//            FileVo fileVo = fileManagerService.updateUploadMsg(fileBean, uploadRequest1.getHeadCorpId(), uploadRequest1.getLoginOperId());
//            String fileViewCode = fileManagerService.saveFileView(uploadRequest1.getFileName(), fileVo.getStorageCount(), loginOperId, headCorpId, sysCode, fileVo.getId());
//            inboundChannel.writeAndFlush(new UploadResponse1(Long.parseLong(fileViewCode), uploadRequest1))
//                    .addListener((ChannelFutureListener) future -> {
//                        if (future.isSuccess()) {
//                            // was able to flush out data, start to read the next chunk
//                            outboundChannel.read();
//                        } else {
//                            future.channel().close();
//                        }
//                    });
//        }).exceptionally(t -> {
//            log.error("Unexpected error", t);
//            inboundChannel.writeAndFlush(new UploadResponse1(t, uploadRequest1));
//            return null;
//        });
//    }
//
//    private void download(DownloadRequest1 downloadRequest1, Channel inboundChannel, Long headCorpId, Long loginOperId,String sysCode) {
//        CompletableFuture.supplyAsync(() -> {
//            Long fileViewCode = downloadRequest1.getFileViewCode();
//            FileViewModel fileViewModel = fileManagerService.getFileViewByCode(fileViewCode, headCorpId);
//            Long fileId = fileViewModel.getFileViewCode();
//            FileAddrModel fileAddrModel = fileManagerService.getFileAddrByFileId(fileId, headCorpId);
//            FileServerVo fileServerVo = fileServerService.getByKey(fileAddrModel.getServerId(), headCorpId);
//            FileStoragePrositionVo storagePrositionVo = storagePrositionService.getByKey(fileAddrModel.getStoragePositionId(), headCorpId);
//            outboundChannelWriteAndFlush(inboundChannel,
//                    new DownloadRequest2(fileAddrModel.getStorageDir() + "/" + fileAddrModel.getStorageName(),
//                            fileServerVo.getIp(),
//                            storagePrositionVo.getPort(),
//                            downloadRequest1));
//            DownloadResponse2 downloadResponse2 = (DownloadResponse2) backendHandler.getResponse(downloadRequest1);
//
//            //update download info
//            FileManagerBean fileManagerBean = new FileManagerBean();
//            fileManagerBean.setStorageId(storagePrositionVo.getId());
//            fileManagerBean.setSize((long) downloadResponse2.getData().length);
//            fileManagerBean.setFileId(fileId);
//            fileManagerBean.setFileAddrId(fileAddrModel.getId());
//            fileManagerBean.setFileViewId(fileViewModel.getId());
//            fileManagerService.updateDownload(fileManagerBean, headCorpId);
//
//            inboundChannelWriteAndFlush(inboundChannel, new DownloadResponse1(fileViewModel.getName(), downloadResponse2));
//            return null;
//        }).exceptionally(t -> {
//            log.error("Unexpected error", t);
//            inboundChannel.writeAndFlush(new UploadResponse1(t, downloadRequest1));
//            return null;
//        });
//    }
//
//    @Override
//    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
//        final Channel inboundChannel = ctx.channel();
//        log.debug("{} {} receive", msg.getClass().getName(), msg);
//        if(msg instanceof Request) {
//            Long headCorpId = ((Request) msg).getHeadCorpId();
//            Long loginOperId = ((Request) msg).getLoginOperId();
//            String sysCode = ((Request) msg).getSysCode();
//            if (msg instanceof CheckFileExistRequest) {
//                CheckFileExistRequest checkFileExistRequest = (CheckFileExistRequest) msg;
//                checkFileExist(checkFileExistRequest, inboundChannel, headCorpId, loginOperId, sysCode);
//            } else if(msg instanceof UploadRequest1) {
//                UploadRequest1 uploadRequest1 = (UploadRequest1) msg;
//                upload(uploadRequest1, inboundChannel, headCorpId, loginOperId, sysCode);
//            } else if(msg instanceof DownloadRequest1) {
//                DownloadRequest1 downloadRequest1 = (DownloadRequest1) msg;
//                download(downloadRequest1, inboundChannel, headCorpId, loginOperId, sysCode);
//            }
//            else {
//                outboundChannelWriteAndFlush(inboundChannel, msg);
//            }
//
//
//        }
//    }

//    private void inboundChannelWriteAndFlush(Channel inboundChannel, Object msg) {
//        inboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
//            if (future.isSuccess()) {
//                // was able to flush out data, start to read the next chunk
//                outboundChannel.read();
//            } else {
//                future.channel().close();
//            }
//        });
//    }
//
//
//    private void outboundChannelWriteAndFlush(Channel inboundChannel,Object msg) {
//        if (outboundChannel.isActive()) {
//            if(msg instanceof Request) {
//                Request request = (Request) msg;
//                if(msg instanceof UploadRequest1) {
//                    log.debug("upload request1 accept");
//
//                }
//                backendHandler.putIfAbsent(request);
//            }
//
//
//
//            outboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
//                if (future.isSuccess()) {
//                    // was able to flush out data, start to read the next chunk
//                    inboundChannel.read();
//                } else {
//                    future.channel().close();
//                }
//            });
//
//        }
//    }

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
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
        ctx.fireChannelReadComplete();
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
        if (outboundChannel != null) {
            closeOnFlush(outboundChannel);
        }
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
        closeOnFlush(ctx.channel());
    }

    /**
     * @method Close on flush.
     * @description
     * @author 李穗恒
     * @param ch the ch
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}