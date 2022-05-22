package com.xc.joy.mockito.basic;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author lxcecho 909231497@qq.com
 * @since 18.05.2022
 */
public class SimpleService {

    public int method1(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }

    public void method2(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }

}
