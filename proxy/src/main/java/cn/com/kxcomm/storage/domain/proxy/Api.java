package cn.com.kxcomm.storage.domain.proxy;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.service.addr.model.FileAddrModel;
import cn.com.kxcomm.storage.domain.service.addr.service.FileAddrService;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * @class Api
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Component
public class Api {
    private final Logger log = LoggerFactory.getLogger(Api.class);

    private final ConnectPool connectPool;
    private final FileAddrService fileAddrServicef;

    private final Random random = new Random();
    private final Object[] storageIds;

    public Api(ConnectPool connectPool, FileAddrService fileAddrServicef) {
        this.fileAddrServicef = fileAddrServicef;
        this.connectPool = connectPool;
        storageIds = connectPool.getStorageLocalClientMap().keySet().toArray();
    }

    /**
     * @method Pre upload pre upload response 2.
     * @description
     * @author 李穗恒
     * @return the pre upload response 2
     * @param preUploadRequest2 the pre upload request 2
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public PreUploadResponse2 preUpload(PreUploadRequest2 preUploadRequest2) {
        long size = preUploadRequest2.getSize();
        Long randomStorageId = getRandomStorageId();
        InetSocketAddress inetSocketAddress = connectPool.getStorageLocalAddressMap().get(randomStorageId);
        return new PreUploadResponse2(inetSocketAddress.getPort(), randomStorageId,preUploadRequest2);
    }


    /**
     * @method Pre download pre download response 2.
     * @description
     * @author 李穗恒
     * @return the pre download response 2
     * @param preDownloadRequest2 the pre download request 2
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    PreDownloadResponse2 preDownload(PreDownloadRequest2 preDownloadRequest2) {
        long storageId = preDownloadRequest2.getStorageId();
        InetSocketAddress address = connectPool.getStorageLocalAddressMap().get(storageId);
        return new PreDownloadResponse2(address.getPort(), preDownloadRequest2);
    }

    /**
     * @method Connect connect response.
     * @description
     * @author 李穗恒
     * @return the connect response
     * @param connectRequest the connect request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public ConnectResponse connect(ConnectRequest connectRequest) {
        Collection<InetSocketAddress> localAddress = connectPool.getStorageLocalAddressMap().values();
        int[] ports = new int[localAddress.size()];
        Iterator<InetSocketAddress> iterator = localAddress.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            ports[i] = iterator.next().getPort();
        }
        return new ConnectResponse(ports, connectRequest);
    }


    /**
     * @method Get client api client api.
     * @description
     * @author 李穗恒
     * @return the client api
     * @param storageId the storage id
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private ClientApi getClientApi(long storageId) {
        return connectPool.getStorageLocalClientMap().get(storageId);
    }

    /**
     * @method Get random storage id long.
     * @description
     * @author 李穗恒
     * @return the long
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    private Long getRandomStorageId() {
        return (Long) storageIds[random.nextInt(storageIds.length)];
    }


}
