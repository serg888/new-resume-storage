package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.JsonStreamSerializer;
import com.urise.webapp.storage.serializer.XmlStreamSerializer;

/**
 * Created by Сергей on 02.10.2016.
 */
public class JsonPathStorageTest extends AbstracStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(),new JsonStreamSerializer()));
    }

}