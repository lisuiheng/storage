package cn.com.kxcomm.storage.domain.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FileAgent {
    private SimpleDateFormat dirFormat;
    private SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private Random random = new Random();

    private final Logger log = LoggerFactory.getLogger(FileAgent.class);

    private final StorageConfig storageConfig;

    public FileAgent(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
        dirFormat = new SimpleDateFormat(storageConfig.getDirFormat());
    }


    public long getFolderSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else
                size += getFolderSize(file);
        }
        return size;
    }


    public void writeByte(byte[] data) throws IOException {
        writeByte((String) null, data);
    }


    public String writeByte(String fileName, byte[] data) throws IOException {
        Path basePath = storageConfig.getPath();

        String dirFormat;
        synchronized (this) {
            dirFormat = this.dirFormat.format(new Date());
        }
        Path dirPath = basePath.resolve(dirFormat);
        Files.createDirectories(dirPath);

        StringBuilder fileNameBuilder = new StringBuilder(fileNameFormat.format(new Date()));
        for (int i = 0; i < 3; i++) {
            fileNameBuilder.append(random.nextInt(10));
        }
        if(fileName != null) {
            //get suffix
            int lastIndexOf = fileName.lastIndexOf(".");
            String suffix = fileName.substring(lastIndexOf + 1, fileName.length());
            fileNameBuilder.append(".").append(suffix);
        }
        fileName = fileNameBuilder.toString();
        Path filePath = dirPath.resolve(fileName);
        writeByte(filePath, data);
        return dirFormat + "/" + fileName;
    }

    private void writeByte(Path filePath, byte[] data) throws IOException {
        if(Files.exists(filePath)) {
            throw new RuntimeException(String.format("file %s already exit", filePath));
        }
        Files.write(filePath, data);
    }


    public byte[] readAllBytes(String relativePath) throws IOException {
        Path base = storageConfig.getPath();
        Path filePath = base.resolve(relativePath);
        return readAllBytes(filePath);
    }

    private byte[] readAllBytes(Path filePath) throws IOException {
        if(!Files.exists(filePath)) {
            throw new RuntimeException(String.format("file %s is not exit", filePath));
        }
        return Files.readAllBytes(filePath);
    }


}
