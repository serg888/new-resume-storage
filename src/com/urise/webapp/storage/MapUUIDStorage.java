package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * Created by Сергей on 01.10.2016.
 */
public class MapUUIDStorage extends AbstractStorage<String> {
    private Map<String, Resume> map;

    public MapUUIDStorage() {
        this.map = new HashMap<>();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume>list=new ArrayList<Resume>(map.values());
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        map.put(searchKey,r);
    }

    @Override
    protected boolean isExist(String searchKey) {
        char c=searchKey.charAt(0);
        return c!='-';
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        String key=searchKey.substring(1);
        map.put(key,r);
    }

    @Override
    protected void doDelete(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return (map.get(uuid)==null)?("-"+uuid):uuid;
    }

    @Override
    protected Resume doGet(String searchKey) {
        return map.get(searchKey);
    }
}
