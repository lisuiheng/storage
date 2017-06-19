package cn.com.kxcomm.storage.domain.storage.share.bean.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;


public class FileGetNameRequest extends Request {
    private long fileViewCode;

    public FileGetNameRequest(long fileViewCode, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileViewCode = fileViewCode;
    }

    public long getFileViewCode() {
        return fileViewCode;
    }

    public void setFileViewCode(long fileViewCode) {
        this.fileViewCode = fileViewCode;
    }
}
