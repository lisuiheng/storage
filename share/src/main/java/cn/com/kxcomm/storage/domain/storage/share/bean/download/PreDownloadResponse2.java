package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Pre download response 2
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreDownloadResponse2 extends Response {
    private String path;
    private int downloadPort;

    public PreDownloadResponse2(int downloadPort, String path, Request request) {
        super(request);
        this.downloadPort = downloadPort;
        this.path = path;
    }

    public PreDownloadResponse2(int downloadPort, PreDownloadRequest2 preDownloadRequest2) {
        super(preDownloadRequest2);
        this.downloadPort = downloadPort;
        this.path = preDownloadRequest2.getPath();
    }

    public PreDownloadResponse2(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
