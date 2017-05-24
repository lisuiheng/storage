package cn.com.kxcomm.common.utils;

import java.util.Date;

public class SysSequenceUtil {
    public static Long getSequenceId(String sequenceCode) {
        return System.currentTimeMillis();
    }
}
