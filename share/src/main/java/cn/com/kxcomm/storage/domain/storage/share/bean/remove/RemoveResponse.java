package cn.com.kxcomm.storage.domain.storage.share.bean.remove;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RemoveResponse extends Response {
    public RemoveResponse(Request request) {
        super(request);
    }

    public RemoveResponse(Response response) {
        super(response);
    }

    public RemoveResponse(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
