package cn.com.kxcomm.common.resourse.file.impl;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.Config;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants;
import cn.com.kxcomm.storage.domain.storage.common.utils.MD5Util;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class FileImpl implements FileProvide {
    private final Logger log = LoggerFactory.getLogger(FileImpl.class);

    private final ClientApi managerClient = new ClientApi();
    private final ConcurrentHashMap<Integer, ClientApi> transportClientMap = new ConcurrentHashMap<>();
    private final ExecutorService uploadAsyncExecutor = Executors.newFixedThreadPool(Config.getUploadAsyncExecutorSize());

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
        String fileName = path.getFileName().toString();
        byte[] data = Files.readAllBytes(path);
        String md5 = MD5Util.md5(data);
        UploadMD5Request uploadMD5Request = new UploadMD5Request(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response uploadMD5Response = (UploadMD5Response) managerClient.send(uploadMD5Request);
        Long fileViewCode = uploadMD5Response.getFileViewCode();

        if(!uploadMD5Response.isFileExit()) {
            uploadFile(fileName, md5, data, fileViewCode, storageCount, loginOperId, headCorpId, sysCode);
        }
        return fileViewCode;
    }

    @Override
    public Long uploadAsync(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return uploadAsync(Paths.get(file.getPath()), ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public Long uploadAsync(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return uploadAsync(path, ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public Long uploadAsync(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return uploadAsync(Paths.get(file.getPath()), storageCount, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public Long uploadAsync(Path path, final Long storageCount, final Long loginOperId, final Long headCorpId, String platformCode, String platformKey, final String sysCode, String sysKey) throws IOException, StorageException {
        final String fileName = path.getFileName().toString();
        final byte[] data = Files.readAllBytes(path);
        final String md5 = MD5Util.md5(data);
        UploadMD5Request uploadMD5Request = new UploadMD5Request(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response uploadMD5Response = (UploadMD5Response) managerClient.send(uploadMD5Request);
        final Long fileViewCode = uploadMD5Response.getFileViewCode();

        if(!uploadMD5Response.isFileExit()) {
            uploadAsyncExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        uploadFile(fileName, md5, data, fileViewCode, storageCount, loginOperId, headCorpId, sysCode);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            });
        }
        return fileViewCode;
    }

    @Override
    public Long uploadByMD5(String fileName, String md5, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException {
        UploadMD5Request uploadMD5Request = new UploadMD5Request(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response uploadMD5Response = (UploadMD5Response) managerClient.send(uploadMD5Request);
        return uploadMD5Response.getFileViewCode();
    }

    private void uploadFile(String fileName, String md5, byte[] data, long fileViewCode, Long storageCount, Long loginOperId, Long headCorpId, String sysCode) throws IOException, StorageException {
        PreUploadRequest1 preUploadRequest1 = new PreUploadRequest1(data.length, headCorpId, loginOperId, sysCode);
        PreUploadResponse1 preUploadResponse1 = (PreUploadResponse1) managerClient.send(preUploadRequest1);
        int uploadPort = preUploadResponse1.getUploadPort();
        UploadRequest3 uploadRequest3 = new UploadRequest3(fileName, data, headCorpId, loginOperId, sysCode);
        UploadResponse3 uploadResponse3 = (UploadResponse3) transportClient(uploadPort).send(uploadRequest3);
        long storageId = preUploadResponse1.getStorageId();
        String storagePath = uploadResponse3.getRelativePath();
        UploadInfoRequest uploadInfoRequest = new UploadInfoRequest(fileName, data.length, md5, storageCount, fileViewCode, storageId, storagePath, uploadRequest3);
        managerClient.send(uploadInfoRequest);
    }

    @Override
    public byte[] downLoad(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException {
        PreDownloadRequest1 preDownloadRequest1 = new PreDownloadRequest1(fileCode, headCorpId, loginOperId, sysCode);
        PreDownloadResponse1 preDownloadResponse1 = (PreDownloadResponse1) managerClient.send(preDownloadRequest1);

        int downloadPort = preDownloadResponse1.getDownloadPort();
        String path = preDownloadResponse1.getPath();

        DownloadRequest3 downloadRequest3 = new DownloadRequest3(path, new Request(headCorpId, loginOperId, sysCode));
        DownloadResponse3 downloadResponse3 = (DownloadResponse3) transportClient(downloadPort).send(downloadRequest3);
        return downloadResponse3.getData();
    }

    @Override
    public void downLoad(Long fileCode, Path targetPath, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException {
        byte[] data = downLoad(fileCode, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
        Files.write(targetPath, data);
    }

    private ClientApi transportClient(int port) {
        ClientApi clientApi = transportClientMap.get(port);
        if(clientApi == null) {
            clientApi = new ClientApi(Config.getRemoteHostname(), port);
            transportClientMap.putIfAbsent(port, clientApi);
        }
        return clientApi;
    }

    @Override
    public void deleteFileByCode(Long fileViewCode, Long loginOperId, Long headCorpId,
                                 String platformCode, String platformKey, String sysCode,
                                 String sysKey) throws StorageException {
        RemoveRequest removeRequest = new RemoveRequest(fileViewCode, headCorpId, loginOperId, sysCode);
        managerClient.send(removeRequest);
    }
}
