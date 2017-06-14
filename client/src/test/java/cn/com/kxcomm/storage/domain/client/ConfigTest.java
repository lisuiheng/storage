package cn.com.kxcomm.storage.domain.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {
    @Test(expected = ExceptionInInitializerError.class)
    public void testInit() {
        Integer remotePort = Config.getRemotePort();
    }

    @Test
    public void testFileNoProp() {
//        Integer remotePort = Config.getRemotePort();
        String remoteHostname = Config.getRemoteHostname();
    }
}