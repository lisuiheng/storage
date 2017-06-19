package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

/**
 * @class List file request
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class ListFileRequest  extends Request {
    private String relativePath;
    private long lastModifiedTime;

    public ListFileRequest(long headCorpId, long loginOperId, String sysCode) {
        this("", headCorpId, loginOperId, sysCode);
    }

    public ListFileRequest(String relativePath, long headCorpId, long loginOperId, String sysCode) {
       this(relativePath, 0L, headCorpId, loginOperId, sysCode);
    }

    public ListFileRequest(String relativePath, long lastModifiedTime, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
