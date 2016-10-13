package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 * Test Branches
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected Integer getSearchKey(String uuid){
        //если резюме не найдено, возвращает -1, иначе индекс в массиве
        for(int i=0;i<size;i++){
            if(storage[i].getUuid().equals(uuid)) return i;
        }
        return -1;
    }

    @Override
    protected void insert(Resume r, int i) {
        storage[size++]=r;
    }

    @Override
    protected void fillDeletedElement(int i) {
        storage[i]=storage[size-1];
    }


}
