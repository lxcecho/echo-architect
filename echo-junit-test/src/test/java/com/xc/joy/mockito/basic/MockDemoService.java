package com.xc.joy.mockito.basic;

/**
 * @author lxcecho 909231497@qq.com
 * @since 17.05.2022
 */
public class MockDemoService {
    public MockitoDemo get() {
        throw new RuntimeException("testFoo exception");
    }
}
