package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Pre download request 1
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
public class PreDownloadRequest1 extends Request {
    private long fileViewCode;

    public PreDownloadRequest1(long fileViewCode, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileViewCode = fileViewCode;
    }

    public PreDownloadRequest1(Request request) {
        super(request);
    }
}
