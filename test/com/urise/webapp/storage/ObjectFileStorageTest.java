package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Сергей on 02.10.2016.
 */
public class ObjectFileStorageTest extends AbstracStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }

}