package cn.com.kxcomm.common.resourse.file.impl;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants;
import cn.com.kxcomm.storage.domain.storage.common.utils.MD5Util;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileImpl implements FileProvide {
    private final Logger log = LoggerFactory.getLogger(FileImpl.class);

    private final ClientApi client = new ClientApi();



    @Override
    public Long upload(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return upload(Paths.get(file.getPath()), ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public Long upload(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return upload(path, ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public Long upload(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return upload(Paths.get(file.getPath()), storageCount, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public Long upload(Path path, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        log.debug("upload file path {}", path);
        String fileName = path.getFileName().toString();
        byte[] data = Files.readAllBytes(path);
        UploadMD5Request uploadMD5Request = new UploadMD5Request(fileName , MD5Util.md5(data),storageCount, headCorpId, loginOperId, sysCode);
        UploadMD5Response uploadMD5Response = (UploadMD5Response) client.send(uploadMD5Request);

        Long fileViewCode = uploadMD5Response.getFileViewCode();
        if(fileViewCode == null) {
            UploadRequest1 uploadRequest1 = new UploadRequest1(path.getFileName().toString(), data ,storageCount, headCorpId, loginOperId, sysCode);
            UploadResponse1 uploadResponse1 = (UploadResponse1) client.send(uploadRequest1);
            fileViewCode = uploadResponse1.getFileViewCode();
        }
        return fileViewCode;
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
