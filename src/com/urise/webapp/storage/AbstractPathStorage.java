package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Сергей on 12.10.2016.
 */
public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;


    protected AbstractPathStorage(String dir) {
        directory= Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if(!Files.isDirectory(directory)||!Files.isWritable(directory)){
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return directory.getNameCount();
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("cannot write this file", path.toString(), e);
        }

    }

    @Override
    protected boolean isExist(Path path) {
        return path.toFile().exists();
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("cannot creat new file", path.toString(), e);
        }
        doUpdate(r, path);

    }

    @Override
    protected void doDelete(Path path) {

        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException("File is not exist", path.toString());
            }
        } catch (IOException e) {
            throw new StorageException("File delete error", path.toString(),e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("cannot read file", path.toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> list.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return list;
    }
}
