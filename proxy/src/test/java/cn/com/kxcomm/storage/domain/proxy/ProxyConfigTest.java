package cn.com.kxcomm.storage.domain.proxy;


import cn.com.kxcomm.storage.domain.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProxyConfigTest {
    @Autowired
    private ProxyConfig proxyConfig;

    @Test
    public void config() {
        assertNotNull(proxyConfig);
        assertNotEquals(0, proxyConfig.getPort());
    }
}