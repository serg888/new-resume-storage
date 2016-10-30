package com.urise.webapp.util;

//import com.google.gson.*;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Сергей on 20.10.2016.
 */

public class JsonSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE="INSTANCE";

    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject=json.getAsJsonObject();
        JsonPrimitive prim=(JsonPrimitive)jsonObject.get(CLASSNAME);
        String className=prim.getAsString();

        try{
            Class clazz=Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANCE),clazz);
        } catch (ClassNotFoundException e){
            throw new JsonParseException(e.getMessage());
        }
    }

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext context) {
        JsonObject retValue=new JsonObject();
        retValue.addProperty(CLASSNAME,section.getClass().getName());
        JsonElement elem=context.serialize(section);
        retValue.add(INSTANCE,elem);
        return retValue;
    }
}
