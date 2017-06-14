package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import static cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants.SYSTEM_HEAD_CORP_ID;

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
@Getter
public class ConnectPool {
    private final Logger log = LoggerFactory.getLogger(ConnectPool.class);

    private final Map<Long ,InetSocketAddress> storageRemoteAddressMap;
    private final Map<Long, InetSocketAddress> storageLocalAddressMap = new HashMap<>();
    private final Map<Long, ClientApi> storageLocalClientMap = new HashMap<>();



    private final ProxyConfig proxyConfig;

    public ConnectPool(FileServerService fileServer, ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
        final int[] proxyStartPort = {this.proxyConfig.getProxyStartPort()};
        storageRemoteAddressMap = fileServer.getStorageAddressMap(SYSTEM_HEAD_CORP_ID);
        storageRemoteAddressMap.keySet().forEach(storageId -> {
            InetSocketAddress localAddress = new InetSocketAddress(proxyConfig.getHostname(), proxyStartPort[0]++);
            storageLocalAddressMap.put(storageId, localAddress);
            storageLocalClientMap.put(storageId, new ClientApi(localAddress));
        });
    }



}
