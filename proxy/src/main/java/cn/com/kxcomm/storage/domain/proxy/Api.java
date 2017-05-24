package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Api {
    private final Logger log = LoggerFactory.getLogger(Api.class);
    
    private final ProxyClientManager proxyClientManager;
    private final FileAddrService fileAddrServicef;

    private final Random random = new Random();
    private final Object[] storageIds;

    public Api(ProxyClientManager proxyClientManager, FileAddrService fileAddrServicef) {
        this.fileAddrServicef = fileAddrServicef;
        this.proxyClientManager = proxyClientManager;
        storageIds = proxyClientManager.getStorageLocalClientMap().keySet().toArray();
    }

    public UploadResponse2 upload(UploadRequest2 uploadRequest2) throws StorageException {
        Long randomStorageId = getRandomStorageId();
        UploadResponse3 uploadResponse3 = (UploadResponse3) getClientApi(randomStorageId).send(new UploadRequest3(uploadRequest2));
        return new UploadResponse2(randomStorageId, uploadResponse3);
    }

    public DownloadResponse2 download(DownloadRequest2 downloadRequest2) throws StorageException {
        long fileId = downloadRequest2.getFileId();
        FileAddrModel fileAddrModel = fileAddrServicef.getByFileId(fileId, downloadRequest2.getHeadCorpId());
        DownloadResponse3 downloadResponse3 = (DownloadResponse3) getClientApi(fileAddrModel.getStoragePositionId()).send(new DownloadRequest3(fileAddrModel.getPath(), downloadRequest2));
        return new DownloadResponse2(downloadResponse3);
    }

    private ClientApi getClientApi(long storageId) {
        return proxyClientManager.getStorageLocalClientMap().get(storageId);
    }

    private Long getRandomStorageId() {
        return (Long) storageIds[random.nextInt(storageIds.length)];
    }


}
