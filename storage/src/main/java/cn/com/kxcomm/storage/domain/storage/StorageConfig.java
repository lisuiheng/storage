package cn.com.kxcomm.storage.domain.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Component
@ConfigurationProperties(prefix = "storage.config")
public class StorageConfig {
    private String host;
    private int port;
    private Path path;
    private String dirFormat;
    private Long fileStorageId;

    public void setBaseDir(String baseDir) {
        this.path = Paths.get(baseDir);
        System.out.println(path);
    }
}
