package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreDownloadResponse1 extends Response {
    private String path;
    private int downloadPort;

    public PreDownloadResponse1(PreDownloadResponse2 preDownloadResponse2) {
        super(preDownloadResponse2);
        this.downloadPort = preDownloadResponse2.getDownloadPort();
        this.path = preDownloadResponse2.getPath();
    }

    public PreDownloadResponse1(Response response) {
        super(response);
    }

    public PreDownloadResponse1(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
