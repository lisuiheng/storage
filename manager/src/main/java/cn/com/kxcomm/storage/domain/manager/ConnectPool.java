package cn.com.kxcomm.storage.domain.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @class Connect pool
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Component
public class ConnectPool {
    private final Map<Integer, Integer> managerProxyPortMap = new HashMap<>();

    /**
     * @method Put manager proxy port.
     * @description
     * @author 李穗恒
     * @param managerPort the manager port
     * @param proxyPort the proxy port
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public void putManagerProxyPort(int managerPort, int proxyPort) {
        managerProxyPortMap.put(proxyPort, managerPort);
    }

    /**
     * @method Get manager port int.
     * @description
     * @author 李穗恒
     * @return the int
     * @param proxyPort the proxy port
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public int getManagerPort(int proxyPort) {
        return managerProxyPortMap.get(proxyPort);
    }
}
