package cn.com.kxcomm.storage.domain.manager;

import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.proxy.ConnectResponse;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants.*;

@Component
@ChannelHandler.Sharable
public final class ManagerServer implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(ManagerServer.class);

    private final ClientApi clientApi;
    private final ConnectPool connectPool;
    private final ManFrontendHandler manFrontendHandler;
    private final ManagerConfig managerConfig;



    @Autowired
    public ManagerServer(ClientApi clientApi, ConnectPool connectPool, ManFrontendHandler manFrontendHandler, ManagerConfig managerConfig) {
        this.clientApi = clientApi;
        this.connectPool = connectPool;
        this.manFrontendHandler = manFrontendHandler;
        this.managerConfig = managerConfig;
    }


    @Override
    public void run(String... strings) throws Exception {

        ConnectRequest connectRequest = new ConnectRequest(SYSTEM_HEAD_CORP_ID, SYSTEM_LOGIN_OPER_ID, SYSTEM_SYSCODE);
        ConnectResponse connectResponse = (ConnectResponse) clientApi.send(connectRequest);
        int[] ports = connectResponse.getPorts();

        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        int proxyStartPort = managerConfig.getProxyStartPort();
        for (int proxyPort : ports) {
            int managerPort = proxyStartPort++;
            final InetSocketAddress localhostAddress = new InetSocketAddress("localhost", managerPort);
            final InetSocketAddress remoteAdress = new InetSocketAddress(managerConfig.getProxyHost(), proxyPort);
            connectPool.putManagerProxyPort(managerPort, proxyPort);

            CompletableFuture.supplyAsync(() -> {
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

        }


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
                                    new ObjectDecoder(1048576 * 100, ClassResolvers.cacheDisabled(null)),
                                    manFrontendHandler);
                        }
                    })
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(managerConfig.getPort()).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

//            try {
//                ServerBootstrap b = new ServerBootstrap();
//                b.group(bossGroup, workerGroup)
//                        .channel(NioServerSocketChannel.class)
////                          .handler(new LoggingHandler(LogLevel.INFO))
//                          .childHandler(new ChannelInitializer() {
//                              @Override
//                              protected void initChannel(Channel ch) throws Exception {
//                                  ch.pipeline().addLast(
//                                      new FrontendHandler(new InetSocketAddress("localhost", 8006)));
//                              }
//                          })
//                        .childOption(ChannelOption.AUTO_READ, false)
//                        .bind(localPort).sync().channel().closeFuture().sync();
//            } catch (Exception e) {
//                throw new RuntimeException(e.getMessage());
//            }
//            finally {
//                bossGroup.shutdownGracefully();
//                workerGroup.shutdownGracefully();
//            }

    }
}