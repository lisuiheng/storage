package cn.com.kxcomm.storage.domain.storage.share.bean.remove;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RemoveRequest3 extends Request {
    private String relativePath;

    public RemoveRequest3(String relativePath, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
    }
}
