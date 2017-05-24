//package cn.com.kxcomm.storage.domain.proxy;
//
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.serialization.ClassResolvers;
//import io.netty.handler.codec.serialization.ObjectDecoder;
//import io.netty.handler.codec.serialization.ObjectEncoder;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//
//import java.net.InetSocketAddress;
//
//public final class HexDumpProxy {
//
//    static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "8300"));
//    static final String REMOTE_HOST = System.getProperty("remoteHost", "localhost");
//    static final int REMOTE_PORT = Integer.parseInt(System.getProperty("remotePort", "8007"));
//
//    public static void main(String[] args) throws Exception {
//        System.err.println("Proxying *:" + LOCAL_PORT + " to " + REMOTE_HOST + ':' + REMOTE_PORT + " ...");
//
//        // Configure the bootstrap.
//        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .childHandler(new ChannelInitializer() {
//                        @Override
//                        protected void initChannel(Channel ch) throws Exception {
//                            ch.pipeline().addLast(
//                                    new ObjectEncoder(),
//                                    new ObjectDecoder( ClassResolvers.cacheDisabled(null)),
//                                    new HexDumpProxyFrontendHandler("localhost", 8007)
//                            );
//                        }
//                    })
//                    .childOption(ChannelOption.AUTO_READ, false)
//                    .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//}