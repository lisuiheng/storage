package cn.com.kxcomm.client;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.common.resourse.file.impl.FileImpl;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProvideTest {
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
    public void uploadDownload() throws IOException, StorageException {
        File file = new File(dir, name);
        Long fileViewCode = fileProvide.upload(file, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);

        Path targetPath = Paths.get(dir, "2.txt");
        fileProvide.downLoad(fileViewCode, targetPath, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
    }
}
