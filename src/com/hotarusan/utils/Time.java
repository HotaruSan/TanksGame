package com.hotarusan.utils;

/**
 * Created by HotaruSan on 08.02.2018.
 */
public class Time {
    public static final long SECOND = 1000000000l;

    public static long get(){
        return System.nanoTime();
    }
}
