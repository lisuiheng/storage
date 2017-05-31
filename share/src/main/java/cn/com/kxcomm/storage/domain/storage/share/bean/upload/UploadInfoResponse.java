package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadInfoResponse extends Response {
    public UploadInfoResponse(Request request) {
        super(request);
    }

    public UploadInfoResponse(Response response) {
        super(response);
    }

    public UploadInfoResponse(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
