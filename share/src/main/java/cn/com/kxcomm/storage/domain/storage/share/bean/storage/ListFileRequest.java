package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.net.SocketAddress;
import java.nio.file.attribute.FileTime;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ListFileRequest extends TransportRequest  {
    private String relativePath;
    private long lastModifiedTime;

    public ListFileRequest(SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(targetAddress, headCorpId, loginOperId, sysCode);
        this.relativePath = "";
        this.lastModifiedTime = 0L;
    }

    public ListFileRequest(String relativePath, SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(targetAddress, headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
        this.lastModifiedTime = lastModifiedTime;
    }

    public ListFileRequest(long lastModifiedTime, SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(targetAddress, headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
        this.lastModifiedTime = lastModifiedTime;
    }

    public ListFileRequest(String relativePath, long lastModifiedTime, SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(targetAddress, headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
        this.lastModifiedTime = lastModifiedTime;
    }



}
