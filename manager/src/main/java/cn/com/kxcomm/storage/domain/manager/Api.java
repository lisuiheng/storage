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
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveResponse1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


/**
 * @class Api
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

    /**
     * @method Handel upload md 5 request upload md 5 response 1.
     * @description
     * @author 李穗恒
     * @return the upload md 5 response 1
     * @param uploadMD5Request1 the upload md 5 request 1
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private UploadMD5Response1 handelUploadMD5Request(UploadMD5Request1 uploadMD5Request1) {
        String md5 = uploadMD5Request1.getMd5();
        Long headCorpId = uploadMD5Request1.getHeadCorpId();
        FileModel fileModel = fileService.getByMd5(md5, headCorpId);
        if(fileModel == null) {
            return new UploadMD5Response1(SysSequenceUtil.getSequenceId(Constants.SEQUENCE_ID_CODE_FILE_VIEW_CODE), false, uploadMD5Request1);
        }

        Long fileId = fileModel.getId();
        String fileName = uploadMD5Request1.getFileName();
        long storageCount = uploadMD5Request1.getStorageCount();
        Long loginOperId = uploadMD5Request1.getLoginOperId();
        String sysCode = uploadMD5Request1.getSysCode();
        FileViewModel fileViewModel = fileViewService.add(fileId, fileName, null, headCorpId, loginOperId, sysCode);
        return new UploadMD5Response1(Long.parseLong(fileViewModel.getCode()), true, uploadMD5Request1);
    }

    /**
     * @method Handel pre upload request pre upload request 2.
     * @description
     * @author 李穗恒
     * @return the pre upload request 2
     * @param preUploadRequest1 the pre upload request 1
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private PreUploadRequest2 handelPreUploadRequest(PreUploadRequest1 preUploadRequest1) {
        return new PreUploadRequest2(preUploadRequest1);
    }

    /**
     * @method Handel pre upload response pre upload response 1.
     * @description
     * @author 李穗恒
     * @return the pre upload response 1
     * @param preUploadResponse2 the pre upload response 2
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private PreUploadResponse1 handelPreUploadResponse(PreUploadResponse2 preUploadResponse2) {
        long storageId = preUploadResponse2.getStorageId();
        int uploadPort = preUploadResponse2.getUploadPort();
        int managerPort = connectPool.getManagerPort(uploadPort);
        return new PreUploadResponse1(managerPort, storageId, preUploadResponse2);
    }

    /**
     * @method Handel upload info request upload info response 1.
     * @description
     * @author 李穗恒
     * @return the upload info response 1
     * @param uploadInfoRequest1 the upload info request 1
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private UploadInfoResponse1 handelUploadInfoRequest(UploadInfoRequest1 uploadInfoRequest1) {
        String fileName = uploadInfoRequest1.getFileName();
        long size = uploadInfoRequest1.getSize();
        String md5 = uploadInfoRequest1.getMd5();
        long storageCount = uploadInfoRequest1.getStorageCount();
        long fileViewCode = uploadInfoRequest1.getFileViewCode();
        long storageId = uploadInfoRequest1.getStorageId();
        String path = uploadInfoRequest1.getPath();
        fileService.add(path, size, md5, storageCount, storageId, fileName, String.valueOf(fileViewCode), uploadInfoRequest1.getHeadCorpId(), uploadInfoRequest1.getLoginOperId(), uploadInfoRequest1.getSysCode());
        return new UploadInfoResponse1(uploadInfoRequest1);
    }

    /**
     * @method Handel pre download request pre download request 2.
     * @description
     * @author 李穗恒
     * @return the pre download request 2
     * @param preDownloadRequest1 the pre download request 1
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private PreDownloadRequest2 handelPreDownloadRequest(PreDownloadRequest1 preDownloadRequest1) {
        Long headCorpId = preDownloadRequest1.getHeadCorpId();
        Long fileViewCode = preDownloadRequest1.getFileViewCode();
        FileViewModel fileViewModel = fileViewService.getByViewCode(fileViewCode.toString(), headCorpId);
        FileAddrModel fileAddrModel = fileAddrService.getByFileId(fileViewModel.getFileId(), preDownloadRequest1.getHeadCorpId());
        Long storagePositionId = fileAddrModel.getStoragePositionId();
        String path = fileAddrModel.getPath();
        return new PreDownloadRequest2(storagePositionId, path, preDownloadRequest1);
    }

    /**
     * @method Handel pre download response pre download response 1.
     * @description
     * @author 李穗恒
     * @return the pre download response 1
     * @param preDownloadResponse2 the pre download response 2
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private PreDownloadResponse1 handelPreDownloadResponse(PreDownloadResponse2 preDownloadResponse2) {
        return new PreDownloadResponse1(preDownloadResponse2);
    }


    /**
     * @method Handel remove remove response 1.
     * @description
     * @author 李穗恒
     * @return the remove response 1
     * @param removeRequest1 the remove request 1
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private RemoveResponse1 handelRemove(RemoveRequest1 removeRequest1) {
        long fileViewCode = removeRequest1.getFileViewCode();
        Long headCorpId = removeRequest1.getHeadCorpId();
        Long loginOperId = removeRequest1.getLoginOperId();
        fileViewService.delete(fileViewCode, headCorpId, loginOperId);
        return new RemoveResponse1(removeRequest1);
    }

    /**
     * @method Handel.
     * @description
     * @author 李穗恒
     * @param request the request
     * @param outboundChannel the outbound channel
     * @param inboundChannel the inbound channel
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    void handel(Request request, Channel outboundChannel, Channel inboundChannel) {
        log.debug("handel Request {}", request);
        CompletableFuture.supplyAsync(() -> {
            if (outboundChannel.isActive()) {
                Response inboundResponse = null;
                Request outboundRequest = null;
                if(request instanceof UploadMD5Request1) {
                    inboundResponse = handelUploadMD5Request((UploadMD5Request1) request);
                } else if(request instanceof UploadInfoRequest1) {
                    inboundResponse = handelUploadInfoRequest((UploadInfoRequest1) request);
                } else if(request instanceof PreUploadRequest1) {
                    outboundRequest = handelPreUploadRequest((PreUploadRequest1) request);
                } else if(request instanceof PreDownloadRequest1) {
                    outboundRequest = handelPreDownloadRequest((PreDownloadRequest1) request);
                } else if(request instanceof RemoveRequest1) {
                    inboundResponse = handelRemove((RemoveRequest1) request);
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
                if(response instanceof PreUploadResponse2) {
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
