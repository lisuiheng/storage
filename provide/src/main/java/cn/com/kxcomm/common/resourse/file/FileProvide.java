/**
 * Copyright 2016
 * 北京市康讯通讯设备有限公司
 * All right reserved.
 */
package cn.com.kxcomm.common.resourse.file;

import cn.com.kxcomm.storage.domain.client.common.StorageException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


/**
 * @class File provide
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public interface FileProvide {

    /**
     * @method Upload long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param file the file
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long upload(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param path the path
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long upload(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param file the file
     * @param storageCount the storage count
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long upload(File file,Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey,
                String sysCode, String sysKey) throws IOException, StorageException;


    /**
     * @method Upload long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param path the path
     * @param storageCount the storage count
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long upload(Path path, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload async long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param file the file
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long uploadAsync(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload async long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param path the path
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long uploadAsync(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload async long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param file the file
     * @param storageCount the storage count
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long uploadAsync(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload async long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param path the path
     * @param storageCount the storage count
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long uploadAsync(Path path, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     * @method Upload by md 5 long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param fileName the file name
     * @param md5 the md 5
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    Long uploadByMD5(String fileName, String md5, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException;

    String downLoad(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException;


    byte[] downLoadByte(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException;

    /**
     * @method Down load.
     * @description
     * @author 李穗恒
     * @param fileCode the file code
     * @param targetPath the target path
     * @param headCorpId the head corp id
     * @param loginOperId the login oper id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    void downLoad(Long fileCode, Path targetPath, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException;

    /**
     * @method Delete file by code.
     * @description
     * @author 李穗恒
     * @param fileViewCode the file view code
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param platformCode the platform code
     * @param platformKey the platform key
     * @param sysCode the sys code
     * @param sysKey the sys key
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    void deleteFileByCode(Long fileViewCode, Long loginOperId, Long headCorpId,
                             String platformCode, String platformKey, String sysCode,
                             String sysKey) throws StorageException;
}