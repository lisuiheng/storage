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

    public DownloadResponse2(DownloadResponse3 downloadResponse3) {
        super(downloadResponse3);
        this.data = downloadResponse3.getData();
    }

    public DownloadResponse2(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
