package cn.com.kxcomm.storage.domain.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class InitRunner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(InitRunner.class);

    @Autowired
    private StorageServer storageServer;

    @Override
    public void run(String... strings) throws Exception {
        CompletableFuture.supplyAsync(() -> {
            try {
                storageServer.start(8007);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }).exceptionally(t -> {
            log.error(t.getMessage());
            return null;
        });
        storageServer.start(8008);
    }
}
