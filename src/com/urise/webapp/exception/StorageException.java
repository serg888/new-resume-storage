package com.urise.webapp.exception;

import java.io.IOException;

/**
 * Created by Сергей on 28.09.2016.
 */
public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message) {
        this(message, null, null);
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        this(message, null, e);
    }


    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
