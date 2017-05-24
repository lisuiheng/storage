package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "data", callSuper = true)
public class UploadRequest3 extends Request {
    private String fileName;
    private byte[] data;

    public UploadRequest3(UploadRequest2 uploadRequest2) {
        super(uploadRequest2);
        this.fileName = uploadRequest2.getFileName();
        this.data = uploadRequest2.getData();
    }
}
