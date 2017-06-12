package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @class Space response
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
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
