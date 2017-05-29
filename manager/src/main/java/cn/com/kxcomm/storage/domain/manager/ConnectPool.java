package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectResponse;
import lombok.Getter;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import static cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants.SYSTEM_HEAD_CORP_ID;
import static cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants.SYSTEM_LOGIN_OPER_ID;
import static cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants.SYSTEM_SYSCODE;

@Component
public class ConnectPool {
    private final Map<Integer, Integer> managerProxyPortMap = new HashMap<>();

    public void putManagerProxyPort(int managerPort, int proxyPort) {
        managerProxyPortMap.put(managerPort, proxyPort);
    }
}
