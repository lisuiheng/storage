package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SpaceResponse extends Response {
    long freeSpace;
    long usableSpace;
    long totalSpace;

    public SpaceResponse(long freeSpace, long usableSpace, long totalSpace, Request request) {
        super(request);
        this.freeSpace = freeSpace;
        this.usableSpace = usableSpace;
        this.totalSpace =  totalSpace;
    }


}
