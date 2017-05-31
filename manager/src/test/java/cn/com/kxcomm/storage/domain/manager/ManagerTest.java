package cn.com.kxcomm.storage.domain.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ManagerTest {
    @Autowired
    private ManagerConfig managerConfig;

    @Test
    public void config() {
        assertNotNull(managerConfig);
        assertNotEquals(0, managerConfig.getPort());
        assertNotEquals(0, managerConfig.getProxyStartPort());
        assertNotNull(managerConfig.getProxyAddress());
    }
}
