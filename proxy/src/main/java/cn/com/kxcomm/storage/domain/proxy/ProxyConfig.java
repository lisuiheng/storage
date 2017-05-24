package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import static cn.com.kxcomm.storage.domain.storage.common.constants.Constants.SYSTEM_HEAD_CORP_ID;


@Data
@Component
@ConfigurationProperties(prefix = "proxy.config")
public class ProxyConfig {
    private final Logger log = LoggerFactory.getLogger(ProxyConfig.class);


    private int port;
    private int proxyStartPort;
    private long timeout;


}
