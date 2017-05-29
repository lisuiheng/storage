package cn.com.kxcomm.storage.domain.storage.share.bean.proxy;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ConnectResponse extends Response {
    private int[] ports;

    public ConnectResponse(int[] ports, Request request) {
        super(request);
        this.ports = ports;
    }

    public ConnectResponse(Response response) {
        super(response);
    }

    public ConnectResponse(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
