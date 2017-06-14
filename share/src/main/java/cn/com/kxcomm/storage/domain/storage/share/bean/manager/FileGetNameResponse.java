package cn.com.kxcomm.storage.domain.storage.share.bean.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.PreDownloadResponse2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class FileGetNameResponse extends Response {
    private String fileName;

    public FileGetNameResponse(String fileName, Request request) {
        super(request);
        this.fileName = fileName;
    }
}
