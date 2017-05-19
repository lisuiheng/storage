package cn.com.kxcomm.storage.domain.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse2;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class Api {
    private final FileAgent fileAgent;
    private final StorageConfig storageConfig;

    public Api(FileAgent fileAgent, StorageConfig storageConfig) {
        this.fileAgent = fileAgent;
        this.storageConfig = storageConfig;
    }

    public UploadResponse2 upload(UploadRequest2 request) throws IOException {
        String fileName = request.getFileName();
        byte[] data = request.getData();

        Long storageId = storageConfig.getFileStorageId();
        String path = fileAgent.writeByte(fileName, data);
        long size = data.length;
        String md5 = DigestUtils.md5DigestAsHex(data);

        return new UploadResponse2(storageId, path, size, md5, request);
    }

    public DownloadResponse3 download(DownloadRequest3 request) throws IOException {
        byte[] data;
        String path = request.getPath();
        data = fileAgent.readAllBytes(path);
        return new DownloadResponse3(data, request);
    }

    public SpaceResponse space(SpaceRequest request) {
        String relativePath = request.getRelativePath();
        File dir = new File(storageConfig.getPath().toString(), relativePath);
        return new SpaceResponse(dir.getFreeSpace(), dir.getUsableSpace(), dir.getTotalSpace(), request);
    }
}
