package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;
import com.urise.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by Сергей on 19.10.2016.
 */
public class JsonStreamSerializer implements StreamSerializer{

    @Override
    public void doWrite (Resume r, OutputStream os) throws IOException{
        try(Writer w=new OutputStreamWriter(os, StandardCharsets.UTF_8)){
 //           JsonParser.write(r,w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException{
        try(Reader r=new InputStreamReader(is,StandardCharsets.UTF_8)){
//            return JsonParser.read(r,Resume.class);
        }
        return null;
    }
}
