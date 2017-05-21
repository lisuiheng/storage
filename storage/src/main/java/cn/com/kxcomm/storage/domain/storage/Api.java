package cn.com.kxcomm.storage.domain.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadRequest3;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse2;
import cn.com.kxcomm.storage.domain.storage.share.bean.download.DownloadResponse3;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceRequest;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.SpaceResponse;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadRequest2;
import cn.com.kxcomm.storage.domain.storage.share.bean.upload.UploadResponse2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Api {
    private final FileAgent fileAgent;
    private final StorageConfig storageConfig;

    private final Logger log = LoggerFactory.getLogger(Api.class);

    public Api(FileAgent fileAgent, StorageConfig storageConfig) {
        this.fileAgent = fileAgent;
        this.storageConfig = storageConfig;
    }

    public UploadResponse2 upload(UploadRequest2 request) throws IOException {
        String fileName = request.getFileName();
        byte[] data = request.getData();

        Long storageId = storageConfig.getFileStorageId();
        String path = fileAgent.writeByte(fileName, data);
        long size = data.length;
        String md5 = fileAgent.md5(data);

        return new UploadResponse2(storageId, path, size, md5, request);
    }

    public DownloadResponse3 download(DownloadRequest3 request) throws IOException {
        byte[] data;
        String path = request.getPath();
        data = fileAgent.readAllBytes(path);
        return new DownloadResponse3(data, request);
    }

    public SpaceResponse space(SpaceRequest request) {
        String relativePath = request.getRelativePath();
        File dir = new File(storageConfig.getPath().toString(), relativePath);
        return new SpaceResponse(dir.getFreeSpace(), dir.getUsableSpace(), dir.getTotalSpace(), request);
    }

    public ListFileResponse listFile(ListFileRequest request) {
        String relativePath = request.getRelativePath();
        FileTime formTime = FileTime.fromMillis(request.getLastModifiedTime());

        final ListFileResponse response = new ListFileResponse(request);
        Path path = storageConfig.getPath().resolve(relativePath);

        int subLength = storageConfig.getPath().toString().length() + 1;
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
                        String md5 = fileAgent.md5(data);
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


}
