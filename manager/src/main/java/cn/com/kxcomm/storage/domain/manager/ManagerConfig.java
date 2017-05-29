package cn.com.kxcomm.storage.domain.manager;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
