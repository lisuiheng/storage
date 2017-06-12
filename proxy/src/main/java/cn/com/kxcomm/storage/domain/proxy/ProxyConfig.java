package cn.com.kxcomm.storage.domain.proxy;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @class Proxy config
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
@ConfigurationProperties(prefix = "proxy.config")
public class ProxyConfig {
    private final Logger log = LoggerFactory.getLogger(ProxyConfig.class);


    private int port;
    private int proxyStartPort;
    private long timeout;


}
