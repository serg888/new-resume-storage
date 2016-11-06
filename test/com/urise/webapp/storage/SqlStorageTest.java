package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.storage.serializer.DataStreamSerializer;

/**
 * Created by Сергей on 02.10.2016.
 */
public class SqlStorageTest extends AbstracStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

}