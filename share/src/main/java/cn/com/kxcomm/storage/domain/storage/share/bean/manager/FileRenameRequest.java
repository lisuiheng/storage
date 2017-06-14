package cn.com.kxcomm.storage.domain.storage.share.bean.manager;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class FileRenameRequest extends Request {
    private long fileViewCode;
    private String newFileName;

    public FileRenameRequest(long fileViewCode, String newFileName, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.fileViewCode = fileViewCode;
        this.newFileName = newFileName;
    }
}
