package cn.com.kxcomm.client;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.common.resourse.file.impl.FileImpl;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.Assert.*;

public class ProvideTest {
    private final Logger log = LoggerFactory.getLogger(ProvideTest.class);

    private final String dir = "/home/lee/workspace/java/test/storage/clienttest/src/main/resources";
    private final String name = "1.txt";

    private long headCorpId = 1L;
    private long loginOperId = 1L;
    private String sysCode = "coms";
    private String platformCode = "";
    private String platformKey = "";
    private String sysKey = "";

    private FileProvide fileProvide = new FileImpl();

    @Test
    public void uploadByMD5() throws StorageException {
        String md5 = "289f6f6ed90914b7a8ee43ba9ebc7f9d";
        String fileName = "3.txt";
        fileProvide.uploadByMD5(fileName, md5, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Test
    public void uploadDownload() throws IOException, StorageException {
        File file = new File(dir, name);
        Long fileViewCode = fileProvide.uploadSyn(file, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);

        Path targetPath = Paths.get(dir, "2.txt");
        fileProvide.downLoad(fileViewCode, targetPath, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
    }

    @Test
    public void upload() throws IOException, StorageException {
        File file = new File(dir, name);
        long start = System.currentTimeMillis();
        Long fileViewCode = fileProvide.uploadSyn(file, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
        assertNotNull(fileViewCode);
        long end = System.currentTimeMillis();
        log.debug("uploadSyn count {}", end - start);
    }

    @Test
    public void uploadAscy() throws IOException, StorageException, InterruptedException {
        File file = new File(dir, name);
        long start = System.currentTimeMillis();
        Long fileViewCode = fileProvide.upload(file, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
        assertNotNull(fileViewCode);
        long end = System.currentTimeMillis();
        log.debug("uploadSyn count {}", end - start);
        Thread.sleep(2000);
    }

    @Test
    public void remove() throws StorageException {
        fileProvide.deleteFileByCode(1496193236545L, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Test
    public void getFileName() throws StorageException {
        String fileName = fileProvide.getFileNameByCode(1496048470202L, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
        assertNotNull(fileName);
    }

    @Test
    public void downLoad() throws IOException, StorageException {
        fileProvide.downLoad(1492833402557890L, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

    @Test
    public void all() throws IOException, StorageException {
        File file = new File(dir, name);
        Path localPath = Paths.get(file.getPath());
        Long fileViewCode = fileProvide.uploadSyn(file, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
        assertNotNull(fileViewCode);

        String fileNameByCode = fileProvide.getFileNameByCode(fileViewCode, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
        assertEquals(name, fileNameByCode);

        String doanloadPath = fileProvide.downLoad(fileViewCode, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
        assertNotNull(doanloadPath);
        assertArrayEquals(Files.readAllBytes(localPath), Files.readAllBytes(Paths.get(doanloadPath)));
    }
}

