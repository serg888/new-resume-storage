package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

/**
 * Created by Сергей on 15.10.2016.
 */
public class ObjectStreamPathStorage extends AbstractPathStorage {

    protected ObjectStreamPathStorage(String directory) {
        super(directory);
    }


    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }

    }


    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("cannot read file", null, e);
        }

    }
}
