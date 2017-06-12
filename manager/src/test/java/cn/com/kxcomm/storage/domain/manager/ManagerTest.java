package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.storage.domain.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @class Manager test
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ManagerTest {
    @Autowired
    private ManagerConfig managerConfig;

    /**
     * @method Config.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void config() {
        assertNotNull(managerConfig);
        assertNotEquals(0, managerConfig.getPort());
        assertNotEquals(0, managerConfig.getProxyStartPort());
        assertNotNull(managerConfig.getProxyAddress());
    }
}
