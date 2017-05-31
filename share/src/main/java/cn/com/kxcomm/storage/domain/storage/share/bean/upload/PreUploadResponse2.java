package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreUploadResponse2 extends Response {
    private int uploadPort;
    private long storageId;

    public PreUploadResponse2(int uploadPort, long storageId, Request request) {
        super(request);
        this.uploadPort = uploadPort;
        this.storageId = storageId;
    }

    public PreUploadResponse2(Response response) {
        super(response);
    }

    public PreUploadResponse2(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
