package cn.com.kxcomm.storage.domain.client;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private final static Properties prop;

    static {
        try {
            prop = new Properties();
            prop.load(Config.class.getClassLoader().getResourceAsStream("client.properties"));
        } catch (IOException e) {
            throw new RuntimeException(String.format("no config %s", "storage.properties"));
        }
    }

    private static String getString(String key) {
        return (String) prop.get(key);
    }

    private static int getInt(String key) {
        return Integer.parseInt((String) prop.get(key));
    }

    public static String getRemoteHostname() {
        return getString(Constants.REMOTE_HOSTNAME);
    }

    public static int getRemotePort() {
        return getInt(Constants.REMOTE_PORT);
    }

    public static int getRetryInterval() {
        return getInt(Constants.RETRY_INTERVAL);
    }

    public static int getRetryTimeOut() {
        return getInt(Constants.RETRY_TIMEOUT);
    }
}
