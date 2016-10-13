package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * Created by Сергей on 01.10.2016.
 */

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list;

    public ListStorage() {
        this.list = new ArrayList<>();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        list.set(searchKey,r);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey==-1)?false:true;
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        list.add(r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        int i=searchKey;
        list.remove(i);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }
}
