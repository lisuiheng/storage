package cn.com.kxcomm.storage.domain.storage.share.bean.proxy;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;

/**
 * @class Connect response
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
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

    public int[] getPorts() {
        return ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
    }
}
