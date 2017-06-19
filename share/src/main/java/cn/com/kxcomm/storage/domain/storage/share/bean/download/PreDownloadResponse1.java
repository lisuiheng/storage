package cn.com.kxcomm.storage.domain.storage.share.bean.download;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;

/**
 * @class Pre download response 1
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDownloadPort() {
        return downloadPort;
    }

    public void setDownloadPort(int downloadPort) {
        this.downloadPort = downloadPort;
    }
}
