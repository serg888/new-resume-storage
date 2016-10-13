package com.urise.webapp;

import java.util.Date;

/**
 * Created by Сергей on 11.10.2016.
 */
public class MainDate {
    public static void main(String[] args) throws InterruptedException {
        Date date=new Date();
        System.out.println(System.currentTimeMillis()-date.getTime());
    }
}
