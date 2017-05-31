package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreUploadResponse1 extends Response {
    private int uploadPort;
    private long storageId;


    public PreUploadResponse1(int uploadPort, long storageId, Response response) {
        super(response);
        this.uploadPort = uploadPort;
        this.storageId = storageId;
    }

    public PreUploadResponse1(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
