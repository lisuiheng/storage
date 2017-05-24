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
import java.util.concurrent.CompletableFuture;

@Component
public class ProxyServer implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ProxyServer.class);

    private final ProxyConfig proxyConfig;
    private final ProxyClientManager proxyClientManager;

    private final SelectHandler selectHandler;

    @Autowired
    public ProxyServer(ProxyConfig proxyConfig, FileServerService fileServer, ProxyClientManager proxyClientManager, SelectHandler selectHandler) {
        this.proxyConfig = proxyConfig;
        this.proxyClientManager = proxyClientManager;
        this.selectHandler = selectHandler;
    }


    @Override
    public void run(String... strings) throws Exception {
        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        proxyClientManager.getStorageRemoteAddressMap().forEach((storageId, remoteAdress) -> {
            CompletableFuture.supplyAsync(() -> {
                SocketAddress localhostAddress = proxyClientManager.getStorageLocalAddressMap().get(storageId);
                ServerBootstrap b = new ServerBootstrap();
                try {
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
                    log.info("listen {} for {}", localhostAddress, remoteAdress);
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
                                    selectHandler
                            );
                        }
                    })
                    .bind(proxyConfig.getPort()).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
