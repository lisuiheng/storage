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
 * @class FileProvide
 * @author 徐琼
 * @create Date 2016年3月29日 上午9:42:11
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 001.00.00
 * @description 文件管理
 */
public interface FileProvide {

    Long upload(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    Long upload(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    /**
     *
     * @method upload
     * @description  上传
     * @author 徐琼
     * @param file 文件
     * @param file 存储数
     * @param loginOperId 登陆人
     * @param headCorpId 归属集团编号
     * @param platformCode 平台编码
     * @param platformKey 平台密钥
     * @param sysCode 系统编码
     * @param sysKey 系统密钥
     * @return 文件ID
     * @create Date 2016年4月22日 下午6:42:28
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @version 001.00.00
     */
    Long upload(File file,Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey,
                String sysCode, String sysKey) throws IOException, StorageException;


    Long upload(Path path, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    Long uploadAsync(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    Long uploadAsync(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    Long uploadAsync(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    Long uploadAsync(Path path, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException;

    Long uploadByMD5(String fileName, String md5, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException;

    /**
     *
     * @method downLoad
     * @description  下载
     * @author 徐琼
     * @param fileCode 文件编码
     * @param headCorpId 归属集团编号
     * @param loginOperId 登陆人
     * @param platformCode 平台编码
     * @param platformKey 平台密钥
     * @param sysCode 系统编码
     * @param sysKey 系统密钥
     * @return 文件地址
     * @create Date 2016年4月29日 上午9:07:22
     * @modified By 李穗恒
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @version 002.00.00
     */
    byte[] downLoad(Long fileCode, Long headCorpId, Long loginOperId,
                    String platformCode, String platformKey, String sysCode,
                    String sysKey) throws StorageException;


    /**
     *
     * @method downLoad
     * @description  下载
     * @author 徐琼
     * @param fileCode 文件编码
     * @param targetPath 下载目标路径
     * @param headCorpId 归属集团编号
     * @param loginOperId 登陆人
     * @param platformCode 平台编码
     * @param platformKey 平台密钥
     * @param sysCode 系统编码
     * @param sysKey 系统密钥
     * @return 文件地址
     * @create Date 2016年4月29日 上午9:07:22
     * @modified By 李穗恒
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @version 002.00.00
     */
    void downLoad(Long fileCode, Path targetPath, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException;

    void deleteFileByCode(Long fileViewCode, Long loginOperId, Long headCorpId,
                             String platformCode, String platformKey, String sysCode,
                             String sysKey) throws StorageException;
}