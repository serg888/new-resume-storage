package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

/**
 * Created by Сергей on 02.10.2016.
 */
public class ObjectPathStorageTest extends AbstracStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(),new ObjectStreamSerializer()));
    }

}