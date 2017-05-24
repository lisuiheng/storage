package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadResponse3 extends Response {
    private String relativePath;
    private long size;
    private String md5;

    public UploadResponse3(String relativePath, long size, String md5, Request request) {
        super(request);
        this.relativePath = relativePath;
        this.size = size;
        this.md5 = md5;
    }
}
