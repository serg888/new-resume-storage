package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Сергей on 12.10.2016.
 */
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;


    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        for (File f : directory.listFiles()) {
            f.delete();
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("cannot write in this file", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("cannot creat new file", file.getName(), e);
        }

    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("cannot read file", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume>list=new ArrayList<>();
        for(File f:directory.listFiles()){
            try {
                list.add(doRead(f));
            } catch (IOException e) {
                throw new StorageException("cannot read file", f.getName(), e);
            }
        }
        return list;
    }
}
