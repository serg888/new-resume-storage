package com.urise.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.Section;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by Сергей on 19.10.2016.
 */
public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer write){
        GSON.toJson(object,write);
    }

}
