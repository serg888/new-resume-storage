package com.urise.webapp.util;

import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.TextSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Сергей on 12.11.2016.
 */
public class StringParser {
    public static Section getSection(String s){
        if(s==null)return null;
        String[]strings=s.split("/n");
        if(strings.length==1) {
            return new TextSection(strings[0]);
        } else {
            List<String> list= Arrays.asList(strings);
            return new ListSection(list);
        }
    }

    public static String concatSection(Section section){
        if(section instanceof TextSection){
            return ((TextSection)section).getContent();
        } else
            if(section instanceof ListSection){
                StringBuilder sb=new StringBuilder();
                ListSection listSection=(ListSection)section;
                List<String>list=listSection.getItems();
                for(int i=0;i<list.size()-1;i++){
                    sb.append(list.get(i));
                    sb.append("/n");
                }
                sb.append(list.get(list.size()-1));
                return sb.toString();
            }
            return null;
    }
}
