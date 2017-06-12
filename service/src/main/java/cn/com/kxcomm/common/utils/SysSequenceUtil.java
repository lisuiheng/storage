package cn.com.kxcomm.common.utils;

import java.util.Date;

/**
 * @class Sys sequence util
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class SysSequenceUtil {
    /**
     * @param sequenceCode the sequence code
     * @return the long
     * @method Get sequence id long.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    public static Long getSequenceId(String sequenceCode) {
        return System.currentTimeMillis();
    }
}
