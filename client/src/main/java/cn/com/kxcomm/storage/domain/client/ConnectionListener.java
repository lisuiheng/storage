package cn.com.kxcomm.storage.domain.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ConnectionListener implements ChannelFutureListener {
    private final Logger log = LoggerFactory.getLogger(ConnectionListener.class);

    private Client client;
    public ConnectionListener(Client client) {
        this.client = client;
    }
    @Override
    public void operationComplete(final ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                log.info("reconnect");
                client.start();
                }
            }, 1L, TimeUnit.SECONDS);
        }
    }
}
