package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Pre upload response 1
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
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
