package cn.com.kxcomm.storage.domain.storage;

import cn.com.kxcomm.storage.domain.storage.common.utils.MD5Util;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.remove.RemoveResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * @class Api
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
@Component
public class Api {
    private final FileAgent fileAgent;
    private final StorageConfig storageConfig;

    private final Logger log = LoggerFactory.getLogger(Api.class);

    public Api(FileAgent fileAgent, StorageConfig storageConfig) {
        this.fileAgent = fileAgent;
        this.storageConfig = storageConfig;
    }

    /**
     * @method Upload upload response 3.
     * @description
     * @author 李穗恒
     * @return the upload response 3
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    UploadResponse3 upload(UploadRequest3 request) throws IOException {
        String fileName = request.getFileName();
        byte[] data = request.getData();

        String path = fileAgent.writeByte(fileName, data);
        long size = data.length;
        String md5 = MD5Util.md5(data);

        return new UploadResponse3(path, request);
    }

    /**
     * @method Download download response 3.
     * @description
     * @author 李穗恒
     * @return the download response 3
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    DownloadResponse3 download(DownloadRequest3 request) throws IOException {
        byte[] data;
        String path = request.getPath();
        data = fileAgent.readAllBytes(path);
        return new DownloadResponse3(data, request);
    }

    /**
     * @method Space space response.
     * @description
     * @author 李穗恒
     * @return the space response
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    SpaceResponse space(SpaceRequest request) {
        String relativePath = request.getRelativePath();
        File dir = new File(storageConfig.getPath().toString(), relativePath);
        return new SpaceResponse(dir.getFreeSpace(), dir.getUsableSpace(), dir.getTotalSpace(), request);
    }

    /**
     * @method List file list file response.
     * @description
     * @author 李穗恒
     * @return the list file response
     * @param request the request
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    ListFileResponse listFile(ListFileRequest request) {
        String relativePath = request.getRelativePath();
        FileTime formTime = FileTime.fromMillis(request.getLastModifiedTime());

        final ListFileResponse response = new ListFileResponse(request);
        Path path = storageConfig.getPath(relativePath);

        int subLength = path.toString().length() + 1;
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    FileTime lastModifiedTime = Files.getLastModifiedTime(file);
                    //skip file time before formTime
                    if(formTime.compareTo(lastModifiedTime) < 0) {
                        String allName = file.toString();

                        String relativeName = allName.substring(subLength, allName.length());

                        byte[] data = Files.readAllBytes(file);
                        String md5 = MD5Util.md5(data);
                        response.append(relativeName, data.length, md5, lastModifiedTime.toMillis());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // Skip folders that can't be traversed
                    log.warn("skipped: {}, ({})", file, exc);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    // Ignore errors traversing a folder
                    if (exc != null) {
                        log.warn("had trouble traversing: {}, ({})", dir, exc);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return new ListFileResponse(e, request);
        }
        return response;
    }


    /**
     * @method Remove remove response 3.
     * @description
     * @author 李穗恒
     * @return the remove response 3
     * @param removeRequest3 the remove request 3
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    RemoveResponse3 remove(RemoveRequest3 removeRequest3) {
        String relativePath = removeRequest3.getRelativePath();
        Path path = storageConfig.getPath(relativePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            return new RemoveResponse3(e, removeRequest3);
        }
        return new RemoveResponse3(removeRequest3);
    }



}
