package cn.com.kxcomm.storage.domain.client;


import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;


/**
 * @class Client api
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public class ClientApi {
    private final Logger log = LoggerFactory.getLogger(ClientApi.class);
    private final Client client;

    public ClientApi() {
        this(Config.getRemoteHostname(), Config.getRemotePort());
    }

    public ClientApi(String hostname, int port) { this(new InetSocketAddress(hostname, port)); }

    public ClientApi(SocketAddress address) {
        this.client = new Client(address);
    }


    /**
     * @method Send response.
     * @description
     * @author 李穗恒
     * @return the response
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public Response send(Request request) throws StorageException {
        // send request to server and wait response untill return
        client.send(request);
        Response response = client.getResponse(request);
        handleError(response);
        return response;
    }


    /**
     * @method Handle error.
     * @description
     * @author 李穗恒
     * @param response the response
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    private  void handleError(Response response) throws StorageException {
        if(response.getThrowable() != null) {
            throw new StorageException(response.getThrowable());
        }
    }


}
