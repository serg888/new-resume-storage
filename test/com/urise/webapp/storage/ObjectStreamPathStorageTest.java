package com.urise.webapp.storage;

/**
 * Created by Сергей on 02.10.2016.
 */
public class ObjectStreamPathStorageTest extends AbstracStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.getAbsolutePath()));
    }

}