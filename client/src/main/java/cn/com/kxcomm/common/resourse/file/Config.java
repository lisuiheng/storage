package cn.com.kxcomm.common.resourse.file;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private final static Properties prop;

    static {
        try {
            prop = new Properties();
            prop.load(Config.class.getClassLoader().getResourceAsStream("storage.properties"));
        } catch (IOException e) {
            throw new RuntimeException(String.format("no config %s", "storage.properties"));
        }
    }

    public static String getString(String key) {
        return (String) prop.get(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt((String) prop.get(key));
    }
}
