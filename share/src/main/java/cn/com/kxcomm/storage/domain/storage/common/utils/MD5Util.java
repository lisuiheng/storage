package cn.com.kxcomm.storage.domain.storage.common.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String MD5_ALGORITHM_NAME = "MD5";
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String md5(byte[] data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM_NAME);
            byte[] bytes = messageDigest.digest(data);
            char[] chars = new char[32];

            for(int i = 0; i < chars.length; i += 2) {
                byte b = bytes[i / 2];
                chars[i] = HEX_CHARS[b >>> 4 & 15];
                chars[i + 1] = HEX_CHARS[b & 15];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + MD5_ALGORITHM_NAME + "\"", var2);
        }
    }
}
