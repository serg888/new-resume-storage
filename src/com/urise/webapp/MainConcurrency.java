package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 24.10.2016.
 */
public class MainConcurrency {
    private static int counter;

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(getName());
            }
        };
        thread0.start();
        System.out.println(thread0.getState());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        System.out.println(thread0.getState());

        List<Thread> threads=new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        inc();
                    }
                }
            });
            thread.start();
            threads.add(thread);

        }

        System.out.println(counter);
    }

    private static synchronized void  inc() {
        counter++;
    }
}


