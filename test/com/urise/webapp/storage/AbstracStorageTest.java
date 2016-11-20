package com.urise.webapp.storage;


import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.ResumeInit;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 28.09.2016.
 */
public abstract class AbstracStorageTest {
    protected Storage storage;

        protected static final File STORAGE_DIR = Config.get().getStorageDir();
    //protected static final File STORAGE_DIR=new File("C:/Users/Сергей/resume-storage/storage");
   // protected static final File STORAGE_DIR=new File("./storage");
    protected static Resume R1;
    protected static Resume R2;
    protected static Resume R3;
    protected static Resume R4;

    private static String UUID_1;

    static {
        List<Resume>list=ResumeInit.getResumes();
        R1=list.get(0);
        R2=list.get(1);
        R3=list.get(2);
        R4=list.get(3);
        UUID_1=R1.getUuid();
    }


    protected AbstracStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R3);
        storage.save(R1);
        storage.save(R2);
    }


    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "Zhenya");
        newResume.addContact(ContactType.MAIL, "zh@mail.ru");
        newResume.addContact(ContactType.PHONE, "888888");
        newResume.addContact(ContactType.SKYPE, "skype-zzzzzzz");
        newResume.addContact(ContactType.MOBILE, "99866567464");

        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        assertEquals(null, storage.get(UUID_1));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void get() throws Exception {
        assertGet(R1);
        Resume r1 = storage.get(R1.getUuid());
        System.out.println(r1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        assertEquals(null, storage.get("dummy"));
    }

    @Test
    public void getAllSorted() throws Exception {
        //must be overrided for sorted
        List<Resume> resumes = storage.getAllSorted();
        assertEquals(3, resumes.size());
        assertEquals(resumes, Arrays.asList(R1, R2, R3));
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistRezume() throws Exception {
        Resume r = new Resume(UUID_1, "Vasya");
        storage.save(new Resume(UUID_1, "Vasya"));
        assertTrue(r != storage.get(UUID_1));
    }


    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        storage.delete("dummy");
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    protected void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }


}