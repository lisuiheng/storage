package cn.com.kxcomm.client;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse1;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
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
    public void download() throws StorageException {
        DownloadRequest1 downloadRequest1 = new DownloadRequest1(1, headCorpId, loginOperId, sysCode);
        DownloadResponse1 downloadResponse1 = (DownloadResponse1) clientApi.send(downloadRequest1);
        assertNotNull(downloadResponse1.getData());
    }
}
