package com.xc.joy.junit4;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author lxcecho 909231497@qq.com
 * @since 22.05.2022
 * <p>
 * Mockito 注解：<a href="https://howtodoinjava.com/mockito/mockito-annotations/">...</a>
 *
 * 如果需要使用以下几个注解，则需要初始化 Mockito，有以下三种方式：
 * 1. @RunWith(MockitoJUnitRunner.class)
 * 2. Use MockitoAnnotations.initMocks(this) in the @Before method of unit test class.即下面的案例。
 * 3. Use MockitoJUnit.rule() to create MockitoRule class.
 *  @Rule public MockitoRule rule = MockitoJUnit.rule();
 */
public class MockAnnotationsTest {

    @Mock
    Map<String, Integer> mockHashMap = new HashMap<>();

    @Before
    public void init() {
        // 使用 @Mock 注解需要将其打开
        MockitoAnnotations.openMocks(this);
    }

    /**
     * @Mock：注解用于创建和注入模拟实例。我们不创建真实的对象，而是要求 mockito 为类创建一个 mock。
     */
    @Test
    public void testMock() {
        mockHashMap.put("A", 1);

        verify(mockHashMap, times(1)).put("A", 1);
        verify(mockHashMap, times(0)).get("A");

        assertEquals(0, mockHashMap.size());
    }

    /**
     * @Spy： 注解用于创建一个实际对象并监视该实际对象。Spy 帮助调用对象的所有常规方法，同时仍然跟踪每个交互，就像我们使用 mock 一样。
     */

    @Spy
    Map<String, Integer> spyMap = new HashMap<>();

    @Test
    public void testSpy() {
        spyMap.put("B", 2);
        verify(spyMap, times(1)).put("B", 2);
        verify(spyMap, times(0)).get("B");

        // spy 创建的 map 真实创建了数据
        assertEquals(1, spyMap.size());
        assertEquals(Integer.valueOf(2), (Integer) spyMap.get("B"));
    }

    /**
     * @Spy 和 @Mock 区别：
     * 1。 When using @Mock, mockito creates a bare-bones shell instance of the Class, entirely instrumented to track interactions with it. This is not a real object and does not maintain the state changes to it.
     * 2. When using @Spy, mockito creates a real instance of the class and track every interactions with it. It maintains the state changes to it.
     */



    /**
     * @Captor: 注解用于创建一个 ArgumentCaptor 实例，该实例用于为进一步的断言捕获方法参数值。
     */
    @Mock
    Map<String, Integer> hashMap = new HashMap<>();

    @Captor
    ArgumentCaptor<String> keyCaptor;

    @Captor
    ArgumentCaptor<Integer> valueCaptor;

    @Test
    public void testCaptor() {
        hashMap.put("A", 1);

        verify(hashMap).put(keyCaptor.capture(), valueCaptor.capture());

        assertEquals("A", keyCaptor.getValue());;
        assertEquals(Integer.valueOf(1), valueCaptor.getValue());
    }

}
