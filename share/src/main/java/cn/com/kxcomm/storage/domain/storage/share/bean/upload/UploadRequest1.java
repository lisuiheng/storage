package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UploadRequest1 extends Request {
    private String fileName;
    private long storageCount;
    private byte[] data;

    public UploadRequest1(String fileName, byte[] data, long headCorpId, long loginOperId, String sysCode) {
        this(fileName, data, ShareConstants.STORAGE_COUNT_DEFAULT, headCorpId, loginOperId, sysCode);
    }
    public UploadRequest1(String fileName, byte[] data,long storageCount,  long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileName = fileName;
        this.storageCount = storageCount;
        this.data = data;
        this.sysCode = sysCode;
    }
}
