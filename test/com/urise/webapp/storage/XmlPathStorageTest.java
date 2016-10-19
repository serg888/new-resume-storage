package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;
import com.urise.webapp.storage.serializer.XmlStreamSerializer;

/**
 * Created by Сергей on 02.10.2016.
 */
public class XmlPathStorageTest extends AbstracStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(),new XmlStreamSerializer()));
    }

}