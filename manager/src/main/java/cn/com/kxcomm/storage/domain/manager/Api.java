package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.common.utils.SysSequenceUtil;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.service.common.constants.Constants;
import cn.com.kxcomm.storage.domain.service.file.model.FileModel;
import cn.com.kxcomm.storage.domain.service.file.service.FileService;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import cn.com.kxcomm.storage.domain.service.view.model.FileViewModel;
import cn.com.kxcomm.storage.domain.service.view.service.FileViewService;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
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

    private final ManBackendHandler manBackendHandler;
    private final FileAddrService fileAddrService;
    private final FileServerService fileServerService;
    private final FileViewService fileViewService;
    private final FileService fileService;

    private final ConnectPool connectPool;

    public Api(ManBackendHandler manBackendHandler, FileAddrService fileAddrService, FileServerService fileServerService, FileViewService fileViewService, FileService fileService, ConnectPool connectPool) {
        this.manBackendHandler = manBackendHandler;
        this.fileAddrService = fileAddrService;
        this.fileServerService = fileServerService;
        this.fileViewService = fileViewService;
        this.fileService = fileService;
        this.connectPool = connectPool;
    }


//    private CheckFileExistResponse checkFileExist(CheckFileExistRequest request) {
//        String md5 = request.getMd5();
//        String fileName = request.getFileName();
//        Long headCorpId = request.getHeadCorpId();
//        Long loginOperId = request.getLoginOperId();
//        String sysCode = request.getSysCode();
//
//        FileModel fileModel = fileService.getByMd5(md5, headCorpId);
//        String fileViewCode = null;
//        if(fileModel != null) {
//            FileViewModel fileViewModel = fileViewService.add(fileModel.getId(), fileName, headCorpId, loginOperId, sysCode);
//            fileViewCode = fileViewModel.getCode();
//        }
//        return new CheckFileExistResponse(fileViewCode, request);
//    }

    private UploadMD5Response handelUploadMD5Request(UploadMD5Request uploadMD5Request) {
        String md5 = uploadMD5Request.getMd5();
        Long headCorpId = uploadMD5Request.getHeadCorpId();
        FileModel fileModel = fileService.getByMd5(md5, headCorpId);
        if(fileModel == null) {
            return new UploadMD5Response(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_VIEW_CODE), false, uploadMD5Request);
        }

        Long fileId = fileModel.getId();
        String fileName = uploadMD5Request.getFileName();
        long storageCount = uploadMD5Request.getStorageCount();
        Long loginOperId = uploadMD5Request.getLoginOperId();
        String sysCode = uploadMD5Request.getSysCode();
        FileViewModel fileViewModel = fileViewService.add(fileId, fileName, null, headCorpId, loginOperId, sysCode);
        return new UploadMD5Response(Long.parseLong(fileViewModel.getCode()), true, uploadMD5Request);
    }

    private PreUploadRequest2 handelPreUploadRequest(PreUploadRequest1 preUploadRequest1) {
        return new PreUploadRequest2(preUploadRequest1);
    }

    private PreUploadResponse1 handelPreUploadResponse(PreUploadResponse2 preUploadResponse2) {
        long storageId = preUploadResponse2.getStorageId();
        int uploadPort = preUploadResponse2.getUploadPort();
        int managerPort = connectPool.getManagerPort(uploadPort);
        return new PreUploadResponse1(managerPort, storageId, preUploadResponse2);
    }

    private UploadInfoResponse handelUploadInfoRequest(UploadInfoRequest uploadInfoRequest) {
        String fileName = uploadInfoRequest.getFileName();
        long size = uploadInfoRequest.getSize();
        String md5 = uploadInfoRequest.getMd5();
        long storageCount = uploadInfoRequest.getStorageCount();
        long fileViewCode = uploadInfoRequest.getFileViewCode();
        long storageId = uploadInfoRequest.getStorageId();
        String path = uploadInfoRequest.getPath();
        fileService.add(path, size, md5, storageCount, storageId, fileName, String.valueOf(fileViewCode), uploadInfoRequest.getHeadCorpId(), uploadInfoRequest.getLoginOperId(), uploadInfoRequest.getSysCode());
        return new UploadInfoResponse(uploadInfoRequest);
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
        String fileViewCode = fileService.add(path, size, md5, storageCount, storageId, fileName, null, uploadRequest1.getHeadCorpId(), uploadRequest1.getLoginOperId(), uploadRequest1.getSysCode());
        return new UploadResponse1(Long.parseLong(fileViewCode), uploadRequest1);
    }

    private PreDownloadRequest2 handelPreDownloadRequest(PreDownloadRequest1 preDownloadRequest1) {
        Long headCorpId = preDownloadRequest1.getHeadCorpId();
        Long fileViewCode = preDownloadRequest1.getFileViewCode();
        FileViewModel fileViewModel = fileViewService.getByViewCode(fileViewCode.toString(), headCorpId);
        FileAddrModel fileAddrModel = fileAddrService.getByFileId(fileViewModel.getFileId(), preDownloadRequest1.getHeadCorpId());
        Long storagePositionId = fileAddrModel.getStoragePositionId();
        String path = fileAddrModel.getPath();
        return new PreDownloadRequest2(storagePositionId, path, preDownloadRequest1);
    }

    private PreDownloadResponse1 handelPreDownloadResponse(PreDownloadResponse2 preDownloadResponse2) {
        return new PreDownloadResponse1(preDownloadResponse2);
    }

    private DownloadRequest2 handelDownloadRequest(DownloadRequest1 downloadRequest1) {
        Long headCorpId = downloadRequest1.getHeadCorpId();
        Long fileViewCode = downloadRequest1.getFileViewCode();
        FileViewModel fileViewModel = fileViewService.getByViewCode(fileViewCode.toString(), headCorpId);
        Long fileId = fileViewModel.getFileId();
        return new DownloadRequest2(fileId, downloadRequest1);
    }

    private DownloadResponse1 handelDownloadResponse(DownloadResponse2 downloadResponse2, DownloadRequest1 downloadRequest1) {
        Long headCorpId = downloadRequest1.getHeadCorpId();
        Long fileViewCode = downloadRequest1.getFileViewCode();
        FileViewModel fileViewModel = fileViewService.getByViewCode(fileViewCode.toString(), headCorpId);
        return new DownloadResponse1(fileViewModel.getName() ,downloadResponse2);
    }



    void handel(Request request, Channel outboundChannel, Channel inboundChannel) {
        log.debug("handel Request {}", request);
        CompletableFuture.supplyAsync(() -> {
            if (outboundChannel.isActive()) {
                Response inboundResponse = null;
                Request outboundRequest = null;
                if(request instanceof  UploadMD5Request) {
                    inboundResponse = handelUploadMD5Request((UploadMD5Request) request);
                }  else if(request instanceof UploadRequest1) {
                    outboundRequest = handelUploadRequest((UploadRequest1) request);
                } else if(request instanceof DownloadRequest1) {
                    outboundRequest = handelDownloadRequest((DownloadRequest1) request);
                } else if(request instanceof UploadInfoRequest) {
                    inboundResponse = handelUploadInfoRequest((UploadInfoRequest) request);
                } else if(request instanceof PreUploadRequest1) {
                    outboundRequest = handelPreUploadRequest((PreUploadRequest1) request);
                } else if(request instanceof PreDownloadRequest1) {
                    outboundRequest = handelPreDownloadRequest((PreDownloadRequest1) request);
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
                    return manBackendHandler.getResponse(request);
                }
            }
            return null;
        }).thenAcceptAsync (response -> {
            log.debug("handel responese {}", response);
            if(response != null) {
                if(response instanceof UploadResponse2) {
                    response = handelUploadResponse((UploadResponse2) response, (UploadRequest1) request);
                } else if(response instanceof DownloadResponse2) {
                    response = handelDownloadResponse((DownloadResponse2) response, (DownloadRequest1) request);
                } else if(response instanceof PreUploadResponse2) {
                    response = handelPreUploadResponse((PreUploadResponse2) response);
                } else if(response instanceof PreDownloadResponse2) {
                    response = handelPreDownloadResponse((PreDownloadResponse2) response);
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
