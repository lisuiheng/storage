package cn.com.kxcomm.storage.domain.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * @class Storage config
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
@ConfigurationProperties(prefix = "storage.config")
public class StorageConfig {
    private String host;
    private int port;
    private Path path;
    private Path basePath;
    private String dirFormat;
    private long fileStorageId;
    private int maxObjectSize;


    /**
     * @method Get path path.
     * @description
     * @author 李穗恒
     * @return the path
     * @param relativePath the relative path
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public Path getPath(String relativePath) {
        return basePath.resolve(relativePath);
    }

    /**
     * @method Set max object size.
     * @description
     * @author 李穗恒
     * @param maxObjectSize the max object size
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public void setMaxObjectSize(int maxObjectSize) {
        this.maxObjectSize = maxObjectSize * 1024 * 1024;
    }
}
