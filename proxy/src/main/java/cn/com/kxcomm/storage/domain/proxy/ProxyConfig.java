package cn.com.kxcomm.storage.domain.proxy;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "proxy.config")
public class ProxyConfig {
    private final Logger log = LoggerFactory.getLogger(ProxyConfig.class);


    private int port;
    private int proxyStartPort;
    private long timeout;


}
