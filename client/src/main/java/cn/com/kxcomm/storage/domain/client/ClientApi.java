package cn.com.kxcomm.storage.domain.client;


import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ClientApi {
    private final Logger log = LoggerFactory.getLogger(ClientApi.class);
    private final Client client;

    public ClientApi() {
        this(Config.getRemoteHostname(), Config.getRemotePort());
    }

    public ClientApi(String hostname, int port) {
        this(new InetSocketAddress(hostname, port));
    }

    public ClientApi(SocketAddress address) {
        this.client = new Client(address);
    }




//    public Long upload(File file, Long storageCount,
//                              Long loginOperId, Long headCorpId, String platformCode,
//                              String platformKey, String sysCode, String sysKey) throws IOException, NoSuchAlgorithmException, StorageException {
//        Path path = Paths.get(file.getRelativePath());
//        byte[] bytes = Files.readAllBytes(path);
//        String fileName = path.getFileName().toString();
//
//        Long fileId = getFileIdByMD5(md5(bytes), headCorpId, loginOperId, sysCode);
//        if(fileId == null) {
//            UploadRequest1 uploadRequest1 = new UploadRequest1(fileName, bytes, headCorpId, loginOperId, sysCode);
//            UploadResponse1 response = (UploadResponse1) send(uploadRequest1);
//            fileId = response.getFileViewCode();
//        }
//        return fileId;
//    }
//
//    public Long getFileIdByMD5(String md5, long headCorpId, long loginOperId, String sysCode) throws StorageException {
//        CheckFileExistRequest request = new CheckFileExistRequest(md5, headCorpId, loginOperId, sysCode);
//        CheckFileExistResponse response = (CheckFileExistResponse)send(request);
//        return response.getFileViewCode();
//    }
//
//    private String md5(byte[] bytes) throws  NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(bytes);
//        byte[] digest = md.digest();
//        String hash = DatatypeConverter
//                .printHexBinary(digest).toLowerCase();
//        return hash;
//    }


    public Response send(Request request) throws StorageException {
        client.send(request);
        Response response = client.getResponse(request);
        handleError(response);
        return response;
    }



    /*
    handle error
     */
    private  void handleError(Response response) throws StorageException {
        if(response.getThrowable() != null) {
            throw new StorageException(response.getThrowable());
        }
    }


}
