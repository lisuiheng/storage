package cn.com.kxcomm.client;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.Assert.*;

public class ProxyTest {
    private final String dir = "/home/lee/workspace/java/test/storage/clienttest/src/main/resources";
    private final String name = "1.txt";

    private long headCorpId = 1L;
    protected long loginOperId = 1L;
    protected String sysCode = "coms";

//    private ClientApi clientApi = new ClientApi(new InetSocketAddress("172.16.103.200", 8006));
    private ClientApi clientApi = new ClientApi(new InetSocketAddress("testweb.dev.kxcomm.com", 8300));


    @Test(expected = StorageException.class)
    public void transport() throws StorageException {
        Response response = clientApi.send(new Request(headCorpId, loginOperId, sysCode));
        assertNotNull(response);
    }


    @Test
    public void connect() throws StorageException {
        ConnectRequest connectRequest = new ConnectRequest(headCorpId, loginOperId, sysCode);
        ConnectResponse connectResponse = (ConnectResponse) clientApi.send(connectRequest);
        assertNotNull(connectResponse.getPorts());
    }

    @Test
    public void preUpload() throws StorageException {
        PreUploadRequest2 preUploadRequest2 = new PreUploadRequest2(new PreUploadRequest1(1, headCorpId, loginOperId, sysCode));
        PreUploadResponse2 preUploadResponse2 = (PreUploadResponse2) clientApi.send(preUploadRequest2);
        assertNotEquals(0, preUploadResponse2.getStorageId());
        assertNotEquals(0, preUploadResponse2.getUploadPort());
    }

    @Test
    public void upload() throws StorageException, IOException {
        UploadRequest3 uploadRequest3 = new UploadRequest3(name, Files.readAllBytes(Paths.get(dir, name)), headCorpId, loginOperId, sysCode);
        UploadResponse3 uploadResponse3 = (UploadResponse3) clientApi.send(uploadRequest3);
        assertNotNull(uploadRequest3.getFileName());
    }
}
