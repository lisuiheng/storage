package cn.com.kxcomm.storage.domain.storage.share.bean.remove;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RemoveResponse3 extends Response {
    public RemoveResponse3(Request request) {
        super(request);
    }
    public RemoveResponse3(Throwable t, Request request) {
        super(t, request);
    }
}
