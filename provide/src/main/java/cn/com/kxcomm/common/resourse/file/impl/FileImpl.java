package cn.com.kxcomm.common.resourse.file.impl;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileImpl implements FileProvide {
    private final Logger log = LoggerFactory.getLogger(FileImpl.class);

    private final ClientApi client = new ClientApi();

    @Override
    public Long upload(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        log.debug("upload file {}", file);
        Path path = Paths.get(file.getPath());
        UploadRequest1 uploadRequest1 = new UploadRequest1(file.getName(), Files.readAllBytes(path), headCorpId, loginOperId, sysCode);
        UploadResponse1 uploadResponse1 = (UploadResponse1) client.send(uploadRequest1);
        return uploadResponse1.getFileViewCode();
    }

    @Override
    public byte[] downLoad(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException {
        DownloadRequest1 downloadRequest1 = new DownloadRequest1(fileCode, headCorpId, loginOperId, sysCode);
        DownloadResponse1 downloadResponse1 = (DownloadResponse1) client.send(downloadRequest1);
        return downloadResponse1.getData();
    }

    @Override
    public void downLoad(Long fileCode, Path targetPath, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException {
        byte[] data = downLoad(fileCode, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
        Files.write(targetPath, data);
    }
}
