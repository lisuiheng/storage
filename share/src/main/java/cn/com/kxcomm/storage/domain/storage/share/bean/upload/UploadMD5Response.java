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

    public UploadMD5Response(Request request) {
        this(null, request);
    }

    public UploadMD5Response(Long fileViewCode, Request request) {
        super(request);
        this.fileViewCode = fileViewCode;
    }
}
