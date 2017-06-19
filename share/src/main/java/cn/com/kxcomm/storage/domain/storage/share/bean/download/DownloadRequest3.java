package cn.com.kxcomm.storage.domain.storage.share.bean.download;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

/**
 * @class Download request 3
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class DownloadRequest3 extends Request {
    private String path;

    public DownloadRequest3(String path, Request request) {
        super(request);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
