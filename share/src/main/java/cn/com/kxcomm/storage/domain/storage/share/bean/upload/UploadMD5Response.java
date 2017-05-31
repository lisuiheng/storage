package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString( callSuper = true)
public class UploadMD5Response  extends Response {
    private Long fileViewCode;
    private boolean FileExit;


    public UploadMD5Response(Long fileViewCode,boolean FileExit, Request request) {
        super(request);
        this.fileViewCode = fileViewCode;
        this.FileExit = FileExit;
    }
}
