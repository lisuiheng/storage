package cn.com.kxcomm.storage.domain.client;

import java.io.IOException;
import java.util.Properties;

/**
 * @class Config
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public class Config {
    private static Properties prop;



    private Config() {}

    public static void load() {
        load(Constants.CONFIG_PATH);
    }

    public static void load(String path) {
        try {
            prop = new Properties();
            prop.load(Config.class.getClassLoader().getResourceAsStream(path));
            validate();
        } catch (IOException e) {
            throw new RuntimeException(String.format("no config %s", path));
        }
    }

    private static void validate() {
        getString(Constants.REMOTE_HOSTNAME);
        getInt(Constants.REMOTE_PORT);
        getInt(Constants.RETRY_INTERVAL);
        getInt(Constants.RETRY_TIMEOUT);
    }


    /**
     * @method Get string string.
     * @description
     * @author 李穗恒
     * @return the string
     * @param key the key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    private static String getString(String key) {
        return (String) getProp(key);
    }

    /**
     * @method Get int int.
     * @description
     * @author 李穗恒
     * @return the int
     * @param key the key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    private static Integer getInt(String key) {
        return Integer.parseInt((String) getProp(key));
    }


    private static Object getProp(String key) {
        try {
            if(prop == null) {
                load();
            }
            Object value = prop.get(key);
            if(value.toString().trim().equals("")) {
                throw new RuntimeException(String.format("%s is null", key));
            }
            return value;
        } catch (NumberFormatException | NullPointerException e) {
            throw new RuntimeException(String.format("%s is null", key));
        }
    }


    /**
     * @method Get remote hostname string.
     * @description
     * @author 李穗恒
     * @return the string
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public static String getRemoteHostname() {
        return getString(Constants.REMOTE_HOSTNAME);
    }

    /**
     * @method Get remote port int.
     * @description
     * @author 李穗恒
     * @return the int
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public static int getRemotePort() {
        return getInt(Constants.REMOTE_PORT);
    }

    /**
     * @method Get retry interval int.
     * @description
     * @author 李穗恒
     * @return the int
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public static int getRetryInterval() {
        return getInt(Constants.RETRY_INTERVAL);
    }

    /**
     * @method Get retry time out int.
     * @description
     * @author 李穗恒
     * @return the int
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    public static int getRetryTimeOut() {
        return getInt(Constants.RETRY_TIMEOUT);
    }



}
