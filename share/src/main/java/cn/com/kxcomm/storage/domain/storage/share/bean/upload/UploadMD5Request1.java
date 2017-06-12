package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Upload md 5 request 1
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
public class UploadMD5Request1 extends Request {
    private String fileName;
    private long storageCount;
    private String md5;

    public UploadMD5Request1(String fileName, String md5, long headCorpId, long loginOperId, String sysCode) {
        this(fileName, md5, ShareConstants.STORAGE_COUNT_DEFAULT, headCorpId, loginOperId, sysCode);
    }
    public UploadMD5Request1(String fileName, String md5, long storageCount, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileName = fileName;
        this.md5 = md5;
        this.storageCount = storageCount;
        this.sysCode = sysCode;
    }
}
