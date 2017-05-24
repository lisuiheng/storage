//package cn.com.kxcomm.client;
//
//
//import cn.com.kxcomm.storage.domain.client.ClientApi;
//import cn.com.kxcomm.storage.domain.client.Config;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//public class ClientTest {
//    private final Logger log = LoggerFactory.getLogger(ClientTest.class);
//    private final long headCorpId = 1L;
//    private final long loginOperId = 1L;
//    private final String sysCode = "coms";
//    private final String platformCode = "";
//    private final String platformKey = "";
//    private final String sysKey = "";
//
//    @Test
//    public void test() {
////        Response response = ClientApi.send(new Request(1L, 1L, "coms"));
//        log.info("response recevice");
//    }
//
//    @Test
//    public void getConfig() {
//        String ip = Config.getString("ip");
//        int port = Config.getInt("port");
//        log.info("adress is {}:{}", ip, port);
//    }
//
//    @Test
//    public void queueTimeout() throws InterruptedException {
//        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(1);
//        Integer integer = linkedBlockingQueue.poll(1, TimeUnit.SECONDS);
//        log.info("end");
//    }
//
//    @Test
//    public void upload() throws IOException, NoSuchAlgorithmException {
//        Long upload = ClientApi.upload(new File("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt"), 1L, loginOperId, headCorpId, platformCode,
//                platformKey, sysCode, sysKey);
//        log.info("end");
//    }
//}
