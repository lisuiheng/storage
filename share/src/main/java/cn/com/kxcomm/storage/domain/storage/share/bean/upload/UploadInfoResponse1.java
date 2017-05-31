package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadInfoResponse1 extends Response {
    public UploadInfoResponse1(Request request) {
        super(request);
    }

    public UploadInfoResponse1(Response response) {
        super(response);
    }

    public UploadInfoResponse1(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
