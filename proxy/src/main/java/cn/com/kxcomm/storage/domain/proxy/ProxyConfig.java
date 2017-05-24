package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import cn.com.kxcomm.storage.domain.service.storage.model.FileStoragePrositionModel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import static cn.com.kxcomm.storage.domain.storage.common.constants.Constants.SYSTEM_HEAD_CORP_ID;


@Data
@Component
@ConfigurationProperties(prefix = "cn.com.kxcomm.storage.domain.proxy.config")
public class ProxyConfig {
    private final Logger log = LoggerFactory.getLogger(ProxyConfig.class);

    private int port;
    private final Map<SocketAddress, FileStoragePrositionModel> storageMap;
    private final Map<SocketAddress, ClientApi> clientApiMap = new HashMap<>();

    public ProxyConfig(FileServerService fileServer) {
        storageMap = fileServer.getAdressMap(SYSTEM_HEAD_CORP_ID);
        storageMap.keySet().forEach(address -> {
            clientApiMap.put(address, new ClientApi(address));
        });
        log.debug("init storageMap is {}", storageMap);
    }
}
