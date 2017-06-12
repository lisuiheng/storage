package cn.com.kxcomm.storage.domain.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @class Connection listener
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public class ConnectionListener implements ChannelFutureListener {
    private final Logger log = LoggerFactory.getLogger(ConnectionListener.class);

    private Client client;
    public ConnectionListener(Client client) {
        this.client = client;
    }

    /**
     * @method Operation complete.
     * @description
     * @author 李穗恒
     * @param channelFuture the channel future
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
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
