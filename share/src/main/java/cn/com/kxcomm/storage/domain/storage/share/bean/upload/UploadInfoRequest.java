package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadInfoRequest extends Request {
    private String fileName;
    private long size;
    private String md5;
    private long storageCount;
    private long fileViewCode;
    private long storageId;
    private String path;


    public UploadInfoRequest(String fileName, long size, String md5, long storageCount, long fileViewCode, long storageId, String path, Request request) {
        super(request);
        this.fileName = fileName;
        this.size = size;
        this.md5 = md5;
        this.storageCount = storageCount;
        this.fileViewCode = fileViewCode;
        this.storageId = storageId;
        this.path = path;
    }
}
