package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "data", callSuper = true)
public class DownloadResponse3 extends Response {
    private byte[] data;

    public DownloadResponse3(byte[] data, Request request) {
        super(request);
        this.data = data;
    }

    @Override
    public String toString() {
        return "DownloadResponse3{" +
                "id=" + id +
                ", throwable=" + throwable +
                '}';
    }
}