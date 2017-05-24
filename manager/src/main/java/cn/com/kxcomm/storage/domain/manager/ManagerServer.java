package cn.com.kxcomm.storage.domain.manager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
@PropertySource(value = "classpath:manager.properties")
public final class ManagerServer implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ManagerServer.class);

    private final FrontendHandler frontendHandler;

    @Value("${local.port}")
    private int localPort;
    @Value("${remote.host}")
    private String remoteHost;
    @Value("${remote.port}")
    private int remotePort;

    @Autowired
    public ManagerServer(FrontendHandler frontendHandler) {
        this.frontendHandler = frontendHandler;
    }


    @Override
    public void run(String... strings) throws Exception {
//        CompletableFuture.supplyAsync(() -> {
            log.info("Proxying *:" + localPort + " to " + remoteHost + ':' + remotePort + " ...");
            // Configure the bootstrap.
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                          .handler(new LoggingHandler(LogLevel.INFO))
//                        .childHandler(new HexDumpProxyInitializer(REMOTE_HOST, REMOTE_PORT))
                          .childHandler(new ChannelInitializer() {
                              @Override
                              protected void initChannel(Channel ch) throws Exception {
                                  ch.pipeline().addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(1048576 * 100, ClassResolvers.cacheDisabled(null)),
                                    frontendHandler);
                              }
                          })
                        .childOption(ChannelOption.AUTO_READ, false)
                        .bind(localPort).sync().channel().closeFuture().sync();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
//            return null;
//        }).exceptionally(e -> {
//            log.error(e.getMessage());
//            return null;
//        });

    }
}