package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @class Application config
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
public class ApplicationConfig {
    /**
     * @method Client api client api.
     * @description
     * @author 李穗恒
     * @return the client api
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Bean
    public ClientApi clientApi() {
        return new ClientApi("localhost", 8006);
    }
}
