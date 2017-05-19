package cn.com.kxcomm.client;

import cn.com.kxcomm.common.resourse.file.Api;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse2;
import org.junit.Test;

import java.io.IOException;
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

    @Test
    public void uploadDownload() throws IOException {
        Path filePath = Paths.get(dir, name);
        byte[] data = Files.readAllBytes(filePath);
        UploadRequest2 uploadRequest2 = new UploadRequest2(name, data, headCorpId, loginOperId, sysCode);
        UploadResponse2 uploadResponse2 = (UploadResponse2)Api.send(uploadRequest2);
        String md5 = uploadResponse2.getMd5();
        String path = uploadResponse2.getPath();
        long size = uploadResponse2.getSize();
        long storageId = uploadResponse2.getStorageId();
        assertNotNull(md5);
        assertNotNull(path);
        assertNotNull(size);
        assertNotNull(storageId);

        assertEquals(data.length, size);

        DownloadRequest3 downloadRequest3 = new DownloadRequest3(path, headCorpId, loginOperId, sysCode);
        DownloadResponse3 downloadResponse3 = (DownloadResponse3)Api.send(downloadRequest3);
        assertArrayEquals(data, downloadResponse3.getData());
    }

    @Test
    public void space() {
        SpaceRequest request = new SpaceRequest("", headCorpId, loginOperId, sysCode);
        Response response = Api.send(request);
        assertNull(response.getThrowable());
    }

}
