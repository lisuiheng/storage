package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @class List file response
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ListFileResponse extends Response {
    private final Set<File> files = new HashSet<>();

    public ListFileResponse(Request request) {
        super(request);
    }

    public ListFileResponse(Throwable throwable, Request request ) {
        super(throwable, request);
    }

    /**
     * @method Append.
     * @description
     * @author 李穗恒
     * @param name the name
     * @param size the size
     * @param md5 the md 5
     * @param lastModifiedTime the last modified time
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public synchronized void append(String name, long size, String md5, long lastModifiedTime) {
        append(new File(name, size, md5, lastModifiedTime));
    }

    /**
     * @method Append.
     * @description
     * @author 李穗恒
     * @param file the file
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public void append(File file) {
        files.add(file);
    }


    /**
     * @class File
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     * @description
     */
    @ToString
    @EqualsAndHashCode
    @Getter
    public class File implements Serializable {
        private String relativeName;
        private long size;
        private String md5;
        private long lastModifiedTime;

        private String relativeDir;
        private String fileName;

        public File(String relativeName, long size, String md5, long lastModifiedTime) {
            this.relativeName = relativeName;
            this.size = size;
            this.md5 = md5;
            this.lastModifiedTime = lastModifiedTime;

            int lastIndexOf = relativeName.lastIndexOf("/");
            relativeDir = relativeName.substring(0, lastIndexOf);
            fileName = relativeName.substring(lastIndexOf, relativeName.length());
        }


    }
}
