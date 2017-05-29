package cn.com.kxcomm.storage.domain.storage.share.bean.proxy;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ConnectRequest extends Request {
    public ConnectRequest(long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
    }

    public ConnectRequest(Request request) {
        super(request);
    }
}
