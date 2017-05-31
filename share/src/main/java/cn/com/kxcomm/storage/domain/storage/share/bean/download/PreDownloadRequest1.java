package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
