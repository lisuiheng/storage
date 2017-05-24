package cn.com.kxcomm.storage.domain.proxy;

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

@Component
public class ProxyServer implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ProxyServer.class);

    private final ProxyConfig proxyConfig;

    private final FrontendHandler frontendHandler;

    @Autowired
    public ProxyServer(ProxyConfig proxyConfig, FrontendHandler frontendHandler) {
        this.proxyConfig = proxyConfig;
        this.frontendHandler = frontendHandler;
    }


    @Override
    public void run(String... strings) throws Exception {

        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
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
                                    frontendHandler
                            );
                        }
                    })
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(proxyConfig.getPort()).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
