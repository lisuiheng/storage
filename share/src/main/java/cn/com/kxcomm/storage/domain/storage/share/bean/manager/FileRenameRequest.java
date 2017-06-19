package cn.com.kxcomm.storage.domain.storage.share.bean.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;

public class FileRenameRequest extends Request {
    private long fileViewCode;
    private String newFileName;

    public FileRenameRequest(long fileViewCode, String newFileName, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileViewCode = fileViewCode;
        this.newFileName = newFileName;
    }

    public long getFileViewCode() {
        return fileViewCode;
    }

    public void setFileViewCode(long fileViewCode) {
        this.fileViewCode = fileViewCode;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
}
