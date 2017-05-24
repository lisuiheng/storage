package cn.com.kxcomm.storage.domain.storage.share.bean.download;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class DownloadRequest2 extends Request {
    private long fileId;
    public DownloadRequest2(long fileId, DownloadRequest1 downloadRequest1) {
       super(downloadRequest1.getHeadCorpId(), downloadRequest1.getLoginOperId(), downloadRequest1.getSysCode());
        this.fileId = fileId;
    }

}
