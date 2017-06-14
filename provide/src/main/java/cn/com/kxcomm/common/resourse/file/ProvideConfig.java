package cn.com.kxcomm.common.resourse.file;


import java.io.IOException;
import java.util.Properties;



/**
 * @class ProvideConfig
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public class ProvideConfig {
    private static Properties prop;
    private static int UPLOAD_ASYNC_EXECUTOR_SIZE;
    private static String DOWNLOAD_PATH;

    private ProvideConfig() {}

    public static void load() {
        load(Constants.CONFIG_PATH);
    }

    public static void load(String path) {
        try {
            prop = new Properties();
            prop.load(ProvideConfig.class.getClassLoader().getResourceAsStream(path));
            UPLOAD_ASYNC_EXECUTOR_SIZE = getInt(Constants.UPLOAD_ASYNC_EXECUTOR_SIZE);
            DOWNLOAD_PATH = getString(Constants.DOWNLOAD_PATH);
        } catch (IOException e) {
            throw new RuntimeException(String.format("no config %s", path));
        }
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
     * @method Get upload async executor size int.
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
    public static int getUploadAsyncExecutorSize() {
        return UPLOAD_ASYNC_EXECUTOR_SIZE;
    }

    public static String getDownloadPath() {
        return DOWNLOAD_PATH;
    }

}
