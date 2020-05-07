package com.github.cynaith.util;


public class CutTime {

    private static CutTime cutTime = new CutTime();
    private volatile static long startTime;
    private volatile static long endTime;

    private CutTime(){

    }

    public String startTime(){
        startTime = System.currentTimeMillis();
        return "OK";
    }

    public String endTime(){
        endTime = System.currentTimeMillis();
        return "程序运行时间： "+(endTime-startTime)+"ms";
    }

    public static CutTime getInstance(){
        return cutTime;
    }

}
