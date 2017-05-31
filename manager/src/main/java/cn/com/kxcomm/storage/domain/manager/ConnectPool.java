package cn.com.kxcomm.storage.domain.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectPool {
    private final Map<Integer, Integer> managerProxyPortMap = new HashMap<>();

    public void putManagerProxyPort(int managerPort, int proxyPort) {
        managerProxyPortMap.put(proxyPort, managerPort);
    }

    public int getManagerPort(int proxyPort) {
        return managerProxyPortMap.get(proxyPort);
    }
}
