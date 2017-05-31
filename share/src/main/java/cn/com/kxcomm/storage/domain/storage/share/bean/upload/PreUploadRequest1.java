package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreUploadRequest1 extends Request {
    private long size;

    public PreUploadRequest1(long size, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.size = size;
    }

    public PreUploadRequest1(Request request) {
        super(request);
    }
}
