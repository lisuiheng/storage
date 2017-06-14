package cn.com.kxcomm.storage.domain.client;


import cn.com.kxcomm.storage.domain.client.common.StorageException;
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


/**
 * @class Client
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public class Client  {
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private final ClientHandler handler = new ClientHandler(this);
    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();
    private final EventLoopGroup eventLoop = new NioEventLoopGroup();

    private int retryCount = 0;
    private final int retryTimeout;
    private final int retryInterval;
    private final SocketAddress address;


    public Client(String hostname, int port) {
        this(new InetSocketAddress(hostname, port));
    }


    Client(SocketAddress address) {
        this.address = address;
        this.retryTimeout = Config.getRetryTimeOut();
        this.retryInterval = Config.getRetryInterval();
        start();
    }


    /**
     * @method Start.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    void start()  {
        log.info("client start connect remote server {}", address);
        if(!handler.channelIsActive()) {
            createBootstrap(address, new Bootstrap());
        }
    }


    /**
     * @method Is ready boolean.
     * @description
     * @author 李穗恒
     * @return the boolean
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public boolean isReady() {
        return handler.channelIsActive();
    }

    /**
     * @method Create bootstrap bootstrap.
     * @description
     * @author 李穗恒
     * @return the bootstrap
     * @param socketAddress the socket address
     * @param bootstrap the bootstrap
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Bootstrap createBootstrap(SocketAddress socketAddress, Bootstrap bootstrap)  {
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
//                            new ChunkedWriteHandler(),
                            handler
                    );
                }
            });
            bootstrap.connect(socketAddress).addListener(new ConnectionListener(this));
        }
        return bootstrap;
    }

    /**
     * @method Put response.
     * @description
     * @author 李穗恒
     * @param response the response
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    void putResponse(Response response) throws InterruptedException {
        BlockingQueue<Response> queue = responseMap.get(response.getId());
        queue.offer(response, retryTimeout * retryInterval , TimeUnit.MILLISECONDS);
    }

    /**
     * @method Send.
     * @description
     * @author 李穗恒
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    void send(Request request) {
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
        try {
            waitChannelActive();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        handler.writeAndFlush(request);
        log.debug("{} {} send", request.getClass().getName(), request);
    }

    /**
     * @method Get response response.
     * @description
     * @author 李穗恒
     * @return the response
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Response getResponse(Request request) throws StorageException {
        Response result;
        responseMap.putIfAbsent(request.getId(), new LinkedBlockingQueue<Response>(1));
        try {
            BlockingQueue<Response> responses = responseMap.get(request.getId());
            result = responses.poll(retryTimeout * retryInterval , TimeUnit.MILLISECONDS);
            if (null == result) {
                throw new StorageException("request time out not result");
            }
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            responseMap.remove(request.getId());
        }
        return result;
    }

    /**
     * @method Is shutdown boolean.
     * @description
     * @author 李穗恒
     * @return the boolean
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public boolean isShutdown() {
        return eventLoop.isShutdown();
    }

    /**
     * @method Wait channel active.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
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

    /**
     * @method Get connet adress socket address.
     * @description
     * @author 李穗恒
     * @return the socket address
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public SocketAddress getConnetAdress() {
        return address;
    }


}
