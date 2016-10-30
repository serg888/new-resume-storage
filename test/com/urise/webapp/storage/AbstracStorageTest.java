package com.urise.webapp.storage;


import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 28.09.2016.
 */
public abstract class AbstracStorageTest {
    protected Storage storage;

        protected static final File STORAGE_DIR = Config.get().getStorageDir();
    //protected static final File STORAGE_DIR=new File("C:/Users/Сергей/resume-storage/storage");
   // protected static final File STORAGE_DIR=new File("./storage");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    protected static final Resume R1;
    protected static final Resume R2;
    protected static final Resume R3;
    protected static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");

/*
        R1.addContact(ContactType.MAIL, "serg888@mail.ru");
        R1.addContact(ContactType.PHONE, "8-933-300-4498");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("achievment1", "achievment1", "achievment2"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Turbo Pascal", "1C", "Java"));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "contenet1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))
                ));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organisation12", "http://organisation12.ru")
                ));

        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "phone2");
        R2.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1")
                        )));
*/
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