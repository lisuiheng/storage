package cn.com.kxcomm.storage.domain.storage.share.bean.upload;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "data", callSuper = true)
public class UploadRequest2 extends Request {
    private String fileName;
    private byte[] data;

    public UploadRequest2(UploadRequest1 uploadRequest1) {
        super(uploadRequest1);
        this.fileName = uploadRequest1.getFileName();
        this.data = uploadRequest1.getData();
    }



}
