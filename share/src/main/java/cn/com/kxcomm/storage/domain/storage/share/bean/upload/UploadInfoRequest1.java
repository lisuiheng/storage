package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

/**
 * @class Upload info request 1
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class UploadInfoRequest1 extends Request {
    private String fileName;
    private long size;
    private String md5;
    private long storageCount;
    private long fileViewCode;
    private long storageId;
    private String path;


    public UploadInfoRequest1(String fileName, long size, String md5, long storageCount, long fileViewCode, long storageId, String path, Request request) {
        super(request);
        this.fileName = fileName;
        this.size = size;
        this.md5 = md5;
        this.storageCount = storageCount;
        this.fileViewCode = fileViewCode;
        this.storageId = storageId;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(long storageCount) {
        this.storageCount = storageCount;
    }

    public long getFileViewCode() {
        return fileViewCode;
    }

    public void setFileViewCode(long fileViewCode) {
        this.fileViewCode = fileViewCode;
    }

    public long getStorageId() {
        return storageId;
    }

    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
