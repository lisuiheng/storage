package cn.com.kxcomm.client;


import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest3;
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


    private ClientApi clientApi = new ClientApi(new InetSocketAddress("127.0.0.1", 8007));

    @Test
    public void remove() throws StorageException {
        Path basePath = Paths.get("/home/lee/Downloads/file");
        String relativePath = "20170427/20170427154452318528.txt";
        RemoveRequest3 removeRequest3 = new RemoveRequest3(relativePath, headCorpId, loginOperId, sysCode);
        assertTrue(Files.exists(basePath.resolve(relativePath)));
        clientApi.send(removeRequest3);
        assertFalse(Files.exists(basePath.resolve(relativePath)));
    }
}
