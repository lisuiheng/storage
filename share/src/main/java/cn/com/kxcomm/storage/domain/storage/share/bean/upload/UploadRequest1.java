package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UploadRequest1 extends Request {
    private String fileName;
    private byte[] data;

    public UploadRequest1(String fileName, byte[] data, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileName = fileName;
        this.data = data;
        this.sysCode = sysCode;
    }
}
