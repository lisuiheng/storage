package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static cn.com.kxcomm.storage.domain.storage.common.constants.Constants.SYSTEM_HEAD_CORP_ID;

@Component
@Getter
public class ProxyClientManager {

    private final Map<Long ,SocketAddress> storageRemoteAddressMap;
    private final Map<Long, SocketAddress> storageLocalAddressMap = new HashMap<>();
    private final Map<Long, ClientApi> storageLocalClientMap = new HashMap<>();



    private final ProxyConfig proxyConfig;

    public ProxyClientManager(FileServerService fileServer, ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
        final int[] proxyStartPort = {this.proxyConfig.getProxyStartPort()};
        storageRemoteAddressMap = fileServer.getStorageAddressMap(SYSTEM_HEAD_CORP_ID);
        storageRemoteAddressMap.keySet().forEach(storageId -> {
            InetSocketAddress localAddress = new InetSocketAddress("localhost", proxyStartPort[0]++);
            storageLocalAddressMap.put(storageId, localAddress);
            storageLocalClientMap.put(storageId, new ClientApi(localAddress));
        });
    }



}
