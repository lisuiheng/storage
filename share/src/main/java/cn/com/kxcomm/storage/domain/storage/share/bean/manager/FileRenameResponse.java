package cn.com.kxcomm.storage.domain.storage.share.bean.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class FileRenameResponse extends Response {
    public FileRenameResponse(Request request) {
        super(request);
    }

    public FileRenameResponse(Response response) {
        super(response);
    }

    public FileRenameResponse(Throwable throwable, Request request) {
        super(throwable, request);
    }
}
