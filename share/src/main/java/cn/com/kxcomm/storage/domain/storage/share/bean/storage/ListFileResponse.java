package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.Set;

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

    public synchronized void append(String name, long size, String md5, FileTime lastModifiedTime) {
        append(new File(name, size, md5, lastModifiedTime));
    }

    public void append(File file) {
        files.add(file);
    }


    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public class File implements Serializable {
        private String relativeName;
        private long size;
        private String md5;
        private FileTime lastModifiedTime;
    }
}
