package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.nio.file.attribute.FileTime;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ListFileRequest extends Request implements Serializable {
    private String relativePath;
    private long lastModifiedTime;

    public ListFileRequest(String relativePath, long lastModifiedTime, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
        this.lastModifiedTime = lastModifiedTime;
    }

    public ListFileRequest(String relativePath, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
        this.lastModifiedTime = 0;
    }
}
