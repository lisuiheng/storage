//package cn.com.kxcomm.common.resourse.file;
//
//import cn.com.kxcomm.test.file.share.Request;
//import cn.com.kxcomm.test.file.share.Response;
//import cn.com.kxcomm.test.file.share.upload.UploadRequest1;
//import cn.com.kxcomm.test.file.share.upload.UploadRequest2;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.NoSuchAlgorithmException;
//import java.util.concurrent.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class FileSystemClientTest {
//    private final Logger log = LoggerFactory.getLogger(FileSystemClientTest.class);
//
//    private final int managerPort = 8005;
//    private final int transportPort = 8006;
//    private final int storage1Port = 8007;
//    private final long headCorpId = 1L;
//    private final long loginOperId = 1L;
//    private final String sysCode = "coms";
//
//    @Autowired
//    private ClientHandler clientHandler;
//    @Autowired
//    private Api api;
//    @Autowired
//    private Client client;
//
//    private void startClient(int port) {
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                client.start("localhost",  port);
//                return null;
//            } catch (Exception e) {
//                return e;
//            }
//        }).thenAcceptAsync(e -> {
//            if(null != e) {
//                log.error(e.getMessage());
//            }
//        });
//        log.info("start");
//    }
//
//    @Test
//    public void upload() throws Exception {
//        startClient(managerPort);
//
//        api.upload(Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt"), headCorpId, loginOperId, sysCode);
////        api.upload(Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt"), headCorpId, loginOperId, sysCode);
//    }
//
//    @Test
//    public void checkMd5() {
//        startClient(managerPort);
//        api.checkFileExist("1.txt","9ad00f332a13b8b8d77e860428acc34", 2084L, headCorpId, loginOperId, sysCode);
//        log.info("checkFileExist1");
//        api.checkFileExist("2.txt","9ad00f332a13b8b8d77e860428acc34", 2084L, headCorpId, loginOperId, sysCode);
//        log.info("checkFileExist2");
//    }
//
//    @Test
//    public void request() throws InterruptedException {
//        startClient(managerPort);
//        for (int i = 0; i < 1; i++) {
//            Request request = new Request(1L,1L, "");
//            clientHandler.send(request);
//            Response response = clientHandler.getResponse(request);
//            log.info("end");
//        }
//
//        Thread.sleep(2000L);
////        Request request = new Request(headCorpId, loginOperId, sysCode);
////        clientHandler.send(request);
////        Response response = clientHandler.getResponse(request.getId());
//    }
//
//    @Test
//    public void download() throws IOException {
//        startClient(managerPort);
//        api.download("/home/lee/workspace/java/test/file/client/src/main/resources", 1494818881898762L, headCorpId, loginOperId, sysCode);
//    }
//
//    @Test
//    public void uploadDownload() throws IOException, NoSuchAlgorithmException {
//        startClient(managerPort);
//        long fileViewCode = api.upload(Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt"), headCorpId, loginOperId, sysCode);
//        byte[] data = api.download(fileViewCode, headCorpId, loginOperId, sysCode);
//        Files.write(Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/2.txt"), data);
//    }
//
//    @Test
//    public void uploadToStorage() throws IOException {
//        startClient(storage1Port);
//        Path filePath = Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt");
//        UploadRequest2 uploadRequest2 = new UploadRequest2(new UploadRequest1(filePath.getFileName().toString(), Files.readAllBytes(filePath), headCorpId, loginOperId, sysCode));
//        clientHandler.send(uploadRequest2);
//        Response response = clientHandler.getResponse(uploadRequest2);
//    }
//}
