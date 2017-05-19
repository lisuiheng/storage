package cn.com.kxcomm.common.resourse.file;

import cn.com.kxcomm.common.utils.Md5Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiTest {
    private final Logger log = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void md5() throws NoSuchAlgorithmException, IOException {
        Path path = Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt");
        byte[] bytes = Files.readAllBytes(path);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        byte[] digest = md.digest();
        String sysHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();

        String kxHash = Md5Util.getMd5(new File("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt"));
        log.info("sysHash is {}, kxHash is {}", sysHash, kxHash);

    }

    @Test
    public void getSuffix() {
        Path path = Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt");
        String fileName = path.getFileName().toString();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        log.info(suffix);
    }

    @Test
    public void getFileName() {
        Path path = Paths.get("/home/lee/workspace/java/test/file/client/src/main/resources/1.txt");
        log.info(path.getFileName().toString());
    }

//    @Test
//    public void test() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            long time = System.nanoTime();
//            int count = i;
//            log.info("{} before time is {}", count, time);
//            CompletableFuture.supplyAsync(() -> {
//                log.info("{} supplyAsync time is {}", count, time);
//                return time;
//            }).thenAcceptAsync(t -> {
//                log.info("{} thenAcceptAsync time is {}", count, time);
//            });
//        }
//        Thread.sleep(100L);
////        time = System.nanoTime();
//    }
//
//    @Test
//    public void testCompletableFutureError() throws InterruptedException {
//        CompletableFuture.supplyAsync(() -> {
//            int i =  1 / 0;
//            return i;
//        }).thenAcceptAsync(i -> {
//
//        }).exceptionally(t -> {
//            log.error("Unexpected error", t);
//            return null;
//        });
//        Thread.sleep(100L);
//    }

    @Test
    public void random() {
        Map<String, String> randomMap = new HashMap<>();
        randomMap.put("1", "1");
        randomMap.put("2", "2");

        ArrayList<String> list = new ArrayList<>(randomMap.keySet());
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            log.info(randomMap.get(list.get(random.nextInt(list.size()))));
        }
    }

    @Test
    public void getFolderSize() throws IOException {
        long size = getFolderSize(new File("/home/lee/workspace/java/test/file/2"));
        log.info("size is {}", size);
    }

    private long getFolderSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else
                size += getFolderSize(file);
        }
        return size;
    }

    @Test
    public void remoteAdress() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoop = new NioEventLoopGroup();
        bootstrap.group(eventLoop);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(
                        new ObjectEncoder(),
                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                        new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                SocketAddress socketAddress = ctx.channel().remoteAddress();
                                log.info(socketAddress.toString());
                            }
                        }
                );
            }
        });
        bootstrap.remoteAddress("localhost", 8007);
        bootstrap.connect().sync().channel().closeFuture().sync();
    }

//    @Test
//    public void startClient() throws InterruptedException {
//        ExecutorService executor = Executors.newCachedThreadPool();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Response response = Api.send(new Request(1L, 1L, "som"));
//                log.info("receive response {}", response);
//            }
//        };
//
//        for (int i = 0; i < 10; i++) {
//            executor.submit(runnable);
//        }
//        Thread.sleep(2000L);
//    }

    @Test
    public void readProperties() throws IOException {
        Properties prop = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("storage.properties");
        prop.load(in);
        log.info("property is {}", prop.getProperty("ip"));
        log.info("property is {}", prop.getProperty("port"));
    }

    @Test
    public void getConfig() {
        int max = Config.getInt(Constants.RETRY_TIMEOUT);
        log.info("retry max is {}", max);
    }
}