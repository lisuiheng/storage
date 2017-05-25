package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "data", callSuper = true)
public class DownloadResponse1 extends Response {
    private String fileName;
    private byte[] data;

    public DownloadResponse1(String fileName, DownloadResponse2 downloadResponse2) {
        super(downloadResponse2);
        this.fileName = fileName;
        this.data = downloadResponse2.getData();
    }

}