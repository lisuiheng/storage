package cn.com.kxcomm.client;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.common.utils.MD5Util;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest;
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
    public void checkFileExist() throws StorageException {
        String expectFileName = "1.txt";
        String expectMD5 = "202cb962ac59075b964b07152d234b70";
        CheckFileExistRequest request = new CheckFileExistRequest(expectFileName, expectMD5, headCorpId, loginOperId, sysCode);
        CheckFileExistResponse response = (CheckFileExistResponse) clientApi.send(request);
        assertNotNull(response.getFileViewCode());

        request = new CheckFileExistRequest(expectFileName, "", headCorpId, loginOperId, sysCode);
        response = (CheckFileExistResponse) clientApi.send(request);
        assertNull(response.getFileViewCode());
    }

    @Test
    public void upload() throws IOException, StorageException {
        String fileName = "1.txt";
        byte[] data = Files.readAllBytes(Paths.get("/home/lee/workspace/java/test/storage/clienttest/src/main/resources",fileName));
        UploadRequest1 uploadRequest1 = new UploadRequest1(fileName, data, headCorpId, loginOperId, sysCode);
        UploadResponse1 response = (UploadResponse1) clientApi.send(uploadRequest1);
        assertNotNull(response.getFileViewCode());
    }

    @Test
    public void uploadMD5() throws IOException, StorageException {
        String fileName = "1.txt";
        byte[] data = Files.readAllBytes(Paths.get("/home/lee/workspace/java/test/storage/clienttest/src/main/resources",fileName));
        String md5 = MD5Util.md5(data);
        UploadMD5Request uploadMD5Request = new UploadMD5Request(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response uploadMD5Response = (UploadMD5Response) clientApi.send(uploadMD5Request);
        assertNotNull(uploadMD5Request);
    }

    @Test
    public void download() throws StorageException {
        DownloadRequest1 downloadRequest1 = new DownloadRequest1(1, headCorpId, loginOperId, sysCode);
        DownloadResponse1 downloadResponse1 = (DownloadResponse1) clientApi.send(downloadRequest1);
        assertNotNull(downloadResponse1.getData());
        assertNotNull(downloadResponse1.getFileName());
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
        UploadMD5Request uploadMD5Request = new UploadMD5Request(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response uploadMD5Response = (UploadMD5Response) clientApi.send(uploadMD5Request);
        Long fileViewCode = uploadMD5Response.getFileViewCode();

        if(!uploadMD5Response.isFileExit()) {
            PreUploadRequest1 preUploadRequest1 = new PreUploadRequest1(data.length, headCorpId, loginOperId, sysCode);
            PreUploadResponse1 preUploadResponse1 = (PreUploadResponse1) clientApi.send(preUploadRequest1);
            int uploadPort = preUploadResponse1.getUploadPort();
            ClientApi clientApi = new ClientApi("localhost", uploadPort);
            UploadRequest3 uploadRequest3 = new UploadRequest3(fileName, data, headCorpId, loginOperId, sysCode);
            UploadResponse3 uploadResponse3 = (UploadResponse3) clientApi.send(uploadRequest3);

            long storageId = preUploadResponse1.getStorageId();
            String path = uploadResponse3.getRelativePath();
            UploadInfoRequest uploadInfoRequest = new UploadInfoRequest(fileName, data.length, md5, 1, fileViewCode, storageId, path, uploadRequest3);
            ClientApi managerClient = new ClientApi("localhost", 8005);
            UploadInfoResponse uploadInfoResponse = (UploadInfoResponse) managerClient.send(uploadInfoRequest);
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
