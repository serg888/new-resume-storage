package com.urise.webapp.storage;

import java.io.File;

/**
 * Created by Сергей on 02.10.2016.
 */
public class ObjectStreamStorageTest extends AbstracStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }

}