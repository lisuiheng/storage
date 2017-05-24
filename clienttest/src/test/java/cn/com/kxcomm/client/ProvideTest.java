package cn.com.kxcomm.client;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.common.resourse.file.impl.FileImpl;
import org.junit.Test;

import java.io.File;

public class ProvideTest {
    private final String dir = "/home/lee/workspace/java/test/storage/clienttest/src/main/resources";
    private final String name = "1.txt";

    private long headCorpId = 1L;
    protected long loginOperId = 1L;
    protected String sysCode = "coms";

    private FileProvide fileProvide = new FileImpl();

    @Test
    public void uploadDownload() {
//        fileProvide.upload(new File(dir, name))
    }
}
