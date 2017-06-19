package cn.com.kxcomm.storage.domain.storage.share.bean.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;


public class FileGetNameResponse extends Response {
    private String fileName;

    public FileGetNameResponse(String fileName, Request request) {
        super(request);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
