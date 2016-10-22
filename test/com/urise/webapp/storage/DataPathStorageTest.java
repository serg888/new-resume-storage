package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializer;
import com.urise.webapp.storage.serializer.JsonStreamSerializer;

/**
 * Created by Сергей on 02.10.2016.
 */
public class DataPathStorageTest extends AbstracStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(),new DataStreamSerializer()));
    }

}