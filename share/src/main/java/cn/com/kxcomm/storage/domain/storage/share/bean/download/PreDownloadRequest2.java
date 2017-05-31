package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreDownloadRequest2 extends Request {
    private long storageId;
    private String path;

    public PreDownloadRequest2(long storageId, String path, Request request) {
        super(request);
        this.storageId = storageId;
        this.path = path;
    }
}
