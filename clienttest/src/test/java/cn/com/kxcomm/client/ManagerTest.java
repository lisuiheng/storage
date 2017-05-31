package cn.com.kxcomm.client;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.common.utils.MD5Util;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ManagerTest {
    private final Logger log = LoggerFactory.getLogger(ManagerTest.class);

    private long headCorpId = 1L;
    protected long loginOperId = 1L;
    protected String sysCode = "coms";

    private ClientApi clientApi = new ClientApi(new InetSocketAddress("172.16.103.200", 8005));

    @Test(expected = StorageException.class)
    public void testConnet() throws StorageException {
        Request request = new Request(headCorpId, loginOperId, sysCode);
        Response response = clientApi.send(request);
        log.info(response.toString());
    }




    @Test
    public void uploadMD5() throws IOException, StorageException {
        String fileName = "1.txt";
        byte[] data = Files.readAllBytes(Paths.get("/home/lee/workspace/java/test/storage/clienttest/src/main/resources",fileName));
        String md5 = MD5Util.md5(data);
        UploadMD5Request1 uploadMD5Request1 = new UploadMD5Request1(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response1 uploadMD5Response1 = (UploadMD5Response1) clientApi.send(uploadMD5Request1);
        assertNotNull(uploadMD5Request1);
    }


    @Test
    public void preUpload() throws StorageException {
        PreUploadRequest1 preUploadRequest1 = new PreUploadRequest1(1, headCorpId, loginOperId, sysCode);
        PreUploadResponse1 preUploadResponse1 = (PreUploadResponse1) clientApi.send(preUploadRequest1);
        int uploadPort = preUploadResponse1.getUploadPort();
        assertNotEquals(0, uploadPort);
    }

    @Test
    public void upload2() throws IOException, StorageException {
        long start = System.currentTimeMillis();
        String fileName = "1.txt";
        byte[] data = Files.readAllBytes(Paths.get("/home/lee/workspace/java/test/storage/clienttest/src/main/resources",fileName));
        String md5 = MD5Util.md5(data);
        UploadMD5Request1 uploadMD5Request1 = new UploadMD5Request1(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response1 uploadMD5Response1 = (UploadMD5Response1) clientApi.send(uploadMD5Request1);
        Long fileViewCode = uploadMD5Response1.getFileViewCode();

        if(!uploadMD5Response1.isFileExit()) {
            PreUploadRequest1 preUploadRequest1 = new PreUploadRequest1(data.length, headCorpId, loginOperId, sysCode);
            PreUploadResponse1 preUploadResponse1 = (PreUploadResponse1) clientApi.send(preUploadRequest1);
            int uploadPort = preUploadResponse1.getUploadPort();
            ClientApi clientApi = new ClientApi("localhost", uploadPort);
            UploadRequest3 uploadRequest3 = new UploadRequest3(fileName, data, headCorpId, loginOperId, sysCode);
            UploadResponse3 uploadResponse3 = (UploadResponse3) clientApi.send(uploadRequest3);

            long storageId = preUploadResponse1.getStorageId();
            String path = uploadResponse3.getRelativePath();
            UploadInfoRequest1 uploadInfoRequest1 = new UploadInfoRequest1(fileName, data.length, md5, 1, fileViewCode, storageId, path, uploadRequest3);
            ClientApi managerClient = new ClientApi("localhost", 8005);
            UploadInfoResponse1 uploadInfoResponse1 = (UploadInfoResponse1) managerClient.send(uploadInfoRequest1);
        }
        log.info("time use {}", System.currentTimeMillis() - start);
    }

    @Test
    public void preDownload() throws StorageException {
        PreDownloadRequest1 preDownloadRequest1 = new PreDownloadRequest1(1496048470202L, headCorpId, loginOperId, sysCode);
        PreDownloadResponse1 preDownloadResponse1 = (PreDownloadResponse1) clientApi.send(preDownloadRequest1);

        int downloadPort = preDownloadResponse1.getDownloadPort();
        String path = preDownloadResponse1.getPath();
        assertNotEquals(0, downloadPort);
        assertNotNull(path);

        ClientApi storageClient = new ClientApi("localhost", downloadPort);
        DownloadRequest3 downloadRequest3 = new DownloadRequest3(path, new Request(headCorpId, loginOperId, sysCode));
        DownloadResponse3 downloadResponse3 = (DownloadResponse3) storageClient.send(downloadRequest3);
        assertNotNull(downloadResponse3.getData());
    }


}
