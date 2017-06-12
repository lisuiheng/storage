package cn.com.kxcomm.storage.domain.storage.share.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @class Request
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
@EqualsAndHashCode
@ToString
public class Request implements Serializable {
    private static final long serialVersionUID = 2750646443189480771L;
    protected final long id;
    protected Long headCorpId;
    protected Long loginOperId;
    protected String sysCode;


    public Request(long headCorpId, long loginOperId, String sysCode) {
        id = System.nanoTime();
        this.headCorpId = headCorpId;
        this.loginOperId = loginOperId;
        this.sysCode = sysCode;
    }

    public Request(Request request) {
        id = request.getId();
        this.headCorpId = request.getHeadCorpId();
        this.loginOperId = request.getLoginOperId();
        this.sysCode = request.getSysCode();
    }

}
