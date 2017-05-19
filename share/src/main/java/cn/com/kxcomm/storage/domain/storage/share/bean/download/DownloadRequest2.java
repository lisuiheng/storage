package cn.com.kxcomm.storage.domain.storage.share.bean.download;


import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class DownloadRequest2 extends TransportRequest {
    private String path;
    public DownloadRequest2(String path, String targetIp, int targetPort, DownloadRequest1 downloadRequest1) {
       super(targetIp, targetPort, downloadRequest1.getHeadCorpId(), downloadRequest1.getLoginOperId(), downloadRequest1.getSysCode());
        this.path = path;
    }

}
