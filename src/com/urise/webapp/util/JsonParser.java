package com.urise.webapp.util;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.Section;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

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

    public static <T> T read(String s,Class<T> clazz){
        return GSON.fromJson(s,clazz);
    }

    public static <T> void write(T object, Writer write){
        GSON.toJson(object,write);
    }


    public static String write(Object object, Type type){
        return GSON.toJson(object,type);
    }


/*
    public static String write(Object object){
        return GSON.toJson(object);
    }
*/


}
