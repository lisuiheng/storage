package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.nio.file.attribute.FileTime;
import java.util.HashSet;
import java.util.Set;

public class ListFileRequest extends Request {
    private final Set<File> files = new HashSet<>();

    public ListFileRequest(long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
    }

    public ListFileRequest(Request request) {
        super(request);
    }

    public void append(String name, long size, String md5, FileTime lastModifiedTime) {
        append(new File(name, size, md5, lastModifiedTime));
    }

    public void append(File file) {
        files.add(file);
    }


    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    private class File implements Serializable {
        private String name;
        private long size;
        private String md5;
        private FileTime lastModifiedTime;
    }
}
