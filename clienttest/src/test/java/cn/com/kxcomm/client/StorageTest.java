package cn.com.kxcomm.client;


import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class StorageTest {
    private final Logger log = LoggerFactory.getLogger(StorageTest.class);

    private final String dir = "/home/lee/workspace/java/test/storage/clienttest/src/main/resources";
    private final String name = "1.txt";
    private long headCorpId = 1L;
    protected long loginOperId = 1L;
    protected String sysCode = "coms";


    private ClientApi clientApi = new ClientApi(new InetSocketAddress("testweb.dev.kxcomm.com", 8007));

    @Test
    public void upload() throws StorageException, IOException {
        Path filePath = Paths.get(dir).resolve(name);
        UploadRequest3 uploadRequest3 = new UploadRequest3(name, Files.readAllBytes(filePath), headCorpId, loginOperId, sysCode);
        Response response = clientApi.send(uploadRequest3);
        assertNull(response.getThrowable() );
    }


    @Test
    public void remove() throws StorageException {
        Path basePath = Paths.get("/home/lee/Downloads/file");
        String relativePath = "20170427/20170427154452318528.txt";
        RemoveRequest3 removeRequest3 = new RemoveRequest3(relativePath, headCorpId, loginOperId, sysCode);
        assertTrue(Files.exists(basePath.resolve(relativePath)));
        clientApi.send(removeRequest3);
        assertFalse(Files.exists(basePath.resolve(relativePath)));
    }

    @Test
    public void uploadDownloadRemove() throws IOException, StorageException {
        //upload
        Path filePath = Paths.get(dir).resolve(name);
        byte[] data = Files.readAllBytes(filePath);
        UploadRequest3 uploadRequest3 = new UploadRequest3(name, data, headCorpId, loginOperId, sysCode);
        UploadResponse3 uploadResponse3 = (UploadResponse3)clientApi.send(uploadRequest3);
        assertNull(uploadResponse3.getThrowable() );

        //Download
        DownloadRequest3 downloadRequest3 = new DownloadRequest3(uploadResponse3.getRelativePath(), uploadRequest3);
        DownloadResponse3 downloadResponse3 = (DownloadResponse3) clientApi.send(downloadRequest3);
        assertArrayEquals(data, downloadResponse3.getData());

        //Remove
        RemoveRequest3 removeRequest3 = new RemoveRequest3(uploadResponse3.getRelativePath(), headCorpId, loginOperId, sysCode);
        RemoveResponse3 removeResponse3 = (RemoveResponse3) clientApi.send(removeRequest3);
        assertNull(removeResponse3.getThrowable());

        //Download check
        downloadRequest3 = new DownloadRequest3(uploadResponse3.getRelativePath(), uploadRequest3);
        try {
            downloadResponse3 = (DownloadResponse3) clientApi.send(downloadRequest3);
        } catch (StorageException e) {
        }
    }
}
