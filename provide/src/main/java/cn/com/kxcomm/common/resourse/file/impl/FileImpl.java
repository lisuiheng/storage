package cn.com.kxcomm.common.resourse.file.impl;

import cn.com.kxcomm.common.resourse.file.FileProvide;
import cn.com.kxcomm.common.resourse.file.ProvideConfig;
import cn.com.kxcomm.storage.domain.client.ClientApi;
import cn.com.kxcomm.storage.domain.client.Config;
import cn.com.kxcomm.storage.domain.client.common.StorageException;
import cn.com.kxcomm.storage.domain.storage.common.constants.ShareConstants;
import cn.com.kxcomm.storage.domain.storage.common.utils.MD5Util;
import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.*;
import cn.com.kxcomm.storage.domain.storage.share.bean.manager.FileGetNameRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.manager.FileGetNameResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.manager.FileRenameRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.manager.FileRenameResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest1;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.com.kxcomm.common.resourse.file.Constants.CONFIG_PATH;


/**
 * @class File
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.7
 * @version 002.00.00
 * @description
 */
public class FileImpl implements FileProvide {
    private final Logger log = LoggerFactory.getLogger(FileImpl.class);

    private final ClientApi managerClient;
    private final ConcurrentHashMap<Integer, ClientApi> transportClientMap;
    private final ExecutorService uploadAsyncExecutor;

    public FileImpl() {
        //load client config
        Config.load(CONFIG_PATH);
        //load provide config
        ProvideConfig.load();
        managerClient = new ClientApi();
        transportClientMap = new ConcurrentHashMap<>();
        uploadAsyncExecutor = Executors.newFixedThreadPool(ProvideConfig.getUploadAsyncExecutorSize());
    }

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
    @Override
    public Long uploadSyn(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return uploadSyn(Paths.get(file.getPath()), ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

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
    @Override
    public Long uploadSyn(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return uploadSyn(path, ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

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
    @Override
    public Long uploadSyn(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return uploadSyn(Paths.get(file.getPath()), storageCount, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

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
    @Override
    public Long uploadSyn(Path path, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        //check md5 exit if exit get fileViewCode , else upload file and get fileViewCode
        String fileName = path.getFileName().toString();
        byte[] data = Files.readAllBytes(path);
        String md5 = MD5Util.md5(data);
        UploadMD5Request1 uploadMD5Request1 = new UploadMD5Request1(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response1 uploadMD5Response1 = (UploadMD5Response1) managerClient.send(uploadMD5Request1);
        Long fileViewCode = uploadMD5Response1.getFileViewCode();

        if(!uploadMD5Response1.isFileExit()) {
            uploadFile(fileName, md5, data, fileViewCode, storageCount, loginOperId, headCorpId, sysCode);
        }
        return fileViewCode;
    }



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
    @Override
    public Long upload(File file, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return upload(Paths.get(file.getPath()), ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

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
    @Override
    public Long upload(Path path, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return upload(path, ShareConstants.STORAGE_COUNT_DEFAULT, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

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
    @Override
    public Long upload(File file, Long storageCount, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws IOException, StorageException {
        return upload(Paths.get(file.getPath()), storageCount, loginOperId, headCorpId, platformCode, platformKey, sysCode, sysKey);
    }

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
    @Override
    public Long upload(Path path, final Long storageCount, final Long loginOperId, final Long headCorpId, String platformCode, String platformKey, final String sysCode, String sysKey) throws IOException, StorageException {
        /**
         * check md5 exit if exit get fileViewCode , else upload file and get fileViewCode
         * this method is asynchronous,will upload fail and no error return
         */
        final String fileName = path.getFileName().toString();
        final byte[] data = Files.readAllBytes(path);
        final String md5 = MD5Util.md5(data);
        UploadMD5Request1 uploadMD5Request1 = new UploadMD5Request1(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response1 uploadMD5Response1 = (UploadMD5Response1) managerClient.send(uploadMD5Request1);
        final Long fileViewCode = uploadMD5Response1.getFileViewCode();

        if(!uploadMD5Response1.isFileExit()) {
            uploadAsyncExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        uploadFile(fileName, md5, data, fileViewCode, storageCount, loginOperId, headCorpId, sysCode);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            });
        }
        return fileViewCode;
    }

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
    @Override
    public Long uploadByMD5(String fileName, String md5, Long loginOperId, Long headCorpId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException {
        UploadMD5Request1 uploadMD5Request1 = new UploadMD5Request1(fileName, md5, headCorpId, loginOperId, sysCode);
        UploadMD5Response1 uploadMD5Response1 = (UploadMD5Response1) managerClient.send(uploadMD5Request1);
        return uploadMD5Response1.getFileViewCode();
    }

    /**
     * @method Upload file.
     * @description
     * @author 李穗恒
     * @param fileName the file name
     * @param md5 the md 5
     * @param data the data
     * @param fileViewCode the file view code
     * @param storageCount the storage count
     * @param loginOperId the login oper id
     * @param headCorpId the head corp id
     * @param sysCode the sys code
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    private void uploadFile(String fileName, String md5, byte[] data, long fileViewCode, Long storageCount, Long loginOperId, Long headCorpId, String sysCode) throws IOException, StorageException {
        PreUploadRequest1 preUploadRequest1 = new PreUploadRequest1(data.length, headCorpId, loginOperId, sysCode);
        PreUploadResponse1 preUploadResponse1 = (PreUploadResponse1) managerClient.send(preUploadRequest1);
        int uploadPort = preUploadResponse1.getUploadPort();
        UploadRequest3 uploadRequest3 = new UploadRequest3(fileName, data, headCorpId, loginOperId, sysCode);
        UploadResponse3 uploadResponse3 = (UploadResponse3) transportClient(uploadPort).send(uploadRequest3);
        long storageId = preUploadResponse1.getStorageId();
        String storagePath = uploadResponse3.getRelativePath();
        UploadInfoRequest1 uploadInfoRequest1 = new UploadInfoRequest1(fileName, data.length, md5, storageCount, fileViewCode, storageId, storagePath, uploadRequest3);
        managerClient.send(uploadInfoRequest1);
    }

    /**
     * @method Down load url.
     * @description
     * @author 李穗恒
     * @return url
     * @param fileCode the file code
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
    @Override
    public String downLoad(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException {
        Path downloadDir =  Paths.get(ProvideConfig.getDownloadPath());
        String fileName = getFileNameByCode(fileCode, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
        downLoadRename(fileCode, downloadDir, fileName, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
        return downloadDir.resolve(fileName).toString();
    }

    @Override
    public void downLoad(Long fileCode, Path targetDir, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException {
        String fileName = getFileNameByCode(fileCode, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
        downLoadRename(fileCode, targetDir, fileName, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
    }

    @Override
    public void downLoadRename(Long fileCode, Path targetDir, String targetFileName, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException {
        Path path = targetDir.resolve(targetFileName);
        downLoadRename(fileCode, path, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);

    }

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
    @Override
    public void downLoadRename(Long fileCode, Path targetPath, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException, IOException {
        byte[] data = downLoadByte(fileCode, headCorpId, loginOperId, platformCode, platformKey, sysCode, sysKey);
        Files.write(targetPath, data);
    }

    /**
     * @method Down load byte [ ].
     * @description
     * @author 李穗恒
     * @return the byte [ ]
     * @param fileCode the file code
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
    @Override
    public byte[] downLoadByte(Long fileCode, Long headCorpId, Long loginOperId, String platformCode, String platformKey, String sysCode, String sysKey) throws StorageException {
        PreDownloadRequest1 preDownloadRequest1 = new PreDownloadRequest1(fileCode, headCorpId, loginOperId, sysCode);
        PreDownloadResponse1 preDownloadResponse1 = (PreDownloadResponse1) managerClient.send(preDownloadRequest1);

        int downloadPort = preDownloadResponse1.getDownloadPort();
        String path = preDownloadResponse1.getPath();

        DownloadRequest3 downloadRequest3 = new DownloadRequest3(path, new Request(headCorpId, loginOperId, sysCode));
        DownloadResponse3 downloadResponse3 = (DownloadResponse3) transportClient(downloadPort).send(downloadRequest3);
        return downloadResponse3.getData();
    }


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
    @Override
    public void deleteFileByCode(Long fileViewCode, Long loginOperId, Long headCorpId,
                                 String platformCode, String platformKey, String sysCode,
                                 String sysKey) throws StorageException {
        RemoveRequest1 removeRequest1 = new RemoveRequest1(fileViewCode, headCorpId, loginOperId, sysCode);
        managerClient.send(removeRequest1);
    }

    @Override
    public String getFileNameByCode(Long fileViewCode, Long loginOperId, Long headCorpId,
                                    String platformCode, String platformKey, String sysCode,
                                    String sysKey) throws StorageException {
        FileGetNameRequest fileGetNameRequest = new FileGetNameRequest(fileViewCode, headCorpId, loginOperId, sysCode);
        FileGetNameResponse fileGetNameResponse = (FileGetNameResponse) managerClient.send(fileGetNameRequest);
        return fileGetNameResponse.getFileName();
    }

    @Override
    public void renameByCode(Long fileViewCode, String newFileName, Long loginOperId, Long headCorpId,
                             String platformCode, String platformKey, String sysCode,
                             String sysKey) throws StorageException {
        FileRenameRequest fileRenameRequest = new FileRenameRequest(fileViewCode, newFileName, headCorpId, loginOperId, sysCode);
        FileRenameResponse fileRenameResponse = (FileRenameResponse) managerClient.send(fileRenameRequest);
    }


    /**
     * @method Transport client client api.
     * @description
     * @author 李穗恒
     * @return the client api
     * @param port the port
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.7
     * @version 002.00.00
     */
    private ClientApi transportClient(int port) {
        ClientApi clientApi = transportClientMap.get(port);
        if(clientApi == null) {
            clientApi = new ClientApi(Config.getRemoteHostname(), port);
            transportClientMap.putIfAbsent(port, clientApi);
        }
        return clientApi;
    }
}
