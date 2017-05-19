package cn.com.kxcomm.common.resourse.file;


import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Client  {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private final ClientHandler handler = new ClientHandler(this);
    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();
    private final EventLoopGroup eventLoop = new NioEventLoopGroup();

    private int retryCount = 0;
    private final int retryTimeout = Config.getInt(Constants.RETRY_TIMEOUT);
    private final int retryInterval = Config.getInt(Constants.RETRY_INTERVAL);

    public void start(String hostname, int port)  {
        start(new InetSocketAddress(hostname, port));
    }

    public synchronized void start(SocketAddress address)  {
        log.info("client start");
        if(!handler.channelIsActive()) {
            createBootstrap(address, new Bootstrap());
        }
    }

    public boolean isReady() {
        return handler.channelIsActive();
    }


    public Bootstrap createBootstrap(SocketAddress socketAddress, Bootstrap bootstrap)  {
        if (bootstrap != null) {
            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(
                            new ObjectEncoder(),
                            new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                            handler
                    );
                }
            });
            bootstrap.connect(socketAddress).addListener(new ConnectionListener(this));
        }
        return bootstrap;
    }

    public void putResponse(Response response) throws InterruptedException {
        BlockingQueue<Response> queue = responseMap.get(response.getId());
        queue.offer(response, retryTimeout * retryInterval , TimeUnit.MILLISECONDS);
    }

    public void send(Request request) {
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
        try {
            waitChannelActive();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        handler.writeAndFlush(request);
        log.debug("{} {} send", request.getClass().getName(), request);
    }

    public Response getResponse(Request request) {
//        if(isShutdown()) {
//            String errMsg = "client is shutdown";
//            log.error(errMsg);
//            throw new RuntimeException(errMsg);
//        }
        Response result;
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
        try {
            BlockingQueue<Response> responses = responseMap.get(request.getId());
            result = responses.poll(retryTimeout * retryInterval , TimeUnit.MILLISECONDS);
            if (null == result) {
                throw new RuntimeException("not result");
            }
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            responseMap.remove(request.getId());
        }
        return result;
    }

    public boolean isShutdown() {
        return eventLoop.isShutdown();
    }

    private void waitChannelActive() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while(!handler.channelIsActive()) {
            log.debug("wait channel active");
            if((System.currentTimeMillis()-startTime) > retryTimeout) {
                String errMsg = String.format("check handler channel active time out %d", retryTimeout);
                eventLoop.shutdownGracefully();
                log.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            Thread.sleep(retryInterval);
        }
    }


}
