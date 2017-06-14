package cn.com.kxcomm.storage.domain.manager;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @class Manager config
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "manager.config")
public class ManagerConfig {
    private String hostname;
    private int port;
    private int proxyStartPort;
    private String proxyHost;
    private int proxyPort;
    private InetSocketAddress proxyAddress;

    /**
     * @method Get proxy address inet socket address.
     * @description
     * @author 李穗恒
     * @return the inet socket address
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public InetSocketAddress getProxyAddress() {
        if(proxyAddress == null) {
            this.proxyAddress = new InetSocketAddress(proxyHost, proxyPort);
        }
        return proxyAddress;
    }
}
