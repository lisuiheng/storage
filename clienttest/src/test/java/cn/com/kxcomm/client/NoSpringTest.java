package cn.com.kxcomm.client;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class NoSpringTest {
    @Test
    public void getFile() throws IOException {
        Path path = Paths.get(String.valueOf(this.getClass().getClassLoader().getResource("1.txt")));
        byte[] data = Files.readAllBytes(path);
        assertNotNull(data);
    }
}
