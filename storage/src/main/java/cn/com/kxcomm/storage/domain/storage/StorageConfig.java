package cn.com.kxcomm.storage.domain.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Data
@Component
@ConfigurationProperties(prefix = "storage.config")
public class StorageConfig {
    private String host;
    private int port;
    private Path path;
    private Path basePath;
    private String dirFormat;
    private long fileStorageId;
    private int maxObjectSize;


    public Path getPath(String relativePath) {
        return basePath.resolve(relativePath);
    }

    public void setMaxObjectSize(int maxObjectSize) {
        this.maxObjectSize = maxObjectSize * 1024 * 1024;
    }
}
