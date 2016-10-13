package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

/**
 * Created by Сергей on 01.10.2016.
 */
public abstract class AbstractStorage<T> implements Storage {

    protected abstract void doUpdate(Resume r, T searchKey);

    protected abstract boolean isExist(T searchKey);

    protected abstract void doSave(Resume r, T searchKey);

    protected abstract void doDelete(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract Resume doGet(T searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void update(Resume r) {
        T searchKey = null;
        searchKey = getExistedSearchKey(r.getUuid());
        if (searchKey != null) doUpdate(r, searchKey);
    }

    @Override
    public void save(Resume r) {
        T searchKey = null;
        searchKey = getNotExistedSearchKey(r.getUuid());
        if (searchKey != null) doSave(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        T searchKey = null;
        searchKey = getExistedSearchKey(uuid);
        return (searchKey == null) ? null : doGet(searchKey);
    }


    @Override
    public void delete(String uuid) {
        T searchKey = null;
        searchKey = getExistedSearchKey(uuid);
        if (searchKey != null) doDelete(searchKey);
    }


    private T getExistedSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getNotExistedSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }


}
