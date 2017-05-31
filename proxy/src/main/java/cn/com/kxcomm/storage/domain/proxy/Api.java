package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

@Component
public class Api {
    private final Logger log = LoggerFactory.getLogger(Api.class);

    private final ConnectPool connectPool;
    private final FileAddrService fileAddrServicef;

    private final Random random = new Random();
    private final Object[] storageIds;

    public Api(ConnectPool connectPool, FileAddrService fileAddrServicef) {
        this.fileAddrServicef = fileAddrServicef;
        this.connectPool = connectPool;
        storageIds = connectPool.getStorageLocalClientMap().keySet().toArray();
    }

    public PreUploadResponse2 preUpload(PreUploadRequest2 preUploadRequest2) {
        long size = preUploadRequest2.getSize();
        Long randomStorageId = getRandomStorageId();
        InetSocketAddress inetSocketAddress = connectPool.getStorageLocalAddressMap().get(randomStorageId);
        return new PreUploadResponse2(inetSocketAddress.getPort(), randomStorageId,preUploadRequest2);
    }

    public UploadResponse2 upload(UploadRequest2 uploadRequest2) throws StorageException {
        Long randomStorageId = getRandomStorageId();
        UploadResponse3 uploadResponse3 = (UploadResponse3) getClientApi(randomStorageId).send(new UploadRequest3(uploadRequest2));
        return new UploadResponse2(randomStorageId, uploadResponse3);
    }

    PreDownloadResponse2 preDownload(PreDownloadRequest2 preDownloadRequest2) {
        long storageId = preDownloadRequest2.getStorageId();
        InetSocketAddress address = connectPool.getStorageLocalAddressMap().get(storageId);
        return new PreDownloadResponse2(address.getPort(), preDownloadRequest2);
    }

    public DownloadResponse2 download(DownloadRequest2 downloadRequest2) throws StorageException {
        long fileId = downloadRequest2.getFileId();
        FileAddrModel fileAddrModel = fileAddrServicef.getByFileId(fileId, downloadRequest2.getHeadCorpId());
        DownloadResponse3 downloadResponse3 = (DownloadResponse3) getClientApi(fileAddrModel.getStoragePositionId()).send(new DownloadRequest3(fileAddrModel.getPath(), downloadRequest2));
        return new DownloadResponse2(downloadResponse3);
    }

    public ConnectResponse connect(ConnectRequest connectRequest) {
        Collection<InetSocketAddress> localAddress = connectPool.getStorageLocalAddressMap().values();
        int[] ports = new int[localAddress.size()];
        Iterator<InetSocketAddress> iterator = localAddress.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            ports[i] = iterator.next().getPort();
        }
        return new ConnectResponse(ports, connectRequest);
    }


    private ClientApi getClientApi(long storageId) {
        return connectPool.getStorageLocalClientMap().get(storageId);
    }

    private Long getRandomStorageId() {
        return (Long) storageIds[random.nextInt(storageIds.length)];
    }


}
