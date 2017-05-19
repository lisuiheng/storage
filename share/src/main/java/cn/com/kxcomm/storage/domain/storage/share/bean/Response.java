package cn.com.kxcomm.storage.domain.storage.share.bean;

import lombok.*;

import java.io.Serializable;

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

    public Response(Throwable throwable, Request request ) {
        this.id = request.getId();
        this.throwable = throwable;
    }

}

