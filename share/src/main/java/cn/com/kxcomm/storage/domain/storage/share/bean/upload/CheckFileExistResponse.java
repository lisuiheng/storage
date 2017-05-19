package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CheckFileExistResponse extends Response {
    private long fileViewCode;

    CheckFileExistResponse(long fileViewCode, Request request) {
        super(request);
        this.fileViewCode = fileViewCode;
    }

}
