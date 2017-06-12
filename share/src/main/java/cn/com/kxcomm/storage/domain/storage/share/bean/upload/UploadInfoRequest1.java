package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Upload info request 1
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
public class UploadInfoRequest1 extends Request {
    private String fileName;
    private long size;
    private String md5;
    private long storageCount;
    private long fileViewCode;
    private long storageId;
    private String path;


    public UploadInfoRequest1(String fileName, long size, String md5, long storageCount, long fileViewCode, long storageId, String path, Request request) {
        super(request);
        this.fileName = fileName;
        this.size = size;
        this.md5 = md5;
        this.storageCount = storageCount;
        this.fileViewCode = fileViewCode;
        this.storageId = storageId;
        this.path = path;
    }
}
