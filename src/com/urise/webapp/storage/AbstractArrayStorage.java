package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Array based storage for Resumes
 * Test Branches
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int ARRAY_LENGHT = 1000;
    protected Resume[] storage = new Resume[ARRAY_LENGHT];
    protected int size = 0;

    protected abstract void insert(Resume r, int i);

    protected abstract void fillDeletedElement(int i);

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        int i = searchKey;
        if (i < 0) {
            if (size < ARRAY_LENGHT) {
                insert(r, i);
            } else {
                throw new StorageException("Error: Not enough space", r.getUuid());
            }
        }
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        storage[searchKey] = r;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        int i = searchKey;
        if (i >= 0) {
            storage[i] = storage[size - 1];
            fillDeletedElement(i);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        int i = searchKey;
        return (i < 0) ? null : storage[i];
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> list = Arrays.asList(Arrays.copyOfRange(storage, 0, size));
        return list;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

}
