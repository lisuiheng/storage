package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;

/**
 * @class Upload info response 1
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
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
