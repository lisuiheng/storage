package cn.com.kxcomm.storage.domain.proxy;



import cn.com.kxcomm.storage.domain.service.server.service.FileServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @class Proxy server
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Component
public class ProxyServer implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ProxyServer.class);

    private final ProxyConfig proxyConfig;
    private final ConnectPool connectPool;

    private final ProxyHandler proxyHandler;

    @Autowired
    public ProxyServer(ProxyConfig proxyConfig, FileServerService fileServer, ConnectPool connectPool, ProxyHandler proxyHandler) {
        this.proxyConfig = proxyConfig;
        this.connectPool = connectPool;
        this.proxyHandler = proxyHandler;
    }


    /**
     * @method Run.
     * @description
     * @author 李穗恒
     * @param strings the strings
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Override
    public void run(String... strings) throws Exception {
        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Map<Long, InetSocketAddress> storageRemoteAddressMap = connectPool.getStorageRemoteAddressMap();
        if(storageRemoteAddressMap.size() == 0) {
            log.warn("storage remote address is 0");
        }
        storageRemoteAddressMap.forEach((storageId, remoteAdress) -> {
            CompletableFuture.supplyAsync(() -> {
                SocketAddress localhostAddress = connectPool.getStorageLocalAddressMap().get(storageId);
                ServerBootstrap b = new ServerBootstrap();
                try {
                    log.info("listen {} for {}", localhostAddress, remoteAdress);
                    b.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .childHandler(new ChannelInitializer() {
                                @Override
                                protected void initChannel(Channel ch) throws Exception {
                                    ch.pipeline().addLast(
                                            new ObjectEncoder(),
                                            new ObjectDecoder(1048576 * 101, ClassResolvers.cacheDisabled(null)),
                                            new FrontendHandler(remoteAdress)
                                    );
                                }
                            })
                            .childOption(ChannelOption.AUTO_READ, false)
                            .bind(localhostAddress).sync().channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }).exceptionally(throwable -> {
                log.error(throwable.getMessage());
                return null;
            });
        });


        log.info("proxy listen {}", proxyConfig.getPort());
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(1048576 * 101, ClassResolvers.cacheDisabled(null)),
                                    proxyHandler
                            );
                        }
                    })
                    .bind(proxyConfig.getPort()).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
//
//        // Configure the bootstrap.
//        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
////                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .childHandler(new ChannelInitializer() {
//                        @Override
//                        protected void initChannel(Channel ch) throws Exception {
//                            ch.pipeline().addLast(
//                                    new FrontendHandler(new InetSocketAddress("localhost", 8007)));
//                        }
//                    })
//                    .childOption(ChannelOption.AUTO_READ, false)
//                    .bind(8006).sync().channel().closeFuture().sync();
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//        finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
    }


}
