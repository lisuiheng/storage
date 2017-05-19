package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UploadResponse2 extends Response {
    private long storageId;
    private String path;
    private long size;
    private String md5;

    public UploadResponse2(long storageId, String path, long size, String md5, Request request) {
        super(request);
        this.storageId = storageId;
        this.path = path;
        this.size = size;
        this.md5 = md5;
    }

}
