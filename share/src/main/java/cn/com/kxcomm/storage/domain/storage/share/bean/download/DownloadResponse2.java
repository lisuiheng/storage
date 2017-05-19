package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class DownloadResponse2 extends Response {
    private byte[] data;

    public DownloadResponse2( byte[] data, Request request) {
        super(request);
        this.data = data;
    }

    public DownloadResponse2(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
