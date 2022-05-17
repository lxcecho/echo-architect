package com.xc.joy.service;

import com.xc.joy.model.MockitoDemo;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class MockDemoService {
    public MockitoDemo get() {
        throw new RuntimeException("testFoo exception");
    }
}
