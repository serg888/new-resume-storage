package com.urise.webapp;

import java.io.File;
import java.io.IOException;

/**
 * Created by Сергей on 11.10.2016.
 */
public class MainFile {
    public static void main(String[] args) throws IOException {
        File file=new File(".");
        System.out.println(file.getCanonicalPath());
        listPath("",file.getCanonicalPath());
        //for(File f: file.listFiles())
        //    System.out.println(f.getCanonicalPath()+"--"+(f.isDirectory()?"dir":"file"));

    }
    public static void listPath(String level,String path)throws IOException{
        File file=new File(path);
        for(File f:file.listFiles()){
            if(f.isDirectory()){
                System.out.println(level+"Directory: "+f.getName());
                listPath(level+"   ",f.getCanonicalPath());
            } else{
                System.out.println(level+"File: "+f.getName());
            }
        }

    }
}
