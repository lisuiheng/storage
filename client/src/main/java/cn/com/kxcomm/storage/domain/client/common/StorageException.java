package cn.com.kxcomm.storage.domain.client.common;

public class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
