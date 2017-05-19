package cn.com.kxcomm.storage.domain.storage.share.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class TransportRequest extends Request {
    private String targetIp;
    private int targetPort;

    public TransportRequest(String targetIp, int targetPort, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.targetIp = targetIp;
        this.targetPort = targetPort;
    }

    public TransportRequest(String targetIp, int targetPort, Request request) {
        super(request);
        this.targetIp = targetIp;
        this.targetPort = targetPort;
    }
}