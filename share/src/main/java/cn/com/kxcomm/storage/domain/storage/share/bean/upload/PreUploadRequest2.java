package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PreUploadRequest2 extends Request {
    private long size;

    public PreUploadRequest2(PreUploadRequest1 preUploadRequest1) {
        super(preUploadRequest1);
        this.size = preUploadRequest1.getSize();
    }
}
