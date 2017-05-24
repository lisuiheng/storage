package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.service.file.model.FileModel;
import cn.com.kxcomm.storage.domain.service.file.service.FileService;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.service.FileViewService;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
public class Api {
    private final Logger log = LoggerFactory.getLogger(Api.class);

    private final BackendHandler backendHandler;
    private final FileAddrService fileAddrService;
    private final FileServerService fileServerService;
    private final FileViewService fileViewService;
    private final FileService fileService;

    public Api(BackendHandler backendHandler, FileAddrService fileAddrService, FileServerService fileServerService, FileViewService fileViewService, FileService fileService) {
        this.backendHandler = backendHandler;
        this.fileAddrService = fileAddrService;
        this.fileServerService = fileServerService;
        this.fileViewService = fileViewService;
        this.fileService = fileService;
    }


    private CheckFileExistResponse checkFileExist(CheckFileExistRequest request) {
        String md5 = request.getMd5();
        String fileName = request.getFileName();
        Long headCorpId = request.getHeadCorpId();
        Long loginOperId = request.getLoginOperId();
        String sysCode = request.getSysCode();

        FileModel fileModel = fileService.getByMd5(md5, headCorpId);
        String fileViewCode = null;
        if(fileModel != null) {
            FileViewModel fileViewModel = fileViewService.add(fileModel.getId(), fileName, headCorpId, loginOperId, sysCode);
            fileViewCode = fileViewModel.getCode();
        }
        return new CheckFileExistResponse(fileViewCode, request);
    }

    private UploadRequest2 handelUploadRequest(UploadRequest1 request1) {
        return  new UploadRequest2(request1);
    }

    private UploadResponse1 handelUploadResponse(UploadResponse2 uploadResponse2, UploadRequest1 uploadRequest1) {
        String path = uploadResponse2.getRelativePath();
        long size = uploadResponse2.getSize();
        String md5 = uploadResponse2.getMd5();
        long storageId = uploadResponse2.getStorageId();

        String fileName = uploadRequest1.getFileName();
        long storageCount = uploadRequest1.getStorageCount();
        String fileViewCode = fileService.add(path, size, md5, storageCount, storageId, fileName, uploadRequest1.getHeadCorpId(), uploadRequest1.getLoginOperId(), uploadRequest1.getSysCode());
        return new UploadResponse1(Long.parseLong(fileViewCode), uploadRequest1);
    }

    private DownloadRequest2 handelDownloadRequest(DownloadRequest1 downloadRequest1) {
        Long headCorpId = downloadRequest1.getHeadCorpId();
        Long fileViewCode = downloadRequest1.getFileViewCode();
        FileViewModel fileViewModel = fileViewService.getByViewCode(fileViewCode.toString(), headCorpId);
        Long fileId = fileViewModel.getFileId();
        return new DownloadRequest2(fileId, downloadRequest1);
    }

    void handel(Request request, Channel outboundChannel, Channel inboundChannel) {
        log.debug("handel Request {}", request);
        CompletableFuture.supplyAsync(() -> {
            if (outboundChannel.isActive()) {
                Response inboundResponse = null;
                Request outboundRequest = null;
                if(request instanceof CheckFileExistRequest) {
                    inboundResponse = checkFileExist((CheckFileExistRequest) request);
                } else if(request instanceof UploadRequest1) {
                    outboundRequest = handelUploadRequest((UploadRequest1) request);
                } else if(request instanceof DownloadRequest1) {
                    outboundRequest = handelDownloadRequest((DownloadRequest1) request);
                }
                else {
                    outboundRequest = request;
                }

                if(inboundResponse != null) {
                    inboundChannel.writeAndFlush(inboundResponse).addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            // was able to flush out data, start to read the next chunk
                            inboundChannel.read();
                        } else {
                            future.channel().close();
                        }
                    });
                } else {
                    outboundChannel.writeAndFlush(outboundRequest).addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            // was able to flush out data, start to read the next chunk
                            inboundChannel.read();
                        } else {
                            future.channel().close();
                        }
                    });
                    return backendHandler.getResponse(request);
                }
            }
            return null;
        }).thenAcceptAsync (response -> {
            log.debug("handel responese {}", response);
            if(response != null) {
                if(response instanceof UploadResponse2) {
                    response = handelUploadResponse((UploadResponse2) response, (UploadRequest1) request);
                }
                inboundChannel.writeAndFlush(response).addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        outboundChannel.read();
                    } else {
                        future.channel().close();
                    }
                });
            }
        }).exceptionally(throwable -> {
            log.error(throwable.getMessage());
            inboundChannel.writeAndFlush(new Response(throwable, request));
            return null;
        });
    }
}
