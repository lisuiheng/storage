package cn.com.kxcomm.storage.domain.storage.share.bean.download;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class DownloadRequest3 extends Request {
    private String path;

    public DownloadRequest3(String path, Request request) {
        super(request);
        this.path = path;
    }

}
