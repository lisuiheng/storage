package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadMD5Request extends Request {
    private String fileName;
    private long storageCount;
    private String md5;

    public UploadMD5Request(String fileName,String md5, long headCorpId, long loginOperId, String sysCode) {
        this(fileName, md5, ShareConstants.STORAGE_COUNT_DEFAULT, headCorpId, loginOperId, sysCode);
    }
    public UploadMD5Request(String fileName,String md5, long storageCount, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileName = fileName;
        this.md5 = md5;
        this.storageCount = storageCount;
        this.sysCode = sysCode;
    }
}
