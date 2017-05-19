package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UploadRequest2 extends Request {
    private String fileName;
    private byte[] data;

    public UploadRequest2(UploadRequest1 uploadRequest1) {
        super(uploadRequest1.getHeadCorpId(), uploadRequest1.getLoginOperId(), uploadRequest1.getSysCode());
        this.fileName = uploadRequest1.getFileName();
        this.data = uploadRequest1.getData();
    }

    public UploadRequest2(String fileName, byte[] data, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileName = fileName;
        this.data = data;
    }

}
