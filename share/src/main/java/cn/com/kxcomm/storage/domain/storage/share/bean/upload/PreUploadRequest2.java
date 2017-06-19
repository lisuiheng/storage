package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

/**
 * @class Pre upload request 2
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class PreUploadRequest2 extends Request {
    private long size;

    public PreUploadRequest2(PreUploadRequest1 preUploadRequest1) {
        super(preUploadRequest1);
        this.size = preUploadRequest1.getSize();
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
