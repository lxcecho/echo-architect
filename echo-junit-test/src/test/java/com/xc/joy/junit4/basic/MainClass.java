package com.xc.joy.junit4.basic;

/**
 * @author lxcecho 909231497@qq.com
 * @since 23.05.2022
 */
public class MainClass {
    DatabaseDAO database;
    NetworkDAO network;

    //Setters and getters

    public boolean save(String fileName)
    {
        database.save(fileName);
        System.out.println("Saved in database in Main class");

        network.save(fileName);
        System.out.println("Saved in network in Main class");

        return true;
    }
}
