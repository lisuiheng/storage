package cn.com.kxcomm.storage.domain.storage.share.bean.remove;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

/**
 * @class Remove request 1
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class RemoveRequest1 extends Request {
    private long fileViewCode;

    public RemoveRequest1(long fileViewCode, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileViewCode = fileViewCode;
    }

    public long getFileViewCode() {
        return fileViewCode;
    }

    public void setFileViewCode(long fileViewCode) {
        this.fileViewCode = fileViewCode;
    }
}
