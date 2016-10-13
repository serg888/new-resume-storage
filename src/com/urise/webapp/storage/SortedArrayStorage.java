package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Сергей on 25.09.2016.
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private static Comparator<Resume> comparator=new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };
    @Override
    public Integer getSearchKey(String uuid) {
        Resume searchKey=new Resume(uuid,"dummy" );
        return Arrays.binarySearch(storage,0,size,searchKey,comparator);
    }

    @Override
    protected void insert(Resume r, int i) {
        for(int i1=size;i1>-(i+1);i1--)
            storage[i1]=storage[i1-1];
        storage[-(i+1)]=r;
        size++;
    }

    @Override
    protected void fillDeletedElement(int i) {
        for(int i1=i;i1<size-1;i1++)
            storage[i1]=storage[i1+1];
    }
}
