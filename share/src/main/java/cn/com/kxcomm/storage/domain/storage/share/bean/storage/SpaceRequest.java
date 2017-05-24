package cn.com.kxcomm.storage.domain.storage.share.bean.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.TransportRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class SpaceRequest extends TransportRequest {
    private String relativePath;

    public SpaceRequest(String relativePath,String targetIp, int targetPort, long headCorpId, long loginOperId, String sysCode) {
        super(targetIp, targetPort, headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
    }

    public SpaceRequest(String relativePath,SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(targetAddress, headCorpId, loginOperId, sysCode);
        this.relativePath = relativePath;
    }

    public SpaceRequest(String targetIp, int targetPort, long headCorpId, long loginOperId, String sysCode) {
        super(targetIp, targetPort, headCorpId, loginOperId, sysCode);
        this.relativePath = "";
    }

    public SpaceRequest(SocketAddress targetAddress, long headCorpId, long loginOperId, String sysCode) {
        super(targetAddress, headCorpId, loginOperId, sysCode);
        this.relativePath = "";
    }
}
