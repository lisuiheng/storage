package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadResponse2 extends Response {
    private long storageId;
    private String relativePath;
    private long size;
    private String md5;

    public UploadResponse2(long storageId, UploadResponse3 uploadResponse3) {
        super(uploadResponse3);
        this.storageId = storageId;
        this.relativePath = uploadResponse3.getRelativePath();
        this.size = uploadResponse3.getSize();
        this.md5 = uploadResponse3.getMd5();
    }

}
