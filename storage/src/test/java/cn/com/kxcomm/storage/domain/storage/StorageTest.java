package cn.com.kxcomm.storage.domain.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class StorageTest {
    private final Logger log = LoggerFactory.getLogger(StorageTest.class);

    @Test
    public void fileName() {
        Path path = Paths.get("/home/lee/Downloads/hello/hello.txt");
        log.info("file name is {}", path.toString());
    }

    @Test
    public void md5() throws IOException {
        Path path = Paths.get("/home/lee/Downloads/hello/hello.txt");

        byte[] bytes = Files.readAllBytes(path);
        log.info("md5 start");
        String md5 = DigestUtils.md5DigestAsHex(bytes);
        log.info("md5 end");
        FileInputStream fis = null;


        fis = new FileInputStream(new File("/home/lee/Downloads/datagrip-2017.1.1.tar.gz"));
        String md5utils = org.apache.commons.codec.digest.DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        log.info("md5utils is {}, md5 is {}", md5utils, md5 );
    }

    @Test
    public void fileSize() throws IOException {
        Path path = Paths.get("/home/lee/Downloads/datagrip-2017.1.1.tar.gz");
        byte[] bytes = Files.readAllBytes(path);
        int length = bytes.length;
        long size = Files.size(path);
        log.info("length is {}, size is {}", length, size);
    }

    @Test
    public void getFileName() {
        Path path = Paths.get("/home/lee/Downloads/hello/hello.txt");
        Path fileName = path.getFileName();
        log.info(String.valueOf(fileName.toString().substring(fileName.toString().lastIndexOf(".")+1, fileName.toString().length())));
    }

    @Test
    public void StringFormat() {
        Path path = Paths.get("/home/lee/Downloads1");
        String format = String.format("file %s already exit", path);
        log.info(format);
    }

    @Test
    public void resolvePath() {
        Path path = Paths.get("/home/lee/Downloads1");
        Path resolve = path.resolve("1");
        log.info(String.valueOf(resolve));
    }

    @Test
    public void dateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        log.info(format);
    }

    @Test
    public void sizeOfDirectory() {
        File dir = new File("/home/lee/Downloads");
        long sizeOfDirectory = FileUtils.sizeOfDirectory(dir);

        log.info("size is {}", sizeOfDirectory);
    }


    @Test
    public void getSpace() {
        File dir = new File("/home/lee/Downloads/hello/hello.txt");
        log.info("Free space is {}",  dir.getFreeSpace());
        log.info("Usable space is {}",  dir.getUsableSpace());
        log.info("Total space is {}",  dir.getTotalSpace());
    }

    @Test
    public void notZero() {
        Long headCorpId = null;
        Long loginOper = null;
        new Request(headCorpId,loginOper,null);
    }

    @Test
    public void listFile() throws IOException {
        printAll(Paths.get("/home/lee/Downloads"));
    }

    private void printAll(Path path) {
        try {
            Files.list(path)
                    .forEach(p -> {
                        if(Files.isDirectory(p)) {
                            printAll(p);
                        }
                        System.out.println(p.getFileName());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSize() throws IOException {
        long size = size(Paths.get("/home/lee/Downloads/hello"));
        log.info("size is {}", size);
    }


    private static long size(Path path) {

        final AtomicLong size = new AtomicLong(0);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println(file.getFileName());
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {

                    System.out.println("skipped: " + file + " (" + exc + ")");
                    // Skip folders that can't be traversed
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (exc != null)
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    // Ignore errors traversing a folder
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
    }

    @Test
    public void getLastModifyTime() throws IOException {
        FileTime lastModifiedTime = Files.getLastModifiedTime(Paths.get("/home/lee/Downloads/hello"));
    }
}