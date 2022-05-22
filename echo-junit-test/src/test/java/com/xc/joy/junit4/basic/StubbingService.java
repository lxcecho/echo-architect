package com.xc.joy.junit4.basic;

/**
 * @author lxcecho 909231497@qq.com
 * @since 18.05.2022
 */
public class StubbingService {

    public int getI() {
        System.out.println("getI....");
        return 10;
    }

    public String getS() {
        System.out.println("getS....");
        throw new RuntimeException();
    }

}
