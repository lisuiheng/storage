package cn.com.kxcomm.storage.domain.storage;

import cn.com.kxcomm.storage.domain.storage.share.bean.Request;
import cn.com.kxcomm.storage.domain.storage.share.bean.Response;
import cn.com.kxcomm.storage.domain.storage.share.bean.storage.ListFileResponse;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @class Storage test
 * @author 李穗恒
 * @create Date 2017-06-02
 * @modified By <修改人>
 * @modified Date <修改日期，格式：YYYY-MM-DD>
 * @why & what <修改原因描述>
 * @since JDK1.8
 * @version 002.00.00
 * @description
 */
public class StorageTest {
    private final Logger log = LoggerFactory.getLogger(StorageTest.class);

    /**
     * @method File name.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void fileName() {
        Path path = Paths.get("/home/lee/Downloads/hello/hello.txt");
        log.info("file name is {}", path.toString());
    }

    /**
     * @method Md 5.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
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

    /**
     * @method File size.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void fileSize() throws IOException {
        Path path = Paths.get("/home/lee/Downloads/datagrip-2017.1.1.tar.gz");
        byte[] bytes = Files.readAllBytes(path);
        int length = bytes.length;
        long size = Files.size(path);
        log.info("length is {}, size is {}", length, size);
    }

    /**
     * @method Get file name.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void getFileName() {
        Path path = Paths.get("/home/lee/Downloads/hello/hello.txt");
        Path fileName = path.getFileName();
        log.info(String.valueOf(fileName.toString().substring(fileName.toString().lastIndexOf(".")+1, fileName.toString().length())));
    }

    /**
     * @method String format.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void StringFormat() {
        Path path = Paths.get("/home/lee/Downloads1");
        String format = String.format("file %s already exit", path);
        log.info(format);
    }

    /**
     * @method Resolve path.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void resolvePath() {
        Path path = Paths.get("/home/lee/Downloads1");
        Path resolve = path.resolve("1");
        log.info(String.valueOf(resolve));
    }

    /**
     * @method Date format.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void dateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        log.info(format);
    }

    /**
     * @method Size of directory.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void sizeOfDirectory() {
        File dir = new File("/home/lee/Downloads");
        long sizeOfDirectory = FileUtils.sizeOfDirectory(dir);

        log.info("size is {}", sizeOfDirectory);
    }


    /**
     * @method Get space.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void getSpace() {
        File dir = new File("/home/lee/Downloads/hello/hello.txt");
        log.info("Free space is {}",  dir.getFreeSpace());
        log.info("Usable space is {}",  dir.getUsableSpace());
        log.info("Total space is {}",  dir.getTotalSpace());
    }

    /**
     * @method Not zero.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void notZero() {
        Long headCorpId = null;
        Long loginOper = null;
        new Request(headCorpId,loginOper,null);
    }

    /**
     * @method List file.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void listFile() throws IOException {
        printAll(Paths.get("/home/lee/Downloads"));
    }

    /**
     * @method Print all.
     * @description
     * @author 李穗恒
     * @param path the path
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
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

    /**
     * @method Get size.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void getSize() throws IOException {
        long size = size(Paths.get("/home/lee/Downloads/hello"));
        log.info("size is {}", size);
    }


    /**
     * @method Size long.
     * @description
     * @author 李穗恒
     * @return the long
     * @param path the path
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
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

    /**
     * @method Get last modify time.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void getLastModifyTime() throws IOException, InterruptedException {
//        FileTime lastModifiedTime = Files.getLastModifiedTime(Paths.get("/home/lee/Downloads/hello"));
        FileTime before = FileTime.from(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        Thread.sleep(200L);
        FileTime last = FileTime.from(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        int i = last.compareTo(before);
        log.info("compare {}", i);
    }

    /**
     * @method Filename.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void filename() {
        Path dir = Paths.get("/home/lee/Downloads");
        Path file = Paths.get("/home/lee/Downloads/hello/hello.txt");
        String substring = file.toString().substring(dir.toString().length() + 1, file.toString().length());
        log.info(substring);
    }

    /**
     * @method Substring file name.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void substringFileName() {
        String relativeName = "20170515/20170515110559484461.txt";
        int lastIndexOf = relativeName.lastIndexOf("/");
        String relativeDir = relativeName.substring(0, lastIndexOf);
        String fileName = relativeName.substring(lastIndexOf, relativeName.length());
        log.info("dir:{}, name:{}", relativeDir, relativeName);
    }

    /**
     * @method List files.
     * @description
     * @author 李穗恒
     * @create Date 2017-06-02
     * @modified By <修改人>
     * @modified Date <修改日期，格式：YYYY-MM-DD>
     * @why & what <修改原因描述>
     * @since JDK1.8
     * @version 002.00.00
     */
    @Test
    public void listFiles() throws IOException, InterruptedException {
        FileTime formTime = FileTime.fromMillis(0);

        final ListFileResponse response = new ListFileResponse(new Request(1L,1L,""));
        Path path = Paths.get("/home/lee/Downloads/file/20170509");

        int subLength = "/home/lee/Downloads/file".length() + 1;

        CompletableFuture.supplyAsync(() -> {
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
                            String md5 = "";
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
                log.error(e.getMessage());
            }
//            Set<ListFileResponse.File> files = response.getFiles();
//            System.out.println(files.size());
            return null;
        });
        Thread.sleep(2000L);
    }
}