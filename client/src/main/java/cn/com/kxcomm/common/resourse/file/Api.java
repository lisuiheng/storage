package cn.com.kxcomm.common.resourse.file;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.CheckFileExistRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.CheckFileExistResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Api {
    private final Logger log = LoggerFactory.getLogger(Api.class);
    private static final Client client = new Client();

    public static Long upload(File file, Long storageCount,
                              Long loginOperId, Long headCorpId, String platformCode,
                              String platformKey, String sysCode, String sysKey) throws IOException, NoSuchAlgorithmException {
        Path path = Paths.get(file.getPath());
        byte[] bytes = Files.readAllBytes(path);
        String fileName = path.getFileName().toString();

        Long fileId = getFileIdByMD5(md5(bytes), headCorpId, loginOperId, sysCode);
        if(fileId == null) {
            UploadRequest1 uploadRequest1 = new UploadRequest1(fileName, bytes, headCorpId, loginOperId, sysCode);
            UploadResponse1 response = (UploadResponse1) send(uploadRequest1);
            fileId = response.getFileViewCode();
        }
        return fileId;
    }

    public static Long getFileIdByMD5(String md5, long headCorpId, long loginOperId, String sysCode) {
        CheckFileExistRequest request = new CheckFileExistRequest(md5, headCorpId, loginOperId, sysCode);
        CheckFileExistResponse response = (CheckFileExistResponse)send(request);
        return response.getFileViewCode();
    }

    private static String md5(byte[] bytes) throws  NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toLowerCase();
        return hash;
    }

    public static Response send(Request request) {
        if(!client.isReady()) {
            startClient();
        }
        client.send(request);
        Response response = client.getResponse(request);
        handleError(response);
        return response;
    }

    private static void startClient()   {
        String host = Config.getString(Constants.REMOTE_IP);
        int port = Config.getInt(Constants.REMOTE_PORT);
        client.start(host, port);
    }

//    private final ClientHandler clientHandler;
//
//    public Api(ClientHandler clientHandler) {
//        this.clientHandler = clientHandler;
//    }
//
//
//
//    public void download(String baseDir, long fileViewCode, long headCorpId, long loginOperId, String sysCode) throws IOException {
//        DownloadResponse1 response1 = downloadResponse(fileViewCode, headCorpId, loginOperId, sysCode);
//        Path filePath = Paths.get(baseDir, response1.getFileName());
//        Files.write(filePath, response1.getData());
//    }
//
//    public byte[] download(long fileViewCode, long headCorpId, long loginOperId, String sysCode) throws IOException {
//        DownloadResponse1 response1 = downloadResponse(fileViewCode, headCorpId, loginOperId, sysCode);
//        return response1.getData();
//    }
//
//    private DownloadResponse1 downloadResponse(long fileViewCode, long headCorpId, long loginOperId, String sysCode) throws IOException {
//        DownloadRequest1 request = new DownloadRequest1(fileViewCode, headCorpId, loginOperId, sysCode);
//        clientHandler.send(request);
//        DownloadResponse1 response = (DownloadResponse1) clientHandler.getResponse(request);
//        handleError(response);
//        return response;
//    }
//
//    /*
//    upload file
//     */
//    public long upload(Path path, long headCorpId, long loginOperId, String sysCode) throws IOException, NoSuchAlgorithmException {
//        byte[] bytes = Files.readAllBytes(path);
//        String fileName = path.getFileName().toString();
//
//        Long fileId = getFileIdByMD5(fileName, md5(bytes), (long) bytes.length, headCorpId, loginOperId, sysCode);
//        if(fileId == null) {
//            UploadRequest1 uploadRequest1 = new UploadRequest1(fileName, bytes, headCorpId, loginOperId, sysCode);
//            clientHandler.send(uploadRequest1);
//            UploadResponse1 response = (UploadResponse1) clientHandler.getResponse(uploadRequest1);
//            handleError(response);
//            fileId = response.getFileViewCode();
//        }
//        return fileId;
//    }
//
//    public boolean checkFileExist(String fileName, String md5, long fileSize, long headCorpId, long loginOperId, String sysCode) {
//        Long fileId = getFileIdByMD5(fileName, md5, fileSize, headCorpId, loginOperId, sysCode);
//        return fileId != null;
//    }
//
//    public Long getFileIdByMD5(String fileName, String md5, long fileSize, long headCorpId, long loginOperId, String sysCode) {
//        CheckFileExistRequest request = new CheckFileExistRequest(fileName, md5, fileSize, headCorpId, loginOperId, sysCode);
//        clientHandler.send(request);
//        CheckFileExistResponse response = (CheckFileExistResponse)clientHandler.getResponse(request);
//        handleError(response);
//        return response.getFileViewCode();
//    }
//
//    private String md5(byte[] bytes) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(bytes);
//        byte[] digest = md.digest();
//        String hash = DatatypeConverter
//                .printHexBinary(digest).toLowerCase();
//        return hash;
//    }
//
    /*
    handle error
     */
    private static void handleError(Response response) {
        if(response.getThrowable() != null) {
            throw new RuntimeException(response.getThrowable());
        }
    }


}
