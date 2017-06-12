package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Upload md 5 response 1
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
@ToString( callSuper = true)
public class UploadMD5Response1 extends Response {
    private Long fileViewCode;
    private boolean FileExit;


    public UploadMD5Response1(Long fileViewCode, boolean FileExit, Request request) {
        super(request);
        this.fileViewCode = fileViewCode;
        this.FileExit = FileExit;
    }
}
