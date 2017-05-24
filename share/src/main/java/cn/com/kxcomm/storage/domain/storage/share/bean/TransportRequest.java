package cn.com.kxcomm.storage.domain.storage.share.bean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class TransportRequest extends Request {
    private SocketAddress targetAddress;

    public TransportRequest(String targetIp, int targetPort, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.targetAddress = new InetSocketAddress(targetIp, targetPort);
    }

    public TransportRequest(SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(headCorpId, loginOperId, sysCode);
        this.targetAddress = targetAddress;
    }

    public TransportRequest(String targetIp, int targetPort, Request request) {
        super(request);
        this.targetAddress = new InetSocketAddress(targetIp, targetPort);
    }

    public TransportRequest(SocketAddress targetAddress, Request request) {
        super(request);
        this.targetAddress = targetAddress;
    }
}