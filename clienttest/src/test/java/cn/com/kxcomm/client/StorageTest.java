package cn.com.kxcomm.client;


import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class StorageTest {
    private final String dir = "/home/lee/workspace/java/test/storage/clienttest/src/main/resources";
    private final String name = "1.txt";
    private long headCorpId = 1L;
    protected long loginOperId = 1L;
    protected String sysCode = "coms";


    private ClientApi clientApi = new ClientApi(new InetSocketAddress("172.16.103.200", 8007));


    @Test
    public void upadload() throws IOException, StorageException {
        Path filePath = Paths.get(dir, name);
        byte[] data = Files.readAllBytes(filePath);
        UploadRequest3 uploadRequest3 = new UploadRequest3(new UploadRequest2(new UploadRequest1(name, data, headCorpId, loginOperId, sysCode)));
        UploadResponse3 uploadResponse3 = null;
        uploadResponse3 = (UploadResponse3) clientApi.send(uploadRequest3);
        uploadResponse3 = (UploadResponse3) clientApi.send(uploadRequest3);
        String md5 = uploadResponse3.getMd5();
        String path = uploadResponse3.getRelativePath();
        long size = uploadResponse3.getSize();
        assertNotNull(md5);
        assertNotNull(path);
        assertNotNull(size);
    }

//    @Test
//    public void uploadDownload() throws IOException, StorageException {
//        Path filePath = Paths.get(dir, name);
//        byte[] data = Files.readAllBytes(filePath);
//        UploadRequest3 uploadRequest3 = new UploadRequest3(new UploadRequest2(new UploadRequest1(name, data, headCorpId, loginOperId, sysCode)));
//        UploadResponse3 uploadResponse3 = (UploadResponse3) clientApi.send(uploadRequest3);
//        String md5 = uploadResponse3.getMd5();
//        String path = uploadResponse3.getRelativePath();
//        long size = uploadResponse3.getSize();
//        assertNotNull(md5);
//        assertNotNull(path);
//        assertNotNull(size);
//
//        assertEquals(data.length, size);
//
//        DownloadRequest3 downloadRequest3 = new DownloadRequest3(path, new DownloadRequest2(headCorpId, loginOperId, sysCode));
//        DownloadResponse3 downloadResponse3 = (DownloadResponse3) clientApi.send(downloadRequest3);
//        assertArrayEquals(data, downloadResponse3.getData());
//    }

    @Test
    public void space() throws StorageException {
        SpaceRequest request = new SpaceRequest(new InetSocketAddress("loacalhost",8007), headCorpId, loginOperId, sysCode);
        Response response = clientApi.send(request);
        assertNull(response.getThrowable());
    }

//    @Test
//    public void listFile() throws StorageException {
//        ListFileRequest request = new ListFileRequest("", headCorpId, loginOperId, sysCode);
//        ListFileResponse response = (ListFileResponse) clientApi.send(request);
//        assertNull(response.getThrowable());
//    }
}
