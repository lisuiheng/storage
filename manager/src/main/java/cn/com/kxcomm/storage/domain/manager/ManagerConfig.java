package cn.com.kxcomm.storage.domain.manager;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Data
@Component
@ConfigurationProperties(prefix = "manager.config")
public class ManagerConfig {
    private int port;
    private int proxyStartPort;
    private String proxyHost;
    private int proxyPort;
    private InetSocketAddress proxyAddress;

    public InetSocketAddress getProxyAddress() {
        if(proxyAddress == null) {
            this.proxyAddress = new InetSocketAddress(proxyHost, proxyPort);
        }
        return proxyAddress;
    }
}
