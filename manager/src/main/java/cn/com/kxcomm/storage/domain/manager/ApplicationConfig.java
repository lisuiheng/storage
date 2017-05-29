package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {
    @Bean
    public ClientApi clientApi() {
        return new ClientApi("localhost", 8006);
    }
}
