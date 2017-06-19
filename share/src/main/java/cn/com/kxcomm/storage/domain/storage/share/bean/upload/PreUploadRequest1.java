package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

/**
 * @class Pre upload request 1
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class PreUploadRequest1 extends Request {
    private long size;

    public PreUploadRequest1(long size, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.size = size;
    }

    public PreUploadRequest1(Request request) {
        super(request);
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
