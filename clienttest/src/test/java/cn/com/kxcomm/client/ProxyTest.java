package cn.com.kxcomm.client;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse2;
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

    private ClientApi clientApi = new ClientApi(new InetSocketAddress("172.16.103.200", 8006));


    @Test(expected = StorageException.class)
    public void transport() throws StorageException {
        Response response = clientApi.send(new Request(headCorpId, loginOperId, sysCode));
        assertNotNull(response);
    }

    @Test
    public void upload() throws IOException, StorageException {
        Path filePath = Paths.get(dir, name);
        byte[] data = Files.readAllBytes(filePath);
        UploadRequest2 uploadRequest2 = new UploadRequest2(new UploadRequest1(name, data, headCorpId, loginOperId, sysCode));
        UploadResponse2 uploadResponse2 = null;
        uploadResponse2 = (UploadResponse2) clientApi.send(uploadRequest2);
        uploadResponse2 = (UploadResponse2) clientApi.send(uploadRequest2);
        assertNull(uploadResponse2.getThrowable());
    }

    @Test
    public void download() throws StorageException {
        DownloadRequest2 downloadRequest2 = new DownloadRequest2(1, new DownloadRequest1(1, headCorpId, loginOperId, sysCode));
        DownloadResponse2 downloadResponse2 = (DownloadResponse2) clientApi.send(downloadRequest2);
        assertNotNull(downloadResponse2.getData());
    }
}
