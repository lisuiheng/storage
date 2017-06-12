package cn.com.kxcomm.storage.domain.storage.share.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @class Response
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
public class Response implements Serializable {
    private static final long serialVersionUID = 5887232731148682128L;

    protected long id;
    protected Throwable throwable;

    public Response(Request request) {
        this.id = request.getId();
    }

    public Response(Response response) {
        this.id = response.getId();
    }

    public Response(Throwable throwable, Request request ) {
        this.id = request.getId();
        this.throwable = throwable;
    }

}

